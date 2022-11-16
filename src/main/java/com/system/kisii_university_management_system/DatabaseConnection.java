package com.system.kisii_university_management_system;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection connection;
    public Connection getConnection() {

        final String DB_URL = "jdbc:mysql://localhost:3307/Student_Registration_System?serverTimezone=UTC";
        final String USER_NAME = "root";
        final String PASSWORD = "T33nwo!f";

        try {
            connection = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

}
