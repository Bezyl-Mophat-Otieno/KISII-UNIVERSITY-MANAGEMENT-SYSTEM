package com.system.kisii_university_management_system.MainLogin;

import com.system.kisii_university_management_system.Student.StudentDashboard;
import com.system.kisii_university_management_system.database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class APPController {
    @FXML
    public Label loginAlerts;
    @FXML
   public Button cancelBtn;

    @FXML
    public Button loginBtn;

    @FXML
    public TextField IDtxtField;
    @FXML
    public TextField passwordTxtField;

    String name;
    String course;
    String courseIDcontolled;
    String courseID;
    Double feesPayable ;
    private final DBConnection database = new DBConnection();

    @FXML
    public void loginBtnOnClick() throws SQLException, IOException {
        if(IDtxtField.getText().isBlank() || passwordTxtField.getText().isBlank()){
            loginAlerts.setText("Kindly fill in all the Details");

        }else if( IDtxtField.getText().startsWith("IN")){
            studentLoginVerification();

        } else if ( IDtxtField.getText().startsWith("CAD")) {
            CADLoginVerification();
        } else if (IDtxtField.getText().startsWith("BURS")) {
            bursarLoginVerification();
        } else if (IDtxtField.getText().startsWith("ENR")) {
            enrollmentLoginVerification();
        } else if (IDtxtField.getText().startsWith("ADM")) {
            admOfficeLoginVerification();
        } else if (IDtxtField.getText().startsWith("REG")) {
            registrarLoginVerification();
        } else if (IDtxtField.getText().startsWith("SCH")) {
            schoolLoginVerification();
        }

        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("User Category Does Not Exist");
            alert.showAndWait();

        }


    }
    @FXML public void cancelBtnOnclick(ActionEvent event){
        Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    // student Login test
private final Alert errorAlerts = new Alert(Alert.AlertType.ERROR);

    public  void studentLoginVerification () throws SQLException, IOException {
        Connection connectDB = database.getConnection();
        String sql1 = "SELECT Status FROM Student WHERE Std_ID ='"+IDtxtField.getText()+"'";
        System.out.println(sql1);



            Statement statement1 = connectDB.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql1);

            while (resultSet1.next()){
                if (resultSet1.getString(1).equals("active")  || resultSet1.getString(1).equals("inactive") ){
                    System.out.println(resultSet1.getString(1).equals("active"));

                    String sql = "SELECT COUNT(1) FROM Student WHERE Std_ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";

            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){
                    displayStdDashboard();
                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
                }else {
                    errorAlerts.setContentText("Dropped Out or Deffered  Students Are Not Allowed to Log In");
                    errorAlerts.show();
                }

            }
        }







    // BURSAR AUTHENTICATION

    public  void bursarLoginVerification () throws SQLException {
        Connection connectDB = database.getConnection();
        String sql = "SELECT COUNT(1) FROM bursar WHERE ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){
                    displayBursarDashboard();
                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // SCHOOL AUTHENTICATION
    public  void schoolLoginVerification () throws SQLException {
        Connection connectDB = database.getConnection();
        String sql = "SELECT COUNT(1) FROM schooldept WHERE ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){
                    displaySchoolDashboard();
                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // REG LOGIN Authentication
    public  void registrarLoginVerification () throws SQLException {
        Connection connectDB = database.getConnection();
        String sql = "SELECT COUNT(1) FROM registrar WHERE ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){
                    displayRegistrarDashboard();
                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //CAD LOGIN Authentication

    public  void CADLoginVerification () throws SQLException {
        Connection connectDB = database.getConnection();
        String sql = "SELECT COUNT(1) FROM courseadvisor WHERE ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){
                    displayCADDashboard();
                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //ENR LOGIN Authentication

    public  void enrollmentLoginVerification () throws SQLException {
        Connection connectDB = database.getConnection();
        String sql = "SELECT COUNT(1) FROM enrollment WHERE ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){
                    displayEnrollmentDashboard();
                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public  void admOfficeLoginVerification () throws SQLException {

        Connection connectDB = database.getConnection();
        String sql = "SELECT COUNT(1) FROM admissions WHERE ID ='"+IDtxtField.getText()+"'AND Password ='"+
                passwordTxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                if (resultSet.getInt(1)==1){

                    displayadmDashboard();

                }else {
                    loginAlerts.setText("Incorrect Email or Password ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void studentDetails() throws SQLException {


        Connection connectDB = database.getConnection();
        String sql = "SELECT Student.Std_Name , Courses.courseName , Courses.Course_ID , Courses.costPrice FROM Student JOIN Courses " +
            "ON Student.Course_ID = Courses.Course_ID WHERE Student.Std_ID='"+IDtxtField.getText()+"'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                name = resultSet.getString(1);
                course = resultSet.getString(2);
                courseID = resultSet.getString(3);
                feesPayable = resultSet.getDouble(4);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    // display another scene (student Dashboard )

    public void displayStdDashboard() throws IOException, SQLException {
        studentDetails();
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/student/studentDashboard.fxml"));
        Parent root = loader.load();
        //Creating an instance of StudentDashboard
        StudentDashboard studentDashboard= loader.getController();
        studentDashboard.getStudentIDtxtField(IDtxtField.getText());
//        studentDashboard.displayStudentDetails(name, course, courseID, feesPayable);
        Scene scene = new Scene(root);
        newStage.setTitle("Student Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }


    //display course Advisors dashbnoard
    public void displayCADDashboard() throws IOException {
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/courseadvisor/CourseAdvisorDashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        newStage.setTitle("Course Advisor Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }


    //display Bursar dashbnoard
    public void displayBursarDashboard() throws IOException {
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/bursar/bursar.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        newStage.setTitle("Bursar Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }

    public void displayadmDashboard() throws IOException {
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/admission/admission.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        newStage.setTitle("Admissions Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }


    //display Enrollment dashbnoard
    public void displayEnrollmentDashboard() throws IOException {
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/enrollment/enrollment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        newStage.setTitle("Enrollment Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }

    // display registrar dashboard
    public void displayRegistrarDashboard() throws IOException {
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/registrar/registrar.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        newStage.setTitle("Registrar Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }

    //display School dashbnoard
    public void displaySchoolDashboard() throws IOException {
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        // create an FXML Loader instance
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/school/school.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        newStage.setTitle("School Dept Dashboard");
        newStage.setScene(scene);
        newStage.show();
    }

//    public void displayAdmissionsOfficeDashboard() throws IOException {
//
//        Stage stage = (Stage)loginBtn.getScene().getWindow();
//        stage.close();
//        Stage newStage = new Stage();
//        // create an FXML Loader instance
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdmissionsOffice.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//
//        newStage.setTitle("Admissions Office Dashboard");
//        newStage.setScene(scene);
//        newStage.show();
//
//
//    }

    @FXML
    public BorderPane mainBorderPane;

    public TextField getIDtxtField() {
        return IDtxtField;
    }
}