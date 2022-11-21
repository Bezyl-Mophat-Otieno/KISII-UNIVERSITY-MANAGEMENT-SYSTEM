package com.system.kisii_university_management_system.Student;

import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.system.kisii_university_management_system.database.DBConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.stage.Stage;

import static com.itextpdf.io.font.constants.StandardFonts.TIMES_BOLD;

public class TranscriptView implements Initializable {

    @FXML
    public TableView<Transcript>transcriptTableView;

    @FXML
    public TableColumn<Transcript,String> Unit_Code;

    @FXML
    public TableColumn<Transcript,Double> GPA;
    @FXML
    public Label averageGPALabel;
    private final DBConnection database = new DBConnection();


    public ObservableList<Transcript> transcript = FXCollections.observableArrayList();
    public String studentID;

    //Passing the student ID from the students Dashboard
    public void getStudentID(String studentIdentification){

        studentID = studentIdentification;
    }


    public ObservableList<Transcript> getResults(){
        try {

            Connection connectDB = database.getConnection();
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

            Unit_Code.setCellValueFactory(new PropertyValueFactory<>("UnitCode"));
            GPA.setCellValueFactory(new PropertyValueFactory<>("GPA"));
            transcriptTableView.setItems(getResults());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private final Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    private final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);



    //print transcript
    @FXML
    public void printTranscript(ActionEvent  event) throws IOException {





    }
    public void setStdID(String stdID){

    }
    public String getAverageGPA(){
        double sumOfGPA = 0;
        double numberOfUnits = 0;
        double averageGPA;
        for(Transcript transcriptView: transcriptTableView.getItems()){
            sumOfGPA += transcriptView.getGPA();
            numberOfUnits++;
        }
        averageGPA = sumOfGPA /numberOfUnits;
        return new DecimalFormat("0.00").format(averageGPA);
    }

    @FXML
    public Button homeBtn;

    @FXML
    public void homeBtnOnClick (ActionEvent event) throws IOException, SQLException {

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource(
                "/com/system/kisii_university_management_system/student/studentDashboard.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        StudentDashboard studentDashboard = fxmlLoader.getController();
        studentDashboard.getStudentIDtxtField(studentID);
        Stage newstage = new Stage();
        newstage.setScene(scene);
        newstage.show();



    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(()->{
                    initializeTranscriptTable();
                    averageGPALabel.setText(getAverageGPA());
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
