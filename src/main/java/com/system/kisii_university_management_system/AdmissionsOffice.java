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

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdmissionsOffice {
    @FXML
    public Button stdRegisterBtn;

    @FXML
    public  Button searchBtn;

    @FXML
    public Button logoutBtn;


    @FXML
    public TextField Std_NameTxtField;

    @FXML
    public TextField Std_IDTxtField;

    @FXML
    public TextField Std_EmailTxtField;
    @FXML
    public  TextField Std_PasswordTxtField;

    @FXML
    public TextField CourseTxtField;

    @FXML
    public   TextField YOSTxtField;

    @FXML
    public TextField StatusTxtField;

    @FXML
    public TextField searchTxtField;

    @FXML
    public  Label frontEndErrorAlert;


    @FXML
    public Label courseErrorAlert;

    @FXML
    public Label nameErrorAlert;

    @FXML
    public Label statusErrorAlert;

    @FXML
    public Label yosErrorAlert;

    @FXML
    public Label idErrorAlert;



    @FXML
    public void stdRegisterBtnOnClick(ActionEvent event) {

        registerStudent();

    }

    public  String studentID;
    public  String studentName;
    public String studentEmail;
    public  String studentPassword;
    public  String studentCourse;
    public  Integer studentYOS;
    public  String studentStatus;


    //register a student method

    public void registerStudent() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();

        String insertData = "INSERT INTO `Student`(`Std_ID`,`Std_Name`,`Std_Email`,`Password`,`Course`,`YOS`,`Status`) VALUES " +
                "('" + Std_IDTxtField.getText() + "','" + Std_NameTxtField.getText() + "','" + Std_EmailTxtField.getText() + "','" + Std_PasswordTxtField.getText() + "'" +
                ",'" + CourseTxtField.getText() + "','" + YOSTxtField.getText() + "','" + StatusTxtField.getText() + "')";
        try {
            if (Std_IDTxtField.getText().isEmpty() || Std_NameTxtField.getText().isEmpty() || Std_EmailTxtField.getText().isEmpty()
                    || Std_PasswordTxtField.getText().isEmpty() || CourseTxtField.getText().isEmpty() || YOSTxtField.getText().isEmpty() || StatusTxtField.getText().isEmpty()) {

                frontEndErrorAlert.setText("Kindly Make Sure All fields are Filled ! ");


            } else {

                Statement statement = connectDB.createStatement();
                statement.executeUpdate(insertData);
                registrationSuccessAlert();


            }
        } catch (Exception e) {
            frontEndErrorAlert.setText("Make sure your data types Match those stored in the database");
            e.printStackTrace();

        }
    }

    // A method that clears the text fields
    public void clearTextFields() {
        Std_IDTxtField.clear();
        Std_NameTxtField.clear();
        Std_PasswordTxtField.clear();
        Std_EmailTxtField.clear();
        CourseTxtField.clear();
        YOSTxtField.clear();
        StatusTxtField.clear();


    }
    // Registeration successfull alerts

    public void registrationSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Success ");
        alert.setContentText("Student Successfully Registered ");
        if (alert.showAndWait().get() == ButtonType.OK) {
            clearTextFields();
        }
    }
    // A method that initializes the form data for update

    public void updateFormData (){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String sql = "SELECT * FROM `Student` WHERE std_ID='"+searchTxtField.getText()+"'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                studentID=resultSet.getString(1);
                studentName =resultSet.getString(2);
                studentEmail=resultSet.getString(3);
                studentPassword=resultSet.getString(4);
                studentCourse=resultSet.getString(5);
                studentYOS =resultSet.getInt(6);
                studentStatus=resultSet.getString(7);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }











    @FXML
    public  BorderPane admissionsBorderPane;

    @FXML
    public  Label searchAlert;
    @FXML
    public void searchBtnOnClick (ActionEvent event) throws IOException{
        if(searchTxtField.getText().isEmpty()){
            searchAlert.setText("Kindly enter the Student's ID number to get student's information");


        }else {
            updateFormData();
            Stage stage = (Stage) searchBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateStudentForm.fxml"));
            Parent root = loader.load();
            updateStudentDetails updateStudentDetails = loader.getController();
            updateStudentDetails.filledFormData(studentID,studentName,studentEmail,studentCourse,studentYOS,studentStatus);
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        }



    }




    @FXML
    public void logoutBtnOnClick(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to log out ");
        alert.setContentText("Are you sure you want to Log-Out ");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AppLogin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setWidth(1400);
            newStage.setHeight(900);
            newStage.show();
        }

    }
}