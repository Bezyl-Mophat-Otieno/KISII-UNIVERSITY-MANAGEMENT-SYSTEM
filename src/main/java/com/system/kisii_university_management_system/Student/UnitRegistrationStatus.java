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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.itextpdf.io.font.constants.StandardFonts.TIMES_BOLD;

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

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private final Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    private final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    public void printExamCard(ActionEvent event) throws IOException, SQLException {

        if (unitStatusTable.getItems().size() == 0) {
            informationAlert.setContentText("You currently have not registered for any unit  ");
            informationAlert.show();
        } else {
            String path = "/home/mophat/IdeaProjects/KISII UNIVERSITY MANAGEMENT SYSTEM/src/main/PDF'S/" + "ExamCard.pdf";
            PdfWriter examCardWritter = new PdfWriter(path);
            PdfDocument examCardDocument = new PdfDocument(examCardWritter);
            examCardDocument.setDefaultPageSize(PageSize.A4);
            examCardDocument.addNewPage();
            Style pStyles = new Style();
            pStyles.setFont(PdfFontFactory.createFont(TIMES_BOLD))
                    .setFontSize(12);


            Document examCardDoc = new Document(examCardDocument);

            String examCardHeader = "\t\t Exam Card \n" +
                    "============================================================================\n" +
                    "Student Registration Number:\t\t " + studentID + "\n\n" +
                    "Unit Code:\t\t\t\t\t\t\t\t\t" + "Unit Name:\t\t\t\t\t\t\t\t\t" +"\n" +
                    "============================================================================\n" +
                    "\t\t\t\t\t\t\t\t\t" + "\t\t\t\t\t\t\t\t\t" + "\n\n";
            Paragraph examCardParagraph1 = new Paragraph(examCardHeader).addStyle(pStyles);
            examCardDoc.add(examCardParagraph1);
            for (UnitStatus unitStatus: unitStatusTable.getItems()) {
                    String cardtData = "\t\t\t\t\t\t\t\t\t" + unitStatus.getUnitCode() + "\t\t\t\t\t\t\t\t\t"+unitStatus.getUnitName() ;
                    Paragraph examCardParagraph2 = new Paragraph(cardtData).addStyle(pStyles);
                    examCardDoc.add(examCardParagraph2);


            }
            confirmationAlert.setContentText("Your Exam Card has been successfully downlaoded");
            examCardDoc.close();
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
                            if (job.printPage(unitStatusTable)) {
                                job.endJob();
                            }
                        }
                    }

                }
            }

        }



    }
}
