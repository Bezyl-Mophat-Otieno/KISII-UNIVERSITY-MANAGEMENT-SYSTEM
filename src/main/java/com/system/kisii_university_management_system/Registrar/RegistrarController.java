package com.system.kisii_university_management_system.Registrar;

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


public class RegistrarController implements Initializable {

    private final DBConnection database = new DBConnection();
    private boolean isNewButtonClicked;
    private  boolean isEditButtonClicked;
//    private final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private final Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    private ResultSet result;
    private String sqlQuery;
    @FXML
    private TextField fieldCourseID;
    @FXML
    private  TextField fieldCourseName;
    @FXML
    private TextField fieldCourseDuration;
    @FXML
    private TextField fieldCostPrice;
    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    TableView<CoursesTable> courseTableView;
    @FXML
    TableColumn<CoursesTable, String> colCourseID;
    @FXML
    TableColumn<CoursesTable, String> colCourseName;
    @FXML
    TableColumn<CoursesTable, Integer> colCourseDuration;
    @FXML
    TableColumn<CoursesTable, Double> colCostPrice;

    @FXML
    private TextField fieldSearch;

    public void setAdd(){
        setAllEnable();
        isNewButtonClicked = true;
        isEditButtonClicked = false;
    }

    //Save button functionality
    public void save() throws SQLException {
        if(isNewButtonClicked){
            registerCourse();
        }
        else if(isEditButtonClicked){
            updateCourse();
        }
    }
    //register Student
    public void registerCourse() throws SQLException {
        if(!checkFieldsEmpty()){
            Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            sqlQuery = "Select * from Courses where Course_ID='"+fieldSearch.getText()+"';";
            result = database.getConnection().createStatement().executeQuery(sqlQuery);
            if(result.next()){
                errorAlert.setContentText("Course Already Exists");

            }
            else{
                sqlQuery = "Insert into Courses values('"+fieldCourseID.getText()+"','"+fieldCourseName.getText()+"'," +
                        "'"+fieldCourseDuration.getText()+"','"+fieldCostPrice.getText()+"');";
                int result = statement.executeUpdate(sqlQuery);
                System.out.println(result);
                if(result == 1){
                    informationAlert.setContentText("Course Added Successfully!");
                    informationAlert.show();
                    clearAll();
                    isEditButtonClicked = false;
                    isNewButtonClicked = false;
                }
                else{
                    errorAlert.setHeaderText("Error encountered!");
                    errorAlert.show();
                }
            }

        }
        else{
            errorAlert.setContentText("All fields are required!");
            errorAlert.show();
        }
    }

    //Update Student Information

    public void updateCourse() throws SQLException{
        sqlQuery ="Update Courses set Course_ID='"+fieldCourseID.getText()+"',courseName='"+fieldCourseName.getText()+"'," +
            "courseDuration='"+fieldCourseDuration.getText()+"',costPrice='"+fieldCostPrice.getText()+"' " +
                "where Course_ID='"+fieldCourseID.getText()+"';";

        int result =  database.getConnection().createStatement().executeUpdate(sqlQuery);
        System.out.println(result);
        if(result == 1){
            informationAlert.setContentText("Course Updated Successfully");
            informationAlert.setHeaderText("Success");
            informationAlert.show();
            isEditButtonClicked = false;
            isNewButtonClicked = false;
        }
        else {
            errorAlert.setContentText("Error has occurred");
            errorAlert.show();
        }
        clearAll();
    }

    //Deleting Student Information
    public void deleteCourse() throws SQLException {
        CoursesTable courseTable = courseTableView.getSelectionModel().getSelectedItem();

        if(courseTable == null){
            informationAlert.setContentText("Kindly select a record to delete!");
            informationAlert.show();
        }
        else{
            sqlQuery = "Delete from Courses where Course_ID= '"+courseTable.getCourseId()+"';";

            database.getConnection().createStatement().executeUpdate(sqlQuery);
            courseTableView.setItems(getCourses(null));
        }
        assert courseTable != null;

    }

