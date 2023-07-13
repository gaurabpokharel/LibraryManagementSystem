package com.cestar.book;

import com.cestar.jdbc.JDBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * @author
 * @project FSDTProjectLibraryManagement
 * @since 7/13/2023
 **/
public class ReturnBook extends JFrame {
    public ReturnBook(String userName) {
        setTitle("User Book Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Connect to the database and retrieve the data from the table
        JDBConnection jdbConnection = new JDBConnection();
        try (Connection connection = jdbConnection.setUpConnection();
             Statement statement = connection.createStatement()) {

            // Retrieve the data from the table
            String selectDataQuery = "SELECT * FROM user_book";
            ResultSet resultSet = statement.executeQuery(selectDataQuery);

            // Create the table model with column names
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("User Book ID");
            tableModel.addColumn("Email");
            tableModel.addColumn("ISBN");
            tableModel.addColumn("Lent Date");
            tableModel.addColumn("Return Date");

            // Add rows to the table model
            while (resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = resultSet.getInt("user_book_id");
                row[1] = resultSet.getString("email");
                row[2] = resultSet.getString("isbn");
                row[3] = resultSet.getString("lentDate");
                row[4] = resultSet.getString("returnDate");
                tableModel.addRow(row);
            }

            // Create the JTable with the table model
            JTable table = new JTable(tableModel);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

            // Set custom column width if desired
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(150);
            table.getColumnModel().getColumn(3).setPreferredWidth(250);
            table.getColumnModel().getColumn(4).setPreferredWidth(150);
            // ... adjust column widths as needed

            // Add the table to a scroll pane
            JScrollPane scrollPane = new JScrollPane(table);

            // Add the scroll pane to the JFrame
            getContentPane().add(scrollPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving data: " + e.getMessage());
        }
    }
}