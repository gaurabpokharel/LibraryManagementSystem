package com.cestar.book;

import com.cestar.index.IndexPage;
import com.cestar.jdbc.JDBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ReturnBook extends JFrame {
    private DefaultTableModel tableModel;
    private JTable mainTable;
    private JButton backButton;

    public ReturnBook(String username) {
        setTitle("User Book Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Connect to the database and retrieve the data from the table
        JDBConnection jdbConnection = new JDBConnection();
        try (Connection connection = jdbConnection.setUpConnection();
             Statement statement = connection.createStatement()) {

            // Retrieve the data from the table
            String selectDataQuery = "SELECT * FROM user_book WHERE return_date IS NULL";
            ResultSet resultSet = statement.executeQuery(selectDataQuery);

            // Create the table model with column names
             tableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Only the last column (Return) is editable
                    return column == getColumnCount() - 1;
                }
            };
            tableModel.addColumn("User Book ID");
            tableModel.addColumn("Email");
            tableModel.addColumn("ISBN");
            tableModel.addColumn("Lent Date");
            tableModel.addColumn("Return");

            // Add rows to the table model
            while (resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = resultSet.getInt("user_book_id");
                row[1] = resultSet.getString("email");
                row[2] = resultSet.getString("isbn");
                row[3] = resultSet.getString("lent_date");
                row[4] = "Return";

                // Store email and ISBN as additional data in the table model
                ((DefaultTableModel) tableModel).addRow(new Object[]{row[0], row[1], row[2], row[3], row[4]});
            }
            // Create the JTable with the table model
            mainTable = new JTable(tableModel);

            // Set custom column width if desired
            mainTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            mainTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            mainTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            mainTable.getColumnModel().getColumn(3).setPreferredWidth(250);
            mainTable.getColumnModel().getColumn(4).setPreferredWidth(100);

            // Create a custom cell renderer for the "Return" column to display a button
            mainTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());

            // Create a custom cell editor for the "Return" column to handle button clicks
            mainTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), mainTable));

            // Add the table to a scroll pane
            JScrollPane scrollPane = new JScrollPane(mainTable);
            getContentPane().add(scrollPane);
            pack();


            backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Create a new instance of IndexPage
                    IndexPage indexPage = new IndexPage(username);

                    // Set the IndexPage frame as visible
                    indexPage.setVisible(true);

                    // Dispose the current ViewUser frame
                    dispose();
                }
            });
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(backButton);

            Container container = getContentPane();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.add(scrollPane);
            container.add(buttonPanel);

            pack();

        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving data: " + e.getMessage());
        }
    }

    // Custom cell renderer for the "Return" column to display a button
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom cell editor for the "Return" column to handle button clicks
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String email;
        private String isbn;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            button = new JButton();
            button.setOpaque(true);

            // Handle button click event
            button.addActionListener(new ActionListener() {
                DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
                public void actionPerformed(ActionEvent e) {
                    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to return the book?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        fireEditingStopped();

                        // Get the email and ISBN from the corresponding row
                        email = table.getValueAt(table.getSelectedRow(), 1).toString();
                        isbn = table.getValueAt(table.getSelectedRow(), 2).toString();

                        // Perform action with email and ISBN
                        System.out.println("Return button clicked in row: " + table.getSelectedRow());
                        System.out.println("Email: " + email);
                        System.out.println("ISBN: " + isbn);
                        JDBConnection jdbConnection = new JDBConnection();
                        Connection con = jdbConnection.setUpConnection();
                        PreparedStatement ps = null;
                        try {
                            ps = con.prepareStatement("UPDATE user_book SET return_date = now() WHERE email = ? AND isbn = ? AND return_date IS NULL");
                            ps.setString(1, email);
                            ps.setString(2, isbn);
                            ps.executeUpdate();

                            String selectDataQuery = "SELECT * FROM user_book WHERE return_date IS NULL";
                            ResultSet resultSet = ps.executeQuery(selectDataQuery);

                            // Clear the existing rows in the table model
                            model.setRowCount(0);

                            // Add the updated rows to the table model
                            while (resultSet.next()) {
                                Object[] row = new Object[5];
                                row[0] = resultSet.getInt("user_book_id");
                                row[1] = resultSet.getString("email");
                                row[2] = resultSet.getString("isbn");
                                row[3] = resultSet.getString("lent_date");
                                row[4] = "Return";
                                model.addRow(row);
                            }

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }

            button.setText((value == null) ? "" : value.toString());
            return button;
        }

        public Object getCellEditorValue() {
            return button.getText();
        }
    }
}
