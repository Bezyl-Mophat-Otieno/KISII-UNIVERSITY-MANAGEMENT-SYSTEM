package com.system.kisii_university_management_system.Admission;

import com.system.kisii_university_management_system.database.DBConnection;
import com.system.kisii_university_management_system.Enrollment.StudentsTable;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;


public class AdmissionController implements Initializable {

    private final DBConnection database = new DBConnection();
    private boolean isNewButtonClicked;
    private  boolean isEditButtonClicked;
//    private final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private final Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    private ResultSet result;
    private String sqlQuery;
    @FXML
    private TextField fieldStdID;
    @FXML
    private  TextField fieldStdName;
    @FXML
    private TextField fieldStdEmail;
    @FXML
    private PasswordField fieldStdPassword;
    @FXML
    private ComboBox<String> fieldStdCourse;
    @FXML
    private TextField fieldYearOfStudy;
    @FXML
    private ComboBox<String> fieldStatus;
    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteBtn;
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
    TableColumn<StudentsTable, String> colStdPassword;
    @FXML
    TableColumn<StudentsTable, Integer> colYear;
    @FXML
    TableColumn<StudentsTable, String> colStatus;

    @FXML
    private TextField fieldSearch;


    public void setAdd(){
        setAllEnable();
        isNewButtonClicked = true;
    }

    //Save button functionality
    public void save() throws SQLException {
        if(isNewButtonClicked){
         frontEndValidation();
        }
        else if(isEditButtonClicked){
            updateStudent();



        }
    }

    public void insertIntoFeeTable() throws SQLException {
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT Cost_Price FROM Courses WHERE Course_ID IN (SELECT Course_ID FROM Student WHERE Std_ID ='"+fieldStdID.getText()+"'"+")";
        System.out.println(sqlQuery);
        ResultSet result = statement.executeQuery(sqlQuery);
        if (result.next()){
            //Store the fees payable in a variable
            Double feeAmount = result.getDouble("Cost_Price");
            Connection connection1 = database.getConnection();
            Statement statement1 = connection1.createStatement();
            String sqlQuery1="Insert into `billstatement` values('"+fieldStdID.getText()+"','"+feeAmount+"')";
            System.out.println(sqlQuery1);
            int resultInsert = statement.executeUpdate(sqlQuery1);
            if(resultInsert==1){
                    informationAlert.setContentText("Fees Structure ready to be collected from the bursar's office");
                    informationAlert.show();
            }

        }

    }
    //register Student
    public void registerStudent() throws SQLException {
        if(!checkFieldsEmpty()){
            Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            sqlQuery = "Insert into Student values('"+fieldStdID.getText()+"','"+fieldStdName.getText()+"'," +
                "'"+fieldStdEmail.getText()+"','"+fieldStdPassword.getText()+"'," +
                "'"+fieldStdCourse.getSelectionModel().getSelectedItem()+"','"+ parseInt(fieldYearOfStudy.getText())+"',"
                + "'"+fieldStatus.getSelectionModel().getSelectedItem()+"');";
            String sqlQueryInsert = "Insert into `graduationEligible` values('"+fieldStdID.getText()+"','No')";
            int resultInsert = statement.executeUpdate(sqlQueryInsert);
            int result = statement.executeUpdate(sqlQuery);
            System.out.println(resultInsert);
            System.out.println(result);
            if(result == 1 && resultInsert == 1){
                insertIntoFeeTable();
                informationAlert.setContentText("The Student has been registered successfully," +
                        " He/She should proceed to the bursar's Office to pick the fee Structure");
                informationAlert.show();
                clearAll();
            }
            else{
                errorAlert.setHeaderText("Error encountered!");
                errorAlert.show();
            }
        }
        else{
            errorAlert.setContentText("All fields are required!");
            errorAlert.show();
        }
        isNewButtonClicked = true;
        isEditButtonClicked = false;
    }

    //Update Student Information

