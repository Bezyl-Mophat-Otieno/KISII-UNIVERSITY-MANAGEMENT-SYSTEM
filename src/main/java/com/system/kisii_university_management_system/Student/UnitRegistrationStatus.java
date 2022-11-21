package com.system.kisii_university_management_system.Student;

import com.system.kisii_university_management_system.database.DBConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UnitRegistrationStatus implements Initializable{
    @FXML
    public TableColumn<UnitStatus,String> Unit_Name;
    @FXML
    public TableColumn<UnitStatus, String> Unit_Code;

    @FXML
    public TableColumn<UnitStatus,String > Units_Status;

    @FXML
    public TableView<UnitStatus> unitStatusTable;
    private final DBConnection database = new DBConnection();



    ObservableList<UnitStatus> unitStatuses = FXCollections.observableArrayList();
    String studentID;
    

    //Passing the student ID from the students Dashboard
    public void getStudentID(String studentIdentification){
        studentID = studentIdentification;
    }


    public ObservableList<UnitStatus> getUnitStatus(){

        try {

            Connection connectDB = database.getConnection();
            String sql1 = "SELECT Unit_Name, Unit_Code ,Units_Status  FROM `Register_Units` WHERE Std_ID='"+studentID+"'";


            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            while (resultSet.next()){
                UnitStatus unitStatus = new UnitStatus(
                        resultSet.getString("Unit_Name"),
                        resultSet.getString("Unit_Code"),
                        resultSet.getString("Units_Status")
                );
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

            Unit_Code.setCellValueFactory(new PropertyValueFactory<>("unitCode"));
            Unit_Name.setCellValueFactory(new PropertyValueFactory<>("unitName"));
            Units_Status.setCellValueFactory(new PropertyValueFactory<>("unitsStatus"));
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
    public void homeBtnOnClick (ActionEvent event) throws IOException, SQLException {
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

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/student/studentDashboard.fxml"));
        Parent root = fxmlLoader.load();
        StudentDashboard studentDashboard = fxmlLoader.getController();
        studentDashboard.getStudentIDtxtField(studentID);
        Scene scene = new Scene(root);
        Stage newstage = new Stage();
        newstage.setScene(scene);
        newstage.show();



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(this::initializeUnitStatusTable);

    }

    public void printExamCard(ActionEvent event) {
    }
}
