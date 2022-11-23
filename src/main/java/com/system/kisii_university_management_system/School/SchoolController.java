package com.system.kisii_university_management_system.School;


import com.system.kisii_university_management_system.database.DBConnection;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;


public class SchoolController implements Initializable {

    private final DBConnection database = new DBConnection();
//    private final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private final Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    private ResultSet result;
    private String sqlQuery;
    @FXML
    private ComboBox<String> fieldStdCourse;
    @FXML
    private TextField fieldYearOfStudy;
    @FXML
    TableView<StudentsTable> stdTableView;
    @FXML
    TableColumn<StudentsTable, String> colStdID;
    @FXML
    TableColumn<StudentsTable, String> colStdName;
    @FXML
    TableColumn<StudentsTable, String> colStdEmail;
    @FXML
    TableColumn<StudentsTable, String> colCourse;
    @FXML
    TableColumn<StudentsTable, Integer> colYear;
    @FXML
    TableColumn<StudentsTable, String> colStatus;

    // Transcript Table

    @FXML
    TableView<TranscriptTable> transcriptTableView;
    @FXML
    TableColumn<TranscriptTable, String> colUnitCode;
    @FXML
    TableColumn<TranscriptTable, String> colUnitName;
    @FXML
    TableColumn<TranscriptTable, Double> colGPA;

    @FXML
    private TextField fieldSearch;
    @FXML
    private TextField fieldGetTranscript;


    //Searching For Student Information
    public void searchStudent() throws SQLException {
        if (fieldSearch.getText().isEmpty()) {
            informationAlert.setContentText("Kindly Enter Student ID!");
            informationAlert.show();
        }
        sqlQuery = "Select * from Student where Std_ID LIKE'%" + fieldSearch.getText() + "%';";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        if (result.next()) {
            stdTableView.setItems(getStudents(sqlQuery));
        } else {
            informationAlert.setContentText("No Student Found!");
            informationAlert.show();
        }
    }
    // Get Class List && || Enrollment
    public void getList() throws SQLException {
        Statement executeSql = database.getConnection().createStatement();
        if (fieldStdCourse.getSelectionModel().getSelectedItem() == null && fieldYearOfStudy.getText().isEmpty()) {
            informationAlert.setContentText("Kindly Select Course ID or Enter Year Of Study!");
            informationAlert.show();
        }
        else if (fieldStdCourse.getSelectionModel().getSelectedItem() != null && fieldYearOfStudy.getText().isEmpty()){
            sqlQuery = "Select * from Student where Course_ID='"+fieldStdCourse.getSelectionModel().getSelectedItem()+"';";
            result = executeSql.executeQuery(sqlQuery);
            if (result.next()) {
                stdTableView.setItems(getStudents(sqlQuery));

            } else {
                informationAlert.setContentText("No Student Found!");
                informationAlert.show();
            }
        }
        else if(fieldStdCourse.getSelectionModel().getSelectedItem() == null && !fieldYearOfStudy.getText().isEmpty())
        {
            sqlQuery = "Select * from Student where YOS='" + fieldYearOfStudy.getText()+"';";
            result = executeSql.executeQuery(sqlQuery);
            if (result.next()) {
               stdTableView.setItems(getStudents(sqlQuery));

            } else {
                informationAlert.setContentText("No Student Found!");
                informationAlert.show();
            }
        }
        else if(fieldStdCourse.getSelectionModel().getSelectedItem() != null && !fieldYearOfStudy.getText().isEmpty()){
            sqlQuery = "Select * from Student where Course_ID='"+fieldStdCourse.getSelectionModel().getSelectedItem()+"' " +
                    "and YOS='"+fieldYearOfStudy.getText()+"';";
            result = executeSql.executeQuery(sqlQuery);
            if (result.next()) {
                stdTableView.setItems(getStudents(sqlQuery));
            } else {
                informationAlert.setContentText("No Student Found!");
                informationAlert.show();
            }
        }

    }

    //Refreshing student table

    public void refresh() throws SQLException {
        stdTableView.setItems(getStudents(null));
    }

