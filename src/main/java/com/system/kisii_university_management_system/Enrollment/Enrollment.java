<<<<<<< HEAD
package com.system.kisii_university_management_system.Enrollment;
=======
package com.example.studentregistrationsystem.Enrollment;
>>>>>>> d5045dba163a45dfddec7a9be597028918433d00

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Enrollment extends  Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Enrollment.class.getResource(
<<<<<<< HEAD
                "/com/example/studentregistrationsystem/enrollment/enrollment.fxml"));
=======
                "enrollment.fxml"));
>>>>>>> d5045dba163a45dfddec7a9be597028918433d00
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args){
        launch();
    }
}
