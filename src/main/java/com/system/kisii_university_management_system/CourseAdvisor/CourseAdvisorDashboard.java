package com.system.kisii_university_management_system.CourseAdvisor;

import com.system.kisii_university_management_system.FXMLloader.FXMLloader;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CourseAdvisorDashboard implements Initializable {



        @FXML
        public TableView<CADunitApprovalTable> CADactionTable;

        @FXML
        public TableColumn<CADunitApprovalTable, String> Std_ID;


        @FXML
        public TableColumn<CADunitApprovalTable, String> Unit_Name;

        @FXML
        public TableColumn<CADunitApprovalTable, String> action;

        @FXML
        public CheckBox approveAll;

        private final DBConnection database = new DBConnection();


    ObservableList<CADunitApprovalTable> CADunitApprovalTable = FXCollections.observableArrayList();
    // Fetching available table records

    ObservableList < CADunitApprovalTable> CADunitApprovalTableRecords= FXCollections.observableArrayList();


        public ObservableList<CADunitApprovalTable> getUnitsForApproval(){



                try {
                        Connection connectDB = database.getConnection();
                        String status="Pending";
                        String sql1 = "SELECT  Std_ID, Unit_Name ,Status FROM `Register_Units` WHERE Units_Status='"+status+"'";


                        Statement statement = connectDB.createStatement();
                        ResultSet resultSet = statement.executeQuery(sql1);
                        while (resultSet.next()){

                                CADunitApprovalTable caDunitApprovalTable1 = new CADunitApprovalTable(
                                        resultSet.getString("Std_ID"),
                                        resultSet.getString("Unit_Name"),
                                        resultSet.getString("Status"));
                                CADunitApprovalTable.add(caDunitApprovalTable1);

                        }
                }catch (Exception e){
                        e.printStackTrace();
                }
                return CADunitApprovalTable;
        }
        // Populate the CAD UNIT APPROVAL  table
        public void approvalTablePopulate(){
                if(Std_ID != null || Unit_Name != null || action != null){
                    assert Std_ID != null;
                    Std_ID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
                    Unit_Name.setCellValueFactory(new PropertyValueFactory<>("unitName"));
                    action.setCellValueFactory(new PropertyValueFactory<>("action"));
                    CADactionTable.setItems((getUnitsForApproval()));
                }


        }



    // Select All method that checks all the units

    public void selectAllOnClick(){
        if(approveAll != null){
            approveAll.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                System.out.println("Selected all selected");

                CADunitApprovalTableRecords =CADactionTable.getItems();
//                System.out.println(CADunitApprovalTableRecords.get(1).getAction().isSelected());
                for (CADunitApprovalTable caDunitApprovalTableRecord: CADunitApprovalTableRecords) {
                    caDunitApprovalTableRecord.getAction().setSelected(approveAll.isSelected());

                }
            });

        }

    }


    // Approve selected units


    public void approveUnitsBtnOnclick() throws SQLException {
        CADunitApprovalTableRecords = CADactionTable.getItems();
        int numberOfRecords = CADunitApprovalTableRecords.size();
        System.out.println(numberOfRecords);
        for (com.system.kisii_university_management_system.CourseAdvisor.CADunitApprovalTable caDunitApprovalTableRecord
                : CADunitApprovalTableRecords) {
            if (caDunitApprovalTableRecord.getAction().isSelected()) {
                System.out.println(caDunitApprovalTableRecord.getStudentID());
                Connection connectDB = database.getConnection();
                String status = "approved";
                String updateData = "UPDATE `Register_Units` SET `Units_Status` ='" + status + "' " +
                        "WHERE Std_ID = '" + caDunitApprovalTableRecord.getStudentID() + "'";
                try {
                    Statement statement = connectDB.createStatement();
                    statement.executeUpdate(updateData);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        unitApprovalSuccess();
        CADactionTable.refresh();


    }

    public void unitApprovalSuccess() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Success");
        alert.setContentText("Selected Units Successfully Approved");
        alert.showAndWait();


//
    }
@FXML
public Button logoutBtn;

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

    @FXML
   public Button lecturersAssignBtn;
    @FXML
    public  Button unitDescBtn;

    @FXML
   public void lecturersAssignBtnOnClick(){

        FXMLloader object = new FXMLloader();
        Pane view = object.getPage("lecturerAssignment");
        view.prefWidth(600);
        view.prefHeight(500);
        CADBorderPane.setCenter(view);

    }


    @FXML
    public Button addLecturerBtn;

    @FXML
    public TextField lecNameTxtField;

    @FXML
    public Button searchBtn;

    @FXML
    public TextField searchTxtField;

    @FXML
    public TextField staffNoTxtField;


    @FXML
    public ChoiceBox<String> choiceBox ;


    @FXML
    public Label frontEndErrorAlert;

    public ObservableList<String> getCourses() throws SQLException{
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        String sqlQuery = "Select * from Courses;";
        ResultSet result = statement.executeQuery(sqlQuery);
        ObservableList<String> courses = FXCollections.observableArrayList();
        while (result.next()){
            courses.add(result.getString("courseID"));
        }
        return courses;
    }




    @FXML
    public BorderPane CADBorderPane;
    @FXML
    public void unitDescBtnOnClick(){

        FXMLloader object = new FXMLloader();
        Pane view = object.getPage("CourseDesc");
        view.prefWidth(600);
        view.prefHeight(500);
        CADBorderPane.setCenter(view);

    }


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            Platform.runLater(()->{
                approvalTablePopulate();
                selectAllOnClick();
                // Initializing the courses choice box
                if(choiceBox != null) {
                    try {
                        choiceBox.getItems().addAll(getCourses());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            });

        }

    @FXML
    public TextArea courseDescTextArea;

    @FXML
    public TextField unitIDTextField;
    public String unitDesc;
    @FXML
    public void viewUnitDescBtnOnClick() {



        try {

            Connection connectDB = database.getConnection();
            String sql1 = "SELECT Unit_Desc FROM `Course_Units` WHERE Unit_Code='"+unitIDTextField.getText()+"'";
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
    public Button logoutBtn1;

    @FXML
    public Button homeBtn;

    @FXML
    public Button homeBtn2;
    public void homeBtnOnClick(ActionEvent event) throws IOException{

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/courseadvisor/CourseAdvisorDashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
    }

    public void searchBtnOnClick(ActionEvent event) {
    }






    public void addLecturerBtnOnClick() throws SQLException {

            Connection connectDB = database.getConnection();

            String insertData = "INSERT INTO `Lecturers`(`Staff_No`,`Name`,`Course_Name`) VALUES " +
                    "('" + staffNoTxtField.getText() + "','" + lecNameTxtField.getText() + "','" + choiceBox.getValue() + "')";
            try {
                if (staffNoTxtField.getText() .isEmpty() || lecNameTxtField.getText() .isEmpty() || choiceBox.getValue() .isEmpty()) {

                    frontEndErrorAlert.setText("Kindly Make Sure All fields are Filled ! ");


                } else {

                    Statement statement = connectDB.createStatement();
                    statement.executeUpdate(insertData);
                    registrationSuccessAlert();


                }
            } catch (Exception e) {
                frontEndErrorAlert.setText("Make sure your data types Match those stored in the database");
                e.printStackTrace();
            }
        }

    public void registrationSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Success ");
        alert.setContentText("Lecturer Successfully Assigned a Course ");
        if (alert.showAndWait().get() == ButtonType.OK) {
            clearTextFields();
        }
    }

    public void clearTextFields() {
        staffNoTxtField.clear();
        lecNameTxtField.clear();



    }


    public void homeBtn2OnClick(ActionEvent event) throws IOException{

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/courseadvisor/CourseAdvisorDashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();

    }
}