    //Get Transcript Table;

    public void getTranscriptTable() throws SQLException {
        if (fieldGetTranscript.getText().isEmpty()) {
            informationAlert.setContentText("Kindly Enter Student ID!");
            informationAlert.show();
        }
        else {
            sqlQuery = "Select Unit_Results.Unit_Code, Unit_Results.GPA, Course_Units.Unit_Name from Unit_Results JOIN " +
                    "Course_Units ON Unit_Results.Unit_Code = Course_Units.Unit_Code where Unit_Results.Std_ID LIKE"+
                    "'%"+fieldGetTranscript.getText()+"%';";
            transcriptTableView.setItems(getTranscript(sqlQuery));
        }
    }


    public ObservableList<String> getCourses() throws SQLException{
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String sqlQuery = "Select * from Courses;";
        ResultSet result = statement.executeQuery(sqlQuery);
        ObservableList<String> courses = FXCollections.observableArrayList();
        while (result.next()){
            courses.add(result.getString("course_ID"));
        }
        return courses;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fieldStdCourse.setItems(getCourses());
            StdTable();
            transcriptTable();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<StudentsTable> getStudents(String sqlParamsQuery) throws SQLException{
        Optional<String> sqlParamQuery = Optional.ofNullable(sqlParamsQuery);
        if(sqlParamQuery.isPresent()){
            sqlQuery = sqlParamsQuery;
        }
        else{

        sqlQuery = "Select * from Student;";
        }
        ObservableList<StudentsTable> studentTableData = FXCollections.observableArrayList();

        ResultSet result = database.getConnection().createStatement().executeQuery(sqlQuery);
        while (result.next()){
            studentTableData.add(new StudentsTable(
                    result.getString("Std_ID"),
                    result.getString("Std_Name"),
                    result.getString("Std_Email"),
                    result.getString("Course_ID"),
                    result.getInt("YOS"),
                    result.getString("Status")

            ));
        }
        return  studentTableData;
    }
    // get Transcript
    private ObservableList<TranscriptTable> getTranscript(String sqlParamsQuery) throws SQLException{
        Optional<String> sqlParamQuery = Optional.ofNullable(sqlParamsQuery);
        if(sqlParamQuery.isPresent()){
            sqlQuery = sqlParamsQuery;
        }
        ObservableList<TranscriptTable> transcriptTableData = FXCollections.observableArrayList();
        if(!fieldGetTranscript.getText().isEmpty()){
            ResultSet result = database.getConnection().createStatement().executeQuery(sqlQuery);
            if(!result.next()){
                informationAlert.setContentText("No Student Transcript Found!");
                informationAlert.setHeaderText("No Records Found");
                informationAlert.show();
            }
            else{
                while (result.next()){
                    transcriptTableData.add(new TranscriptTable(
                            result.getString("Unit_Code"),
                            result.getString("Unit_Name"),
                            result.getDouble("GPA")

                    ));
                }
            }


        }


        return  transcriptTableData;
    }
    // Std Table
    public void StdTable() throws SQLException {
        colStdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStdEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        stdTableView.setItems(getStudents(null));
    }
    //Transcript Table

    public void transcriptTable() throws SQLException {
        colUnitCode.setCellValueFactory(new PropertyValueFactory<>("unitCode"));
        colUnitName.setCellValueFactory(new PropertyValueFactory<>("unitName"));
        colGPA.setCellValueFactory(new PropertyValueFactory<>("GPA"));

        transcriptTableView.setItems(getTranscript(null));
    }


//    public ArrayList<String> getAdmissionDetails() throws SQLException{
//        String id = "ADM-001";
//        sqlQuery = "Select ID, Name from admissions where ID='"+id+"';";
//        result = database.getConnection().createStatement().executeQuery(sqlQuery);
//        ArrayList<String> admDetails = new ArrayList<>();
//        while (result.next()){
//            admDetails.add(result.getString("ID"));
//            admDetails.add(result.getString("Name"));
//        }
//        return admDetails;
//    }

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
}
