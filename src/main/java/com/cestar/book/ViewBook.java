package com.cestar.book;
import com.cestar.index.IndexPage;
import com.cestar.jdbc.JDBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewBook extends JFrame {

    private JTable userTable;
    private JButton backButton;
    private JButton deleteButton;
    private List<Integer> selectedRows;
    Statement statement;

    public ViewBook(String username) throws SQLException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Book Display");

        // Initialize the selectedRows list
        selectedRows = new ArrayList<>();

        // Fetch user data from the database
        try {
            // Establish a database connection
            JDBConnection jdbConnection = new JDBConnection();
            Connection con = jdbConnection.setUpConnection();
            // Create a SQL query
            String query = "SELECT * FROM books";

            // Execute the query
            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Create the table model
            DefaultTableModel tableModel = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 0) {
                        return Boolean.class; // Checkbox column
                    }
                    return super.getColumnClass(columnIndex);
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 0; // Allow editing only for the checkbox column
                }
            };

            // Add column headers
            tableModel.addColumn("Select");
            tableModel.addColumn("Title");
            tableModel.addColumn("Author");
            tableModel.addColumn("Genre");
            tableModel.addColumn("Year");
            tableModel.addColumn("Publisher");
            tableModel.addColumn("ISBN");

            // Add rows to the table model
            int rowCount = 0;
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                String year = resultSet.getString("year");
                String publisher = resultSet.getString("publisher");
                String isbn = resultSet.getString("isbn");
                

                // Create an array to hold the user data for each row
                Object[] rowData = {false, title, author, genre, year, publisher, isbn};

                // Add the row to the table model
                tableModel.addRow(rowData);

                // Add the row index to the selectedRows list
                selectedRows.add(rowCount);
                rowCount++;
            }

            // Create the JTable with the table model
            userTable = new JTable(tableModel);

            // Close the database connection
            resultSet.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // Logging the exception raised
                    e.printStackTrace();
                }
            }
        }

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Add the scroll pane to the content pane
        getContentPane().add(scrollPane);

        pack();
        
        // Create the "Back" button
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

        // Create the "Delete" button
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected row(s)?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    deleteSelectedRows();
                }
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Create a container to hold the scroll pane and the button panel
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(scrollPane);
        container.add(buttonPanel);

        // Pack the components
        pack();

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    private void deleteSelectedRows() {
        if (selectedRows.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No rows selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // Establish a database connection
            JDBConnection jdbConnection = new JDBConnection();
            connection = jdbConnection.setUpConnection();
            // Prepare the delete query
            String deleteQuery = "DELETE FROM books WHERE isbn = ?";

            // Delete the selected rows from the database
            for (int i = selectedRows.size() - 1; i >= 0; i--) {
                int rowIndex = selectedRows.get(i);
                String flag = model.getValueAt(rowIndex, 0).toString();
                if (flag.equalsIgnoreCase("true")) {
                    String isbn = model.getValueAt(rowIndex, 6).toString();
                    statement = connection.prepareStatement(deleteQuery);
                    statement.setString(1, isbn);
                    statement.executeUpdate();

                    // Remove the row from the table model
                    model.removeRow(rowIndex);
                }
            }
            selectedRows.clear();

            // Refresh the table to reflect the remaining data
            model.fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and connection
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
