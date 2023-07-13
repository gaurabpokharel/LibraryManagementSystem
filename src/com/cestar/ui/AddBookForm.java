package com.cestar.ui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddBookForm extends JFrame {
    // Instance variables for additional attributes
    private JLabel genreLabel;
    private JTextField genreField;
    private JLabel yearLabel;
    private JTextField yearField;
    private JLabel publisherLabel;
    private JTextField publisherField;
    private JLabel isbnLabel;
    private JTextField isbnField;

    AddBookForm() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Add New Book");

        // Create form components
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField(20);
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField(20);
        genreLabel = new JLabel("Genre:");
        genreField = new JTextField(20);
        yearLabel = new JLabel("Year:");
        yearField = new JTextField(4);
        publisherLabel = new JLabel("Publisher:");
        publisherField = new JTextField(20);
        isbnLabel = new JLabel("ISBN:");
        isbnField = new JTextField(13);

        // Create a panel for the form
        int vgap =10;
        int hgap =10;
        JPanel formPanel = new JPanel(new GridLayout(7, 2,vgap,hgap));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        formPanel.add(titleLabel);
        formPanel.add(titleField);
        formPanel.add(authorLabel);
        formPanel.add(authorField);
        formPanel.add(genreLabel);
        formPanel.add(genreField);
        formPanel.add(yearLabel);
        formPanel.add(yearField);
        formPanel.add(publisherLabel);
        formPanel.add(publisherField);
        formPanel.add(isbnLabel);
        formPanel.add(isbnField);

        // Create a button for submitting the form
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String genre = genreField.getText();
                String year = yearField.getText();
                String publisher = publisherField.getText();
                String isbn = isbnField.getText();
                try {
             	   Class.forName("com.mysql.jdbc.Driver");
             	   String mysqlUrl = "jdbc:mysql://localhost:3306/libraryDB";
                    Connection con = DriverManager.getConnection(mysqlUrl, "root", "");
                    PreparedStatement ps = con.prepareStatement("insert into books(title,author,genre,year,publisher,isbn) values(?,?,?,?,?,?)");
                    ps.setString(1, title);
                    ps.setString(2, author);
                    ps.setString(3, genre);
                    ps.setString(4, year);
                    ps.setString(5, publisher);
                    ps.setString(6, isbn);
                    
                    ps.executeUpdate();
                }catch(Exception ex) {
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
