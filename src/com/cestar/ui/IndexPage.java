package com.cestar.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IndexPage extends JFrame {

    private JButton addBookButton;
    private JButton viewBookButton;
    private JButton addUserButton;
    private JButton viewUserButton;

    IndexPage(String username) {
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
        

        // Add action listeners to the buttons
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add new book button click event
                // Add your logic here
                AddBookForm addBookForm = new AddBookForm();
                addBookForm.setVisible(true);
            }
        });
        
        viewBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add new book button click event
                // Add your logic here
                ViewBook viewBook = new ViewBook();
                viewBook.setVisible(true);
            }
        });

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add new user button click event
                // Add your logic here
            	AddUserForm addUserForm = new AddUserForm();
            	addUserForm.setVisible(true);
            }
        });
        
        viewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add new user button click event
                // Add your logic here

                ViewUser viewUser = new ViewUser();
                viewUser.setVisible(true);
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBookButton);
        buttonPanel.add(viewBookButton);
        buttonPanel.add(addUserButton);
        buttonPanel.add(viewUserButton);

        // Create a main panel and add the components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the content pane
        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }

	public IndexPage() {
		// TODO Auto-generated constructor stub
	}
}
