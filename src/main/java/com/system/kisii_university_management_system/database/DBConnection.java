package com.system.kisii_university_management_system.database;

import java.sql.*;

public class DBConnection {
    public  Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/Student_Registration_System";
        String user = "root";
        String pass = "T33nwo!f";
        return DriverManager.getConnection(url, user, pass);
    }
}