    //Editing Student Information
    public void editCourse() throws SQLException{
        isNewButtonClicked = false;
        isEditButtonClicked = true;
        CoursesTable coursesTable = courseTableView.getSelectionModel().getSelectedItem();
        if(coursesTable == null){
            informationAlert.setContentText("Kindly select a record to Edit!");
            informationAlert.show();
        }
        else{
            setAllEnable();
            sqlQuery = "Select * from Courses where Course_ID='"+coursesTable.getCourseId()+"';";

            result = database.getConnection().createStatement().executeQuery(sqlQuery);
            while (result.next()){

            fieldCourseID.setText(result.getString("Course_ID"));
            fieldCourseName.setText(result.getString("courseName"));
            fieldCourseDuration.setText(result.getString("courseDuration"));
            fieldCostPrice.setText(result.getString("costPrice"));
            }
            isEditButtonClicked = true;
            isNewButtonClicked = false;
        }
    }

    //Searching For Student Information
    public void searchCourse() throws SQLException{
        if(fieldSearch.getText().isEmpty()){
            informationAlert.setContentText("Kindly Enter Course ID!");
            informationAlert.show();
        }
        sqlQuery = "Select * from Courses where Course_ID LIKE'%"+fieldSearch.getText()+"%';";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        if(result.next()){
            courseTableView.setItems(getCourses(sqlQuery));
        }
        else{
            informationAlert.setContentText("No Course Found!");
            informationAlert.show();
        }
    }
    public void cancel(){
        setAllDisable();
    }

    //Refreshing student table

    public void refresh() throws SQLException {
        courseTableView.setItems(getCourses(null));
    }

    private void setAllEnable() {
        fieldCourseID.setDisable(false);
        fieldCourseName.setDisable(false);
        fieldCourseDuration.setDisable(false);
        fieldCostPrice.setDisable(false);
        saveBtn.setDisable(false);
        deleteBtn.setDisable(false);
    }

    private void setAllDisable() {
        fieldCourseID.setDisable(true);
        fieldCourseName.setDisable(true);
        fieldCourseDuration.setDisable(true);
        fieldCostPrice.setDisable(true);
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);

    }
    public void clearAll(){
        fieldCourseID.setText("");
        fieldCourseName.setText("");
        fieldCourseDuration.setText("");
        fieldCostPrice.setText("");
    }

    public boolean checkFieldsEmpty(){
        return fieldCourseID.getText().isEmpty() || fieldCourseName.getText().isEmpty() ||
            fieldCourseName.getText().isEmpty() || fieldCourseDuration.getText().isEmpty() ||
            fieldCostPrice.getText().isEmpty() ;

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setAllDisable();
            getCourses(null);
            courseTable();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ObservableList<CoursesTable> getCourses(String sqlParamsQuery) throws SQLException{
        Optional<String> sqlParamQuery = Optional.ofNullable(sqlParamsQuery);
        if(sqlParamQuery.isPresent()){
            sqlQuery = sqlParamsQuery;
        }
        else{

        sqlQuery = "Select * from Courses;";
        }
        ObservableList<CoursesTable> courseTableData = FXCollections.observableArrayList();

        ResultSet result = database.getConnection().createStatement().executeQuery(sqlQuery);
        while (result.next()){
            courseTableData.add(new CoursesTable(
                    result.getString("Course_ID"),
                    result.getString("courseName"),
                    result.getInt("courseDuration"),
                    result.getDouble("costPrice")

            ));
        }
        return  courseTableData;
    }

    public void courseTable() throws SQLException {
        colCourseID.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colCourseDuration.setCellValueFactory(new PropertyValueFactory<>("courseDuration"));
        colCostPrice.setCellValueFactory(new PropertyValueFactory<>("costPrice"));
        courseTableView.setItems(getCourses(null));
    }
//
//
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
