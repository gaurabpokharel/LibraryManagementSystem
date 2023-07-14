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
    private JButton assignBookButton;
    private JButton returnBookButton;

    public IndexPage(String username) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Welcome! Index Page");

        // Create a label to display the welcome message
        JLabel welcomeLabel = new JLabel("Welcome: " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create buttons for different actions
        addBookButton = createButton("Add New Book");
        viewBookButton = createButton("View Books");
        addUserButton = createButton("Add New User");
        viewUserButton = createButton("View Users");
        assignBookButton = createButton("Assign Book");
        returnBookButton = createButton("Return Book");

        // Add action listeners to the buttons
        addBookButton.addActionListener(e -> openAddBookForm(username));
        viewBookButton.addActionListener(e -> openViewBookForm(username));
        addUserButton.addActionListener(e -> openAddUserForm(username));
        viewUserButton.addActionListener(e -> openViewUserForm(username));
        assignBookButton.addActionListener(e -> openAssignBookForm(username));
        returnBookButton.addActionListener(e -> openReturnBookForm(username));

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.add(addBookButton);
        buttonPanel.add(viewBookButton);
        buttonPanel.add(addUserButton);
        buttonPanel.add(viewUserButton);
        buttonPanel.add(assignBookButton);
        buttonPanel.add(returnBookButton);

        // Create a main panel and add the components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the main panel to the content pane
        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }

    // Utility method to create a styled button
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(180, 60));
        return button;
    }

    // Methods to open different forms

    private void openAddBookForm(String username) {
        AddBookForm addBookForm = new AddBookForm(username);
        addBookForm.setVisible(true);
        dispose();
    }

    private void openViewBookForm(String username) {
        try {
            ViewBook viewBook = new ViewBook(username);
            viewBook.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            showErrorDialog("Error occurred while viewing books.");
        }
    }

    private void openAddUserForm(String username) {
        AddUserForm addUserForm = new AddUserForm(username);
        addUserForm.setVisible(true);
        dispose();
    }

    private void openViewUserForm(String username) {
        ViewUser viewUser = new ViewUser(username);
        viewUser.setVisible(true);
        dispose();
    }

    private void openAssignBookForm(String username) {
        AssignBook assignBook = new AssignBook(username);
        assignBook.setVisible(true);
        dispose();
    }

    private void openReturnBookForm(String username) {
        ReturnBook returnBook = new ReturnBook(username);
        returnBook.setVisible(true);
        dispose();
    }

    // Utility method to show an error dialog
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
