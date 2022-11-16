package com.system.kisii_university_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class updateStudentDetails implements Initializable {


    @FXML
    public TextField CourseTxtField;

    @FXML
    public TextField StatusTxtField;

    @FXML
    public TextField Std_EmailTxtField;

    @FXML
    public TextField Std_IDTxtField;

    @FXML
    public TextField Std_NameTxtField;

    @FXML
    public PasswordField Std_PasswordTxtField;

    @FXML
    public TextField YOSTxtField;

    @FXML
    public Label frontEndErrorAlert;

    @FXML
    public Button homeBtn;

    //update data processing
    @FXML
    public void updateStudentDataBtnOnClick(ActionEvent event) {

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connectDB = databaseConnection.getConnection();
        String updateData = "UPDATE `Student` SET `Std_ID` ='"+Std_IDTxtField.getText()+"',`Std_Name`='"+Std_NameTxtField.getText()+"',`Std_Email`='"+Std_EmailTxtField.getText()+"',`Password`='"+Std_PasswordTxtField.getText()+"',`Course`='"+CourseTxtField.getText()+"'" +
                ",`Status`='"+StatusTxtField.getText()+"'  WHERE `Std_ID` = '"+Std_IDTxtField.getText()+"'";


        try {
            if (Std_IDTxtField.getText().isEmpty() || Std_NameTxtField.getText().isEmpty() || Std_EmailTxtField.getText().isEmpty()
                    || Std_PasswordTxtField.getText().isEmpty() || CourseTxtField.getText().isEmpty() || YOSTxtField.getText().isEmpty() || StatusTxtField.getText().isEmpty()) {

                frontEndErrorAlert.setText("Kindly provide all information about a student");


            } else {
                System.out.println(updateData);
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(updateData);
                updateSuccessAlert();

            }
        } catch (Exception e) {
            frontEndErrorAlert.setText("Make sure your data types Match those stored in the database");
            e.printStackTrace();

        }

    }
    @FXML
  public   BorderPane studentDetailsPane;
    //Home button
    @FXML
    public void homeBtnOnClick (ActionEvent event){
        FXMLloader object = new FXMLloader();
        Pane view = object.getPage("AdmissionsOffice");
        studentDetailsPane.setCenter(view);
    }

    // setIDtextfield method


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //Initialize the student Data on the form
    public void filledFormData ( String ID , String Name , String Email , String Course , Integer YOS, String Status ){
Std_IDTxtField.setText(ID);
Std_NameTxtField.setText(Name);
Std_EmailTxtField.setText(Email);
CourseTxtField.setText(Course);
YOSTxtField.setText(String.valueOf(YOS));
StatusTxtField.setText(Status);
    }
    public void updateSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Success ");
        alert.setContentText("Student Info Successfully Updated ");
        if (alert.showAndWait().get() == ButtonType.OK) {

            FXMLloader object = new FXMLloader();
            Pane view = object.getPage("AdmissionsOffice");
            studentDetailsPane.setCenter(view);

        }
    }

}
