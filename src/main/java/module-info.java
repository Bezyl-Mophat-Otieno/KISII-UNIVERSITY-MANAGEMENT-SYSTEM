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
    //Main Login
    exports com.system.kisii_university_management_system.MainLogin;
    opens com.system.kisii_university_management_system.MainLogin to javafx.fxml;
    //Admission
    exports com.system.kisii_university_management_system.Admission;
    opens com.system.kisii_university_management_system.Admission to javafx.fxml;
    //Bursar
    exports com.system.kisii_university_management_system.Bursar;
    opens com.system.kisii_university_management_system.Bursar to javafx.fxml;
    //Enrollment
    exports com.system.kisii_university_management_system.Enrollment;
    opens com.system.kisii_university_management_system.Enrollment to javafx.fxml;
    //Registrar
    exports com.system.kisii_university_management_system.Registrar;
    opens com.system.kisii_university_management_system.Registrar to javafx.fxml;
    // Course Advisor
    exports com.system.kisii_university_management_system.CourseAdvisor;
    opens com.system.kisii_university_management_system.CourseAdvisor to javafx.fxml;
    //School
    exports com.system.kisii_university_management_system.School;
    opens com.system.kisii_university_management_system.School to javafx.fxml;
    // Student
    exports com.system.kisii_university_management_system.Student;
    exports com.system.kisii_university_management_system.FXMLloader;


}