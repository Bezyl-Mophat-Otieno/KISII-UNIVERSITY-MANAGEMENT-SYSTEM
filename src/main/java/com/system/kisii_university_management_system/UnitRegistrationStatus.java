package com.system.kisii_university_management_system;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UnitRegistrationStatus implements Initializable{

    String name;
    String course;
    String courseID;
    Double feesPayable ;


        @FXML
        public TableColumn<UnitStatus,String> Unit_Name;

        @FXML
        public TableColumn<UnitStatus,String > Units_Status;

        @FXML
        public TableView<UnitStatus> unitStatusTable;



    ObservableList<UnitStatus> unitStatuses = FXCollections.observableArrayList();
    String studentID;
    

    //Passing the student ID from the students Dashboard
    public void getStudentID(String studentIdentification){
        studentID = studentIdentification;
    }


    public ObservableList<UnitStatus> getUnitStatus(){

        try {

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connectDB = databaseConnection.getConnection();
            String sql1 = "SELECT Unit_Name ,Units_Status  FROM `Register_Units` WHERE std_ID='"+studentID+"'";


            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            while (resultSet.next()){
                UnitStatus unitStatus = new UnitStatus();
                unitStatus.setUnitName(resultSet.getString("Unit_Name"));
                unitStatus.setUnitsStatus(resultSet.getString("Units_Status"));
                unitStatuses.add(unitStatus);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return unitStatuses;
    }
// Initializing the transcript Table

    public void initializeUnitStatusTable() {
        try {

            Unit_Name.setCellValueFactory(new PropertyValueFactory<UnitStatus,String>("unitName"));
            Units_Status.setCellValueFactory(new PropertyValueFactory<UnitStatus,String>("unitsStatus"));
            unitStatusTable.setItems(getUnitStatus());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public  BorderPane regStatusPane;

    @FXML
    public  Button homeBtn;

    @FXML
    public void homeBtnOnClick (ActionEvent event) throws IOException {
//
//        APPController appController = new APPController();
//        appController.studentDetails();
//        Stage stage = (Stage)homeBtn.getScene().getWindow();
//        stage.close();
//        Stage newStage = new Stage();
//        // create an FXML Loader instance
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("studentDashboard.fxml"));
//        Parent root = loader.load();
//        //Creating an instance of StudentDashboard
//        StudentDashboard studentDashboard= loader.getController();
//        studentDashboard.getStudentIDtxtField(studentID);
//        studentDashboard.displayStudentDetails(name,course,courseID,feesPayable);
//        Scene scene = new Scene(root);
//        newStage.setTitle("Student Dashboard");
//        newStage.setScene(scene);
//        newStage.show();

        Stage stage = (Stage) homeBtn.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("AppLogin.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage newstage = new Stage();
        newstage.setScene(scene);
        newstage.show();



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(()->{
            initializeUnitStatusTable();
        });

    }
}