    public void updateStudent() throws SQLException{
        sqlQuery ="Update Student set Std_ID='"+fieldStdID.getText()+"',Std_Name='"+fieldStdName.getText()+"'," +
            "Std_Email='"+fieldStdEmail.getText()+"',Password='"+fieldStdPassword.getText()+"'," +
            "Course_ID='"+fieldStdCourse.getSelectionModel().getSelectedItem()+"'," +
            "YOS='"+ parseInt(fieldYearOfStudy.getText())+"',"
            + "Status='"+fieldStatus.getSelectionModel().getSelectedItem()+"' " +
                "where Std_ID='"+fieldStdID.getText()+"';";

        int result =  database.getConnection().createStatement().executeUpdate(sqlQuery);
        System.out.println(result);
        if(result == 1){
            insertIntoFeeTable();
            informationAlert.setContentText("Student Updated Successfully");
            informationAlert.setHeaderText("Success");
            informationAlert.show();
            isEditButtonClicked = true;
            isNewButtonClicked = false;
        }
        else {
            errorAlert.setContentText("Error has occurred");
            errorAlert.show();
        }
        clearAll();
    }

    //Deleting Student Information
    public void deleteStudent() throws SQLException {
        StudentsTable stdTable = stdTableView.getSelectionModel().getSelectedItem();

        if(stdTable == null){
            informationAlert.setContentText("Kindly select a record to delete!");
            informationAlert.show();
        }
        else{
            sqlQuery = "Delete from Student where Std_ID= '"+stdTable.getId()+"';";

            database.getConnection().createStatement().executeUpdate(sqlQuery);
            stdTableView.setItems(getStudents(null));
        }
        assert stdTable != null;

    }

    //Editing Student Information
    public void editStudent() throws SQLException{
        isNewButtonClicked = false;
        isEditButtonClicked = true;
        StudentsTable stdTable = stdTableView.getSelectionModel().getSelectedItem();
        if(stdTable == null){
            informationAlert.setContentText("Kindly select a record to Edit!");
            informationAlert.show();
        }
        else{
            setAllEnable();
            sqlQuery = "Select * from Student where Std_ID='"+stdTable.getId()+"';";

            result = database.getConnection().createStatement().executeQuery(sqlQuery);
            while (result.next()){

            fieldStdID.setText(result.getString("Std_ID"));
            fieldStdName.setText(result.getString("Std_Name"));
            fieldStdEmail.setText(result.getString("Std_Email"));
            fieldStdPassword.setText(result.getString("Password"));
            fieldStdCourse.setValue(result.getString("Course_ID"));
            fieldYearOfStudy.setText(result.getString("YOS"));
            fieldStatus.setValue(result.getString("Status"));
            }
            isEditButtonClicked = true;
        }
    }

    //Searching For Student Information
    public void searchStudent() throws SQLException{
        if(fieldSearch.getText().isEmpty()){
            informationAlert.setContentText("Kindly Enter Student ID!");
            informationAlert.show();
        }
        sqlQuery = "Select * from Student where Std_ID LIKE'%"+fieldSearch.getText()+"%';";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        if(result.next()){
            stdTableView.setItems(getStudents(sqlQuery));
        }
        else{
            informationAlert.setContentText("No Student Found!");
            informationAlert.show();
        }
    }
    public void cancel(){
        setAllDisable();
    }

    //Refreshing student table

    public void refresh() throws SQLException {
        stdTableView.setItems(getStudents(null));
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
    private void setAllEnable() {
        fieldStdID.setDisable(false);
        fieldStdName.setDisable(false);
        fieldStdEmail.setDisable(false);
        fieldStdPassword.setDisable(false);
        fieldStdCourse.setDisable(false);
        fieldYearOfStudy.setDisable(false);
        fieldStatus.setDisable(false);
        saveBtn.setDisable(false);
        deleteBtn.setDisable(false);
    }

    private void setAllDisable() {
        fieldStdID.setDisable(true);
        fieldStdName.setDisable(true);
        fieldStdEmail.setDisable(true);
        fieldStdPassword.setDisable(true);
        fieldStdCourse.setDisable(true);
        fieldYearOfStudy.setDisable(true);
        fieldStatus.setDisable(true);
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);

    }
    public void clearAll(){
        fieldStdID.setText("");
        fieldStdName.setText("");
        fieldStdEmail.setText("");
        fieldStdPassword.setText("");
        fieldStdCourse.setValue("Courses");
        fieldYearOfStudy.setText("");
        fieldStatus.setValue("Status");
    }

