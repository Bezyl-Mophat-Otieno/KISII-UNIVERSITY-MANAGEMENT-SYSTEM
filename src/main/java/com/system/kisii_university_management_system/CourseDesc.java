package com.system.kisii_university_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CourseDesc {
    @FXML
    public TextArea courseDescTextArea;

    @FXML
    public TextField unitIDTextField;
    public String unitDesc;
    @FXML
    public void viewUnitDescBtnOnClick(ActionEvent event) {



        try {

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connectDB = databaseConnection.getConnection();
            String sql1 = "SELECT   Unit_Desc FROM `Course_Units` WHERE Unit_Code='"+unitIDTextField.getText()+"'";
            System.out.println(sql1);


            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            while (resultSet.next()){
                unitDesc = resultSet.getString("Unit_Desc");
                courseDescTextArea.setWrapText(true);
                courseDescTextArea.setText(unitDesc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    public Button logoutBtn;

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
            newStage.show();
        }
    }

}


