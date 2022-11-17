package com.system.kisii_university_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class APPController {
    @FXML
    public Label loginAlerts;
    @FXML
   public Button cancelBtn;

    @FXML
    public Button loginBtn;

    @FXML
    public TextField IDtxtField;
    @FXML
    public TextField passwordTxtField;

    String name;
    String course;
    String courseIDcontolled;
    String courseID;
    Double feesPayable ;






    @FXML
    public void loginBtnOnClick(ActionEvent event) {
        if(IDtxtField.getText().isBlank() || passwordTxtField.getText().isBlank()){
            loginAlerts.setText("Kindly fill in all the Details");

        }else if( IDtxtField.getText().startsWith("IN")){
            studentLoginVerification();

        } else if ( IDtxtField.getText().startsWith("CAD")) {
            CADLoginVerification();
        } else if (IDtxtField.getText().startsWith("BURS")) {
            bursarLoginVerification();
        } else if (IDtxtField.getText().startsWith("ENR")) {
            enrollmentLoginVerification();
        } else if (IDtxtField.getText().startsWith("ADM")) {
            admOfficeLoginVerification();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("User Category Does Not Exist");
            alert.showAndWait();

        }


    }
    @FXML public void cancelBtnOnclick(ActionEvent event){
        Stage stage =(Stage)cancelBtn.getScene().getWindow();
        stage.close();
    }
    // student Login test

    public  void studentLoginVerification (){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String sql = "SELECT COUNT(1) FROM Student WHERE Std_ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){
                    displayStdDashboard();
                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // BURSAR AUTHENTICATION

    public  void bursarLoginVerification (){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String sql = "SELECT COUNT(1) FROM bursar WHERE ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){
                    displayBursarDashboard();
                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //CAD LOGIN Authentication

    public  void CADLoginVerification (){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String sql = "SELECT COUNT(1) FROM courseadvisor WHERE ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){
                    displayCADDashboard();
                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //CAD LOGIN Authentication

    public  void enrollmentLoginVerification (){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String sql = "SELECT COUNT(1) FROM enrollment WHERE ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){
                    displayEnrollmentDashboard();
                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public  void admOfficeLoginVerification (){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String sql = "SELECT COUNT(1) FROM admissions WHERE ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){

                    displayadmDashboard();

                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void studentDetails(){


        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String sql = "SELECT Std_Name , Course_Name , Course_ID , Cost_Price FROM Student std JOIN Courses crs ON std.Std_ID = crs.Std_ID WHERE std.Std_ID='"
                +IDtxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                name = resultSet.getString(1);
                course = resultSet.getString(2);
                courseID = resultSet.getString(3);
                feesPayable = resultSet.getDouble(4);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    // display another scene (student Dashboard )

    public void displayStdDashboard() throws IOException {
        studentDetails();
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("studentDashboard.fxml"));
        Parent root = loader.load();
        //Creating an instance of StudentDashboard
        StudentDashboard studentDashboard= loader.getController();
        studentDashboard.getStudentIDtxtField(IDtxtField);
        studentDashboard.displayStudentDetails(name,course,courseID,feesPayable);
        Scene scene = new Scene(root);
        newStage.setTitle("Student Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }

    //display course Advisors dashbnoard
    public void displayCADDashboard() throws IOException {
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseAdvisorDashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        newStage.setTitle("Course Advisor Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }


    //display course Advisors dashbnoard
    public void displayBursarDashboard() throws IOException {
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bursar.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        newStage.setTitle("Bursar Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }

    public void displayadmDashboard() throws IOException {
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admission.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        newStage.setTitle("Admissions Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }


    //display course Advisors dashbnoard
    public void displayEnrollmentDashboard() throws IOException {
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("enrollment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        newStage.setTitle("Enrollment Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }


    public void displayAdmissionsOfficeDashboard() throws IOException {

        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdmissionsOffice.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        newStage.setTitle("Admissions Office Dashboard");
        newStage.setScene(scene);
        newStage.show();


    }

    @FXML
    public BorderPane mainBorderPane;

    public TextField getIDtxtField() {
        return IDtxtField;
    }
}