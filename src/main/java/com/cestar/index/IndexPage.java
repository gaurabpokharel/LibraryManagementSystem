package com.cestar.index;

import com.cestar.book.AddBookForm;
import com.cestar.book.AssignBook;
import com.cestar.book.ReturnBook;
import com.cestar.book.ViewBook;
import com.cestar.user.AddUserForm;
import com.cestar.user.ViewUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class IndexPage extends JFrame {

    private JButton addBookButton;
    private JButton viewBookButton;
    private JButton addUserButton;
    private JButton viewUserButton;

    private JButton assignBook;

    private JButton returnBook;

    public IndexPage(String username) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Welcome! Index Page");

        // Create a label to display the welcome message
        JLabel welcomeLabel = new JLabel("Welcome: " + username);
        welcomeLabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 10));

        // Create buttons for adding a new book and user
        addBookButton = new JButton("Add New Book");
        viewBookButton = new JButton("View Books");
        addUserButton = new JButton("Add New User");
        viewUserButton = new JButton("View User");
        assignBook = new JButton("Assign Book");
        returnBook = new JButton("Return Book");
        

        // Add action listeners to the buttons
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add new book button click event
                // Add your logic here
                AddBookForm addBookForm = new AddBookForm(username);
                addBookForm.setVisible(true);
            }
        });
        
        viewBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add new book button click event
                // Add your logic here
                ViewBook viewBook = null;
                try {
                    viewBook = new ViewBook(username);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                viewBook.setVisible(true);
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add new user button click event
                // Add your logic here
            	AddUserForm addUserForm = new AddUserForm(username);
            	addUserForm.setVisible(true);
            }
        });
        
        viewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add new user button click event
                // Add your logic here

                ViewUser viewUser = new ViewUser(username);
                viewUser.setVisible(true);
            }
        });

        assignBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add new user button click event
                // Add your logic here

                AssignBook assignBook = new AssignBook(username);
                assignBook.setVisible(true);
            }
        });

        returnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add new user button click event
                // Add your logic here

                ReturnBook returnBook = new ReturnBook(username);
                returnBook.setVisible(true);
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBookButton);
        buttonPanel.add(viewBookButton);
        buttonPanel.add(addUserButton);
        buttonPanel.add(viewUserButton);
        buttonPanel.add(assignBook);
        buttonPanel.add(returnBook);

        // Create a main panel and add the components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the content pane
        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }

}