    public ObservableList<String>  getStatus() throws SQLException{
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String sqlQuery = "select Distinct(Status) from Student;";
        ResultSet result = statement.executeQuery(sqlQuery);
        ObservableList<String> courses = FXCollections.observableArrayList();
        while (result.next()){
            courses.add(result.getString("Status"));
        }
        return courses;
    }

    public boolean checkFieldsEmpty(){
        return fieldStdID.getText().isEmpty() || fieldStdName.getText().isEmpty() || fieldStdEmail.getText().isEmpty() ||
                fieldStdPassword.getText().isEmpty() || fieldStdCourse.getSelectionModel().getSelectedItem() == null ||
                fieldStdCourse.getSelectionModel().getSelectedItem().isEmpty() ||
                fieldYearOfStudy.getText().isEmpty() || fieldStatus.getSelectionModel().getSelectedItem() == null ||
                fieldStatus.getSelectionModel().isEmpty();

    }
    @FXML
    Label nameError;
    @FXML
    Label idError;

    @FXML
    Label mailError;
    @FXML
    Label yosError;

    public void frontEndValidation() throws SQLException {
        boolean frontEndErrors;
        //regex for name field [^a-z]
        String studentName = fieldStdName.getText();
        String studentId = fieldStdID.getText();
        //regex find a match  begining with the following combinations of charcters \bIN
        Pattern pattern1 = Pattern.compile("^IN");
        Matcher matcher1 = pattern1.matcher(studentId);
        boolean matchFound1 = matcher1.find();
        if(!matchFound1) {
            idError.setText("Invalid student ID ");
            frontEndErrors=true;
        } else {
            System.out.println("Valid student ID");
            frontEndErrors=false;
            //regex find any character that matches a value not within the brackets[^a-z] and white spaces
            Pattern pattern2 = Pattern.compile("[^a-z\s]", Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = pattern2.matcher(studentName);
            boolean matchFound2 = matcher2.find();
            if(matchFound2) {
                nameError.setText("Invalid name ");
                frontEndErrors=true;
            } else {
                System.out.println("The name is valid");
                frontEndErrors=false;
                // regex that finds @ symbol and .com word in the string [@],"com"
                Pattern pattern3 = Pattern.compile("[@]|com\\b", Pattern.CASE_INSENSITIVE);
                Matcher matcher3 = pattern3.matcher(studentName);
                boolean matchFound3 = matcher3.find();
                if(!matchFound3) {
                    mailError.setText("Invalid Email Address");
                    frontEndErrors=true;
                } else {
                    System.out.println("The Email address is valid");
                    frontEndErrors=false;

                    // regex that ensures the year of study is an int between 1-6
                    Pattern pattern4 = Pattern.compile("[1-6]", Pattern.CASE_INSENSITIVE);
                    Matcher matcher4 = pattern4.matcher(fieldYearOfStudy.getText());
                    boolean matchFound4 = matcher4.find();
                    if(!matchFound4) {
                        yosError.setText("Invalid Year of study");
                        frontEndErrors = true;
                    } else {
                        System.out.println("Valid Year of study");
                        registerStudent();
                    }

                }
            }

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fieldStdCourse.setItems(getCourses());
            fieldStatus.setItems(getStatus());

            setAllDisable();
            getStudents(null);
            StdTable();



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
                    result.getString("Password"),
                    result.getString("Course_ID"),
                    result.getInt("YOS"),
                    result.getString("Status")

            ));
        }
        return  studentTableData;
    }

    public void StdTable() throws SQLException {
        colStdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStdEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStdPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        stdTableView.setItems(getStudents(null));
    }


    public ArrayList<String> getAdmissionDetails() throws SQLException{
        String id = "ADM-001";
        sqlQuery = "Select ID, Name from admissions where ID='"+id+"';";
        result = database.getConnection().createStatement().executeQuery(sqlQuery);
        ArrayList<String> admDetails = new ArrayList<>();
        while (result.next()){
            admDetails.add(result.getString("ID"));
            admDetails.add(result.getString("Name"));
        }
        return admDetails;
    }

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
