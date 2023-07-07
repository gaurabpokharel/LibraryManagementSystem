package com.cestar.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddUserForm extends JFrame{
	AddUserForm() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Add New Book");

        // Create form components
        JLabel firstNameLabel = new JLabel("First name:");
        JTextField firstNameField = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last name:");
        JTextField lastNameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JLabel genderLabel = new JLabel("Gender:");
        JRadioButton maleRadioButton = new JRadioButton("Male");
        JRadioButton femaleRadioButton = new JRadioButton("Female");
        JRadioButton othersRadioButton = new JRadioButton("Others");        
        ButtonGroup genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femaleRadioButton);
        genderButtonGroup.add(othersRadioButton);
        JPanel genderJPanel = new JPanel();
        genderJPanel.add(maleRadioButton);
        genderJPanel.add(femaleRadioButton);
        genderJPanel.add(othersRadioButton);
        
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        JTextField phoneNumberField = new JTextField(20);
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField(20);

        // Create a panel for the form
        int vgap =10;
        int hgap =10;
        JPanel formPanel = new JPanel(new GridLayout(7, 2,vgap,hgap));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(genderLabel);
        formPanel.add(genderJPanel);
        formPanel.add(phoneNumberLabel);
        formPanel.add(phoneNumberField);
        formPanel.add(addressLabel);
        formPanel.add(addressField);

        // Create a button for submitting the form
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String firstName=firstNameField.getText();
               String lastName = lastNameField.getText();
               String email = emailField.getText();
               String password = new String(passwordField.getPassword());
               String gender = "";
               // Check which gender radio button is selected
               if (maleRadioButton.isSelected()) {
                   gender = "Male";
               } else if (femaleRadioButton.isSelected()) {
                   gender = "Female";
               } else if (othersRadioButton.isSelected()) {
                   gender = "Others";
               }
               String phoneNumber = phoneNumberField.getText();
               String address= addressField.getText();
               try {
            	   Class.forName("com.mysql.jdbc.Driver");
            	   String mysqlUrl = "jdbc:mysql://localhost:3306/libraryDB";
                   Connection con = DriverManager.getConnection(mysqlUrl, "root", "");
                   PreparedStatement ps = con.prepareStatement("insert into user(first_name,last_name,email,password,gender,phone_number,address) values(?,?,?,?,?,?,?)");
                   ps.setString(1, firstName);
                   ps.setString(2, lastName);
                   ps.setString(3, email);
                   ps.setString(4, password);
                   ps.setString(5, gender);
                   ps.setString(6, phoneNumber);
                   ps.setString(7, address);
                   
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





