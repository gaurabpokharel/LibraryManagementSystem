package com.cestar.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

class LoginPageForm extends JFrame implements ActionListener {

    private JTextField textField1;
    private JPasswordField passwordField;
    private JButton submitButton;

    LoginPageForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login Form");

        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Create the username label and text field
        JLabel userLabel = new JLabel("Username");
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(userLabel, constraints);

        textField1 = new JTextField(20); // Increased width to 20
        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(textField1, constraints);

        // Create the password label and password field
        JLabel passLabel = new JLabel("Password");
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(passLabel, constraints);

        passwordField = new JPasswordField(20); // Increased width to 20
        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(passwordField, constraints);

        // Create the submit button
        submitButton = new JButton("Submit");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        mainPanel.add(submitButton, constraints);

        // Add an action listener to the submit button
        submitButton.addActionListener(this);

        // Add the main panel to the content pane
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userValue = textField1.getText();
        String passValue = new String(passwordField.getPassword());

        if (userValue.equals("test@gmail.com") && passValue.equals("test")) {
        	IndexPage page = new IndexPage(userValue);
            page.setVisible(true);
            
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class LoginPage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the look and feel to the system's default
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            LoginPageForm form = new LoginPageForm();
            form.setVisible(true);
        });
        try {
            // Registering the MySQL driver
            Class.forName("com.mysql.jdbc.Driver");

            // Getting the connection
            String mysqlUrl = "jdbc:mysql://localhost:3306/libraryDB";
            Connection con = DriverManager.getConnection(mysqlUrl, "root", "");

            // Initialize the script runner
            ScriptRunner sr = new ScriptRunner(con);

            // Creating a reader object
            FileReader fileReader = new FileReader("projectSQL.sql");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Running the script
            sr.runScript(bufferedReader);

            // Closing the reader and connection
            bufferedReader.close();
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}