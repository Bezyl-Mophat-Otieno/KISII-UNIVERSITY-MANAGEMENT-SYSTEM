package com.system.kisii_university_management_system;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import  com.itextpdf.layout.Document;
import javafx.stage.Stage;

public class TranscriptView implements Initializable {

    @FXML
    public TableView<Transcript>transcriptTableView;

    @FXML
    public TableColumn<Transcript,String> Unit_Code;


    @FXML
   public TableColumn<Transcript,Double> GPA;



   public ObservableList<Transcript> transcript = FXCollections.observableArrayList();
    public String studentID;

    //Passing the student ID from the students Dashboard
    public void getStudentID(String studentIdentification){
        studentID = studentIdentification;
    }


    public ObservableList<Transcript> getResults(){




        try {

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connectDB = databaseConnection.getConnection();
            String sql1 = "SELECT GPA ,Unit_Code  FROM `Unit_Results` WHERE std_ID='"+studentID+"'";


            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            while (resultSet.next()){
                Transcript transcript1 = new Transcript();
                transcript1.setUnitCode(resultSet.getString("Unit_Code"));
                transcript1.setGPA(resultSet.getDouble("GPA"));
                transcript.add(transcript1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return transcript;
    }
// Initializing the transcript Table

    public void initializeTranscriptTable() {
        try {

            Unit_Code.setCellValueFactory(new PropertyValueFactory<Transcript,String>("UnitCode"));
            GPA.setCellValueFactory(new PropertyValueFactory<Transcript,Double>("GPA"));
            transcriptTableView.setItems(getResults());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //print transcript
    @FXML
    public void printTranscript(ActionEvent  event){


    }

    @FXML
    public Button homeBtn;

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
            initializeTranscriptTable();
        });

    }

//    public static void main(String[] args) throws FileNotFoundException {
//        String path = "/home/mophat/IdeaProjects/KISII UNIVERSITY MANAGEMENT SYSTEM/src/main/PDF'S/samplePdf.pdf";
//        PdfWriter writer = new PdfWriter(path);
//        PdfDocument pdfDocument =new PdfDocument(writer);
//        pdfDocument.addNewPage();
//        Document document = new Document(pdfDocument);
//        document.close();
//        System.out.println("Done");
//
//    }

}
