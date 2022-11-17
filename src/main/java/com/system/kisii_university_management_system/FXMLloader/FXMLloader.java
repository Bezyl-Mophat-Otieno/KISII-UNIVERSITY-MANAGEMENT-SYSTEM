package com.system.kisii_university_management_system.FXMLloader;

import com.system.kisii_university_management_system.MainLogin.APPController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.net.URL;

public class FXMLloader {
    private Pane view;

    public Pane getPage(String fileName){
        try {
            URL fileUrl = APPController.class.getResource(
                    "/com/system/kisii_university_management_system/courseadvisor/"+ fileName+".fxml");
            if (fileUrl == null){
                throw new FileNotFoundException("The file is not found");
            }
            new FXMLLoader();
            view = FXMLLoader.load(fileUrl);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("NO page "+fileName+" check the  file please");
        }

        return view;
    }

}
