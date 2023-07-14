package com.cestar.book;

import com.cestar.jdbc.JDBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author
 * @project FSDTProjectLibraryManagement
 * @since 7/13/2023
 **/
public class AssignBook extends JFrame {
    public AssignBook(String userName) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Add New Book");

        // Create form components
        JLabel bookISBNLabel = new JLabel("Book ISBN:");
        JTextField bookISBNField = new JTextField(20);
        JLabel userEmailLabel = new JLabel("User Email:");
        JTextField userEmailField = new JTextField(20);

        // Create a panel for the form
        int vgap = 10;
        int hgap = 10;
        JPanel formPanel = new JPanel(new GridLayout(7, 2, vgap, hgap));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        formPanel.add(bookISBNLabel);
        formPanel.add(bookISBNField);
        formPanel.add(userEmailLabel);
        formPanel.add(userEmailField);

        // Create a button for submitting the form
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookISBN = bookISBNField.getText();
                String userEmail = userEmailField.getText();
                try {
                    JDBConnection jdbConnection = new JDBConnection();
                    Connection con = jdbConnection.setUpConnection();
                    PreparedStatement ps = con.prepareStatement("insert into user_book(email,isbn,lent_date) values(?,?,now())");
                    ps.setString(1, userEmail);
                    ps.setString(2, bookISBN);

                    ps.executeUpdate();
                    con.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                dispose();
            }
        });

        // Create a panel for the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);

        // Create a main panel and add the form panel and button panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the content pane
        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }
}