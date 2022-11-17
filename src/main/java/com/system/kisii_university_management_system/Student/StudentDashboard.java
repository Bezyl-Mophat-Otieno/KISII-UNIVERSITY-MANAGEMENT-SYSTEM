package com.system.kisii_university_management_system.Student;

import com.system.kisii_university_management_system.database.DBConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
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

//    Alert unitError = new Alert(Alert.AlertType.ERROR);

    @FXML
    public Button logoutBtn;
    @FXML
    public Label feesPayableLabel;

    @FXML
   public Label errorAlert;

    @FXML
    public BorderPane mainBorderPane;

    private final DBConnection database = new DBConnection();


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
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/system/kisii_university_management_system/login/AppLogin.fxml"));
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

    public void getStudentIDtxtField(String textField) throws SQLException {
        studentID = textField;
        studentDetails(textField);

    }



    public ObservableList<Units> getUnits(){



        try {
            Connection connectDB = database.getConnection();
            String sql1 = "SELECT  Unit_Code , Unit_Name , Unit_Desc , Selected FROM `Course_Units` WHERE Course_ID=("
                    +"SELECT courseID FROM `Courses` WHERE CourseID=(SELECT Course FROM Student " +
                    "WHERE std_ID ='"+studentID+"'"+
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
        unitCode.setCellValueFactory(new PropertyValueFactory<>("unitCode"));
        unitName.setCellValueFactory(new PropertyValueFactory<>("unitName"));
        unitDesc.setCellValueFactory(new PropertyValueFactory<>("unitDesc"));
        selectUnit.setCellValueFactory(new PropertyValueFactory<>("selectUnit"));
        unitsTable.setItems(getUnits());



    }
// Select All method that checks all the units

    public void selectAllOnClick(){
        selectAll.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            System.out.println("Selected all selected");

            unitTableRecords = unitsTable.getItems();
            System.out.println(unitTableRecords.get(1).getSelectUnit().isSelected());
            for (Units unitTableRecord: unitTableRecords) {
                unitTableRecord.getSelectUnit().setSelected(selectAll.isSelected());

            }
        });

    }



    // Register selected units
    public void  registerUnitsBtnOnclick() throws SQLException {
        Integer countNumberOfUnitsRegistered  = 0;
        unitTableRecords = unitsTable.getItems();
        Integer numberOfRecords = unitTableRecords.size();
        Connection connectDB = database.getConnection();
        for (Units unitTableRecord : unitTableRecords) {
            System.out.println(unitTableRecord.getUnitCode());
            if (unitTableRecord.getSelectUnit().isSelected()) {
                System.out.println(unitTableRecord.getUnitCode());
                String checkRegisteredUnit = "Select Std_ID, Unit_Name from Register_Units " +
                        "where Std_ID='"+studentID+"' and Unit_Code='"+unitTableRecord.getUnitCode()+"'";
                boolean isRegistered = connectDB.createStatement().executeQuery(checkRegisteredUnit).next();
                if(isRegistered){
                    return;
                }

                String status = "pending";
                String insertData = "INSERT INTO `Register_Units`(`Std_ID`,`Unit_Code`,`Unit_Name`,`Units_Status`) VALUES " +
                        "('" + studentID + "','" + unitTableRecord.getUnitCode() + "','" + unitTableRecord.getUnitName()
                        + "','" + status + "')";
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
        if(countNumberOfUnitsRegistered.equals(numberOfRecords))
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

//    public void displayStudentDetails()
//            throws SQLException {
//       studentDetails();
//       nameLabel.setText(name);
//       courseLabel.setText(course);
//       courseIDLabel.setText(courseID);
//       feesPayableLabel.setText(String.valueOf(feesPayable));
//
//    }

    public void studentDetails(String studentID) throws SQLException {


        Connection connectDB = database.getConnection();
        String sql = "SELECT Student.Std_Name , Courses.courseName , Courses.courseID , costPrice FROM Student JOIN Courses " +
                "ON Student.Course = Courses.courseID WHERE Student.Std_ID='"+studentID+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                nameLabel.setText(resultSet.getString("Std_Name"));
                courseLabel.setText(resultSet.getString("courseName"));
                courseIDLabel.setText(resultSet.getString("courseID"));
                feesPayableLabel.setText(String.valueOf(resultSet.getDouble("costPrice")));

            }
        }catch (Exception e){
            e.printStackTrace();
        }

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
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/student/UnitRegistrationStatus.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        UnitRegistrationStatus unitRegistrationStatus = loader.getController();
        unitRegistrationStatus.getStudentID(studentID);
        newStage.setTitle("Unit Status");
        newStage.setScene(scene);
        newStage.show();
    }
    @FXML
    public Button viewTranscriptBtn;

    @FXML
    public  Button unitStatusBtn;

    public void viewTranscriptBtnOnClick(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/student/transcriptView.fxml"));
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




