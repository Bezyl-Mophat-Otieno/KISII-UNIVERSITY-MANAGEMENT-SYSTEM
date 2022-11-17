package com.system.kisii_university_management_system.Bursar;

import com.system.kisii_university_management_system.MainLogin.APP;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Bursar extends  Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(APP.class.getResource("bursar.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bursar");
        stage.setScene(scene);

        stage.setResizable(true);

        stage.show();

    }
    public static void main(String[] args){
        launch();
    }
}
