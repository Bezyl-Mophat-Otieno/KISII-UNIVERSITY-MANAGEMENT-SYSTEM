package com.system.kisii_university_management_system.Bursar;

<<<<<<< HEAD
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
=======
import com.system.kisii_university_management_system.APP;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
>>>>>>> d5045dba163a45dfddec7a9be597028918433d00
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Bursar extends  Application{

    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD
        FXMLLoader fxmlLoader = new FXMLLoader(Bursar.class.getResource(
                "/com/example/studentregistrationsystem/bursar/bursar.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
=======
        FXMLLoader fxmlLoader = new FXMLLoader(APP.class.getResource("bursar.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bursar");
        stage.setScene(scene);

        stage.setResizable(true);
>>>>>>> d5045dba163a45dfddec7a9be597028918433d00
        stage.show();

    }
    public static void main(String[] args){
        launch();
    }
}
