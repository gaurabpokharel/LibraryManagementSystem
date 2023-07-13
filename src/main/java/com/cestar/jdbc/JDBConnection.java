package com.cestar.jdbc;

import java.sql.*;

/**
 * @author
 * @project LibraryManagementProject
 * @since 7/13/2023
 **/
public class JDBConnection {
    Connection con ;
    public Connection setUpConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String mysqlUrl = "jdbc:mysql://localhost:3306/libraryDB";
            Connection con = DriverManager.getConnection(mysqlUrl, "root", "");
            if (con != null) {
                System.out.println("Database connected!");
            }
            return con;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error connecting to the database. Please check connection.");
            e.printStackTrace();
        }
        if (con == null) {
            System.out.println("Database not connected. Please check connection.");
        }
        return con;
    }
}
