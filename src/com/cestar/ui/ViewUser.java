package com.cestar.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewUser extends JFrame {

    private JTable userTable;
    private JButton backButton;

    ViewUser() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("User Display");

        // Fetch user data from the database
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryDB", "root", "");

            // Create a SQL query
            String query = "SELECT * FROM user";

            // Execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Create the table model
            DefaultTableModel tableModel = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    // Return the class of the column containing the delete button
                    if (columnIndex == getColumnCount() - 1) {
                        return JButton.class;
                    }
                    return super.getColumnClass(columnIndex);
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    // Disable editing for all cells
                    return false;
                }
            };

            // Add column headers
            tableModel.addColumn("First Name");
            tableModel.addColumn("Last Name");
            tableModel.addColumn("Email");
            tableModel.addColumn("Gender");
            tableModel.addColumn("Phone Number");
            tableModel.addColumn("Address");
            tableModel.addColumn("Delete"); // Add delete button column

            // Add rows to the table model
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String gender = resultSet.getString("gender");
                String phoneNumber = resultSet.getString("phone_number");
                String address = resultSet.getString("address");

                // Create an array to hold the user data for each row
                Object[] rowData = {firstName, lastName, email, gender, phoneNumber, address, "Delete"};

                // Add the row to the table model
                tableModel.addRow(rowData);
            }

            // Create the JTable with the table model
            userTable = new JTable(tableModel) {
                @Override
                public void setValueAt(Object value, int row, int column) {
                    // Handle delete button click
                    if (column == getColumnCount() - 1 && value.equals("Delete")) {
                        // Get the selected user ID from the row
                        int userId = Integer.parseInt(getValueAt(row, 0).toString());

                        // Perform the delete operation
                        deleteUserData(userId);

                        // Remove the row from the table model
                        ((DefaultTableModel) getModel()).removeRow(row);
                    }
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    // Allow editing only for the delete button column
                    return column == getColumnCount() - 1;
                }
            };

            // Set a custom cell renderer for the delete button column
            userTable.getColumnModel().getColumn(userTable.getColumnCount() - 1).setCellRenderer(new ButtonRenderer());

            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
                IndexPage indexPage = new IndexPage();

                // Set the IndexPage frame as visible
                indexPage.setVisible(true);

                // Dispose the current ViewUser frame
                dispose();
            }
        });

        // Create a panel to hold the "Back" button
        JPanel buttonPanel = new JPanel();
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

    // Custom cell renderer for the delete button column
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            return this;
        }
    }

    // Method to delete user data from the database
    private void deleteUserData(int userId) {
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryDB", "root", "");

            // Create a SQL query to delete the user
            String query = "DELETE FROM user WHERE id = ?";

            // Prepare the statement with the user ID
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            // Execute the delete statement
            int rowsAffected = statement.executeUpdate();

            // Check if the delete operation was successful
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("Failed to delete user.");
            }

            // Close the database connection and statement
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
