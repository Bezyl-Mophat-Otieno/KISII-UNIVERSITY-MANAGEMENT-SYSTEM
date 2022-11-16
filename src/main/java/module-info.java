module com.system.kisii_university_management_system {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires java.sql;
    requires javafx.web;
    requires barcodes;
    requires layout;
    requires  pdfa;
    requires forms;
    requires kernel;
    requires io;
    exports com.system.kisii_university_management_system;
//Bursar
    exports com.system.kisii_university_management_system.Bursar;
    opens com.system.kisii_university_management_system.Bursar to javafx.fxml;
    //Enrollment
    exports com.system.kisii_university_management_system.Enrollment;
    opens com.system.kisii_university_management_system.Enrollment to javafx.fxml;
    //Admission
    exports com.system.kisii_university_management_system.Admission;
    opens com.system.kisii_university_management_system.Admission to javafx.fxml;



}