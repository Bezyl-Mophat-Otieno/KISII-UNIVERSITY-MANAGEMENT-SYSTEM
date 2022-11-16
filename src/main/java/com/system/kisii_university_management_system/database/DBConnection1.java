package com.system.kisii_university_management_system.database;

import java.sql.*;

public class DBConnection1 {
    public  Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/Mugo_Database";
        String user = "root";
        String pass = "T33nwo!f";
        return DriverManager.getConnection(url, user, pass);
    }
}
