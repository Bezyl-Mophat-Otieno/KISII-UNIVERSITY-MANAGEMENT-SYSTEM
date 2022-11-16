package com.system.kisii_university_management_system;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UnitRegistration implements Initializable {

    @FXML
    public CheckBox selectAll;
    @FXML
    public TableView< Units> unitsTable;
    @FXML
    public   TableColumn<Units , String> unitCode;

    @FXML
    public TableColumn<Units , String> unitName;

    @FXML
    public    TableColumn<Units , String> unitDesc;

    @FXML
    public   TableColumn<Units , String> selectUnit;
    public   String studentID;


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

    // Populate the units table on load
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
                unitTablePopulate();
        selectAllOnClick();
        });





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
        unitTableRecords = unitsTable.getItems();
        Integer numberOfRecords = unitTableRecords.size();
        int countRegisterdUnits=0;
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
                    countRegisterdUnits++;

                } catch (Exception e) {
                    e.printStackTrace();
                    unitRegistrationFailureAlert();
                    System.out.println("failure Executed");
                    break;

                }

            }



        }
        if(countRegisterdUnits == numberOfRecords){
            unitRegistrationSuccessAlert();
            System.out.println("SUccess executed");
        }

    }
    //  Unit registration request sent successfully
    public void unitRegistrationSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Success ");
        alert.setContentText("Request on unit registration has been sent successfully ");
        alert.showAndWait();
    }

    //  Unit registration request sent successfully
    public void unitRegistrationFailureAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Success ");
        alert.setContentText("A unit Can only be registered Once , Unless the COD fails to approve it. ");
        alert.showAndWait();
    }


}
