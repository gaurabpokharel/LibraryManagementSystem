package com.cestar.book;

import com.cestar.jdbc.JDBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignBook extends JFrame {
    private JComboBox<String> bookISBNComboBox;
    private JComboBox<String> userEmailComboBox;

    public AssignBook(String userName) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Assign Book");

        // Create form components
        JLabel bookISBNLabel = new JLabel("Book ISBN:");
        bookISBNComboBox = new JComboBox<>();
        bookISBNComboBox.setEditable(true);
        bookISBNComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedISBN = (String) bookISBNComboBox.getSelectedItem();
                // Do something with the selected ISBN
            }
        });

        JLabel userEmailLabel = new JLabel("User Email:");
        userEmailComboBox = new JComboBox<>();
        userEmailComboBox.setEditable(true);
        userEmailComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEmail = (String) userEmailComboBox.getSelectedItem();
                // Do something with the selected email
            }
        });

        // Create a panel for the form
        int vgap = 10;
        int hgap = 10;
        JPanel formPanel = new JPanel(new GridLayout(2, 2, vgap, hgap));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        formPanel.add(bookISBNLabel);
        formPanel.add(bookISBNComboBox);
        formPanel.add(userEmailLabel);
        formPanel.add(userEmailComboBox);

        // Create a button for submitting the form
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookISBN = (String) bookISBNComboBox.getSelectedItem();
                String userEmail = (String) userEmailComboBox.getSelectedItem();
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

        bookISBNComboBox.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    String enteredText = (String) bookISBNComboBox.getEditor().getItem();
                    try {
                        List<String> isbnList = getMatchingISBNs(enteredText);
                        updateISBNComboBox(isbnList);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        userEmailComboBox.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    String enteredText = (String) userEmailComboBox.getEditor().getItem();
                    try {
                        List<String> emailList = getMatchingEmails(enteredText);
                        updateEmailComboBox(emailList);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
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

    private List<String> getMatchingISBNs(String text) throws SQLException {
        List<String> isbnList = new ArrayList<>();
        JDBConnection jdbConnection = new JDBConnection();
        Connection con = jdbConnection.setUpConnection();
        PreparedStatement ps = con.prepareStatement("SELECT isbn FROM books WHERE isbn LIKE ?");
        ps.setString(1, "%" + text + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String isbn = rs.getString("isbn");
            isbnList.add(isbn);
        }
        rs.close();
        ps.close();
        con.close();
        return isbnList;
    }

    private List<String> getMatchingEmails(String text) throws SQLException {
        List<String> emailList = new ArrayList<>();
        JDBConnection jdbConnection = new JDBConnection();
        Connection con = jdbConnection.setUpConnection();
        PreparedStatement ps = con.prepareStatement("SELECT email FROM user WHERE email LIKE ?");
        ps.setString(1, "%" + text + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String email = rs.getString("email");
            emailList.add(email);
        }
        rs.close();
        ps.close();
        con.close();
        return emailList;
    }

    private void updateISBNComboBox(List<String> isbnList) {
        bookISBNComboBox.removeAllItems();
        for (String isbn : isbnList) {
            bookISBNComboBox.addItem(isbn);
        }
        bookISBNComboBox.showPopup();
        if (isbnList.size() > 0) {
            bookISBNComboBox.getEditor().setItem(isbnList.get(0));
            bookISBNComboBox.getEditor().selectAll();
        }
    }

    private void updateEmailComboBox(List<String> emailList) {
        userEmailComboBox.removeAllItems();
        for (String email : emailList) {
            userEmailComboBox.addItem(email);
        }
        userEmailComboBox.showPopup();
        if (emailList.size() > 0) {
            userEmailComboBox.getEditor().setItem(emailList.get(0));
            userEmailComboBox.getEditor().selectAll();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AssignBook assignBook = new AssignBook("YourUserName");
                assignBook.setVisible(true);
            }
        });
    }
}
