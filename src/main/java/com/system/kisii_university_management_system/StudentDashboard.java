package com.system.kisii_university_management_system;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StudentDashboard  implements Initializable{


    @FXML
   public Label nameLabel;
    @FXML
    public Label courseLabel;

    @FXML
    public  Label courseIDLabel;

    @FXML
    public Button logoutBtn;
    @FXML
    public Label feesPayableLabel;

    @FXML
   public Label errorAlert;

    @FXML
    public BorderPane mainBorderPane;




    @FXML
    public  CheckBox selectAll;
    @FXML
    public  TableView< Units> unitsTable;
    @FXML
    public TableColumn<Units , String> unitCode;

    @FXML
    public TableColumn<Units , String> unitName;

    @FXML
    public TableColumn<Units , String> unitDesc;

    @FXML
    public  TableColumn<Units , String> selectUnit;

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
    String studentID;




    ObservableList <Units> units = FXCollections.observableArrayList();
    // Fetching available table records

    ObservableList < Units> unitTableRecords = FXCollections.observableArrayList();

    public void getStudentIDtxtField(TextField textField){
        studentID = textField.getText();


    }



    public ObservableList<Units> getUnits(){



        try {

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connectDB = databaseConnection.getConnection();
            String sql1 = "SELECT  Unit_Code , Unit_Name , Unit_Desc , Selected FROM `Course_Units` WHERE Course_ID IN ("
                    +"SELECT Course_ID FROM `Courses` WHERE Course_Name  IN("+"SELECT Course_Name FROM Courses WHERE std_ID ='"+studentID+"'"+
                    ")"+")";


            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            while (resultSet.next()){
                Units unit = new Units(
                        resultSet.getString("Unit_Code"),
                        resultSet.getString("Unit_Name"),
                        resultSet.getString("Unit_Desc"),
                        resultSet.getString("Selected"));
                units.add(unit);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return units;
    }



    // Populate the Units table
    public void unitTablePopulate(){
        unitCode.setCellValueFactory(new PropertyValueFactory<Units,String>("unitCode"));
        unitName.setCellValueFactory(new PropertyValueFactory<Units,String>("unitName"));
        unitDesc.setCellValueFactory(new PropertyValueFactory<Units,String>("unitDesc"));
        selectUnit.setCellValueFactory(new PropertyValueFactory<Units, String>("selectUnit"));
        unitsTable.setItems((getUnits()));



    }
// Select All method that checks all the units

    public void selectAllOnClick(){
        selectAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                System.out.println("Selected all selected");

                unitTableRecords = unitsTable.getItems();
                System.out.println(unitTableRecords.get(1).getSelectUnit().isSelected());
                for (Units unitTableRecord: unitTableRecords) {
                    if(selectAll.isSelected()){
                        unitTableRecord.getSelectUnit().setSelected(true);
                    }else {
                        unitTableRecord.getSelectUnit().setSelected(false);
                    }

                }
            }
        });

    }



    // Register selected units
    public void  registerUnitsBtnOnclick(){
        Integer countNumberOfUnitsRegistered  = 0;
        unitTableRecords = unitsTable.getItems();
        Integer numberOfRecords = unitTableRecords.size();

        for(int i =0 ; i<numberOfRecords ; i++){
            if(unitTableRecords.get(i).getSelectUnit().isSelected()){

                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connectDB = databaseConnection.getConnection();


                String status = "pending";
                String insertData = "INSERT INTO `Register_Units`(`Std_ID`,`Unit_Name`,`Units_Status`) VALUES " +
                        "('" + studentID + "','" + unitTableRecords.get(i).getUnitName() + "','" + status+ "')";
                try {
                    Statement statement = connectDB.createStatement();
                    statement.executeUpdate(insertData);

                } catch (Exception e) {
                    unitRegistrationErrorAlert();
                    e.printStackTrace();
                    break;

                }
            }

        }
        if(countNumberOfUnitsRegistered == numberOfRecords)
        {
            unitRegistrationSuccessAlert();
        }


    }
    //  Unit registration request sent successfully
    public void unitRegistrationSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Success");
        alert.setContentText("Request on unit registration has been sent successfully ");
        alert.showAndWait();
    }

    public void unitRegistrationErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error ");
        alert.setContentText("Request on unit registration was already sent waiting for approval ");
        alert.showAndWait();
    }

    public void displayStudentDetails(String name, String course, String courseID, Double feesPayable) {

   nameLabel.setText(name);
   courseLabel.setText(course);
   courseIDLabel.setText(courseID);
   feesPayableLabel.setText(String.valueOf(feesPayable));

    }



    // Populate the units table on load
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            Platform.runLater(()->{
            unitTablePopulate();
            selectAllOnClick();
        });





    }

    public void viewCurricullumBtnOnClick(ActionEvent event) throws IOException{


    }

    public void unitStatusBtnOnClick(ActionEvent event) throws IOException {


        Stage stage = (Stage)unitStatusBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UnitRegistrationStatus.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        UnitRegistrationStatus unitRegistrationStatus=loader.getController();
        unitRegistrationStatus.getStudentID(studentID);
        newStage.setTitle("Unit Status");
        newStage.setScene(scene);
        newStage.show();
    }
@FXML
public Button viewTranscriptBtn;

    @FXML
    public  Button unitStatusBtn;

    @FXML
    public Button viewCurricullumBtn;
    public void viewTranscriptBtnOnClick(ActionEvent event) throws IOException {

        Stage stage = (Stage)viewTranscriptBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transcriptView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TranscriptView transcriptView = loader.getController();
        transcriptView.getStudentID(studentID);
        newStage.setTitle("Transcript");
        newStage.setScene(scene);
        newStage.show();

    }
}


//
//    //load the Transcript  screen
//    FXMLloader object = new FXMLloader();
//    Pane view = object.getPage("adminManagerMenu");
//        mainBorderPane.setCenter(view);




