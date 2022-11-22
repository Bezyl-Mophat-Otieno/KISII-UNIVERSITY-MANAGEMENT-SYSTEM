package com.system.kisii_university_management_system.Student;

import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.system.kisii_university_management_system.database.DBConnection;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
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
    public TableView<Transcript> transcriptTableView;

    @FXML
    public TableColumn<Transcript, String> Unit_Code;

    @FXML
    public TableColumn<Transcript, Double> GPA;
    @FXML
    public Label averageGPALabel;
    private final DBConnection database = new DBConnection();


    public ObservableList<Transcript> transcript = FXCollections.observableArrayList();
    public String studentID;

    //Passing the student ID from the students Dashboard
    public void getStudentID(String studentIdentification) {

        studentID = studentIdentification;
    }


    public ObservableList<Transcript> getResults() {
        try {

            Connection connectDB = database.getConnection();
            String sql1 = "SELECT GPA ,Unit_Code  FROM `Unit_Results` WHERE std_ID='" + studentID + "'";


            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(sql1);
            while (resultSet.next()) {
                Transcript transcript1 = new Transcript();
                transcript1.setUnitCode(resultSet.getString("Unit_Code"));
                transcript1.setGPA(resultSet.getDouble("GPA"));
                transcript.add(transcript1);
            }
        } catch (Exception e) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private final Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    private final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    //print transcript
    @FXML
    public void printTranscript(ActionEvent event) throws IOException, SQLException {
        if (transcriptTableView.getItems().size() == 0) {
            informationAlert.setContentText("Check with your department for your results");
            informationAlert.show();
        } else {
            String path = "/home/mophat/IdeaProjects/KISII UNIVERSITY MANAGEMENT SYSTEM/src/main/PDF'S/" + "Transcript.pdf";
            PdfWriter transcriptWritter = new PdfWriter(path);
            PdfDocument transDocument = new PdfDocument(transcriptWritter);
            transDocument.setDefaultPageSize(PageSize.A4);
            transDocument.addNewPage();
            Style pStyles = new Style();
            pStyles.setFont(PdfFontFactory.createFont(TIMES_BOLD))
                    .setFontSize(12);


            Document transcriptDoc = new Document(transDocument);

            String transcriptHeader = "\t\t Transcript \n" +
                    "============================================================================\n" +
                    "Student Registration Number:\t\t " + studentID + "\n\n" +
                    "Unit Code:\t\t\t\t\t\t\t\t\t" + "GPA" + "\n\n\n\n\n\n" +
                    "============================================================================\n" +
                    "\t\t\t\t\t\t\t\t\t" + "\t\t\t\t\t\t\t\t\t" + "\n\n\n\n\n\n";
            Paragraph transcriptParagraph1 = new Paragraph(transcriptHeader).addStyle(pStyles);
            transcriptDoc.add(transcriptParagraph1);
            for (Transcript transcript1 : transcriptTableView.getItems()) {

                String transcriptData = "\t\t\t\t\t\t\t\t\t" + transcript1.getUnitCode() + "\t\t\t\t\t\t\t\t\t"+transcript1.getGPA() ;
                Paragraph transcriptParagraph2 = new Paragraph(transcriptData).addStyle(pStyles);
                transcriptDoc.add(transcriptParagraph2);
            }

            transcriptDoc.close();

            confirmationAlert.setTitle("Print");
            confirmationAlert.setHeaderText("Downloaded");
            confirmationAlert.setContentText("Your Transcript Has Been Downloaded. Ready To Be Printed!");
            confirmationAlert.setResizable(false);
            Scene scene = ((Node) event.getSource()).getScene();
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    PrinterJob job = PrinterJob.createPrinterJob();

                    Stage stage = (Stage) homeBtn.getScene().getWindow();
                    stage.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                            "/com/system/kisii_university_management_system/student/studentDashboard.fxml"));
                    Parent root = fxmlLoader.load();
                     scene = new Scene(root);
                    StudentDashboard studentDashboard = fxmlLoader.getController();
                    studentDashboard.getStudentIDtxtField(studentID);
                    Stage newstage = new Stage();
                    newstage.setScene(scene);
                    newstage.show();

                    if (job == null) {
//                        errorAlert.setContentText("No printers Found!");
//                        errorAlert.show();
                    } else {
                        boolean proceed = job.showPrintDialog(scene.getWindow());
                        if (proceed) {
                            if (job.printPage(transcriptTableView)) {
                                job.endJob();
                            }
                        }
                    }

                }
            }

        }
    }

    public void setStdID(String stdID) {

    }

    public String getAverageGPA() {
        double sumOfGPA = 0;
        double numberOfUnits = 0;
        double averageGPA;
        for (Transcript transcriptView : transcriptTableView.getItems()) {
            sumOfGPA += transcriptView.getGPA();
            numberOfUnits++;
        }
        averageGPA = sumOfGPA / numberOfUnits;
        return new DecimalFormat("0.00").format(averageGPA);
    }

    @FXML
    public Button homeBtn;

    @FXML
    public void homeBtnOnClick(ActionEvent event) throws IOException, SQLException {

        Stage stage = (Stage) homeBtn.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
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

        Platform.runLater(() -> {
            initializeTranscriptTable();
            averageGPALabel.setText(getAverageGPA());
        });

    }
}






