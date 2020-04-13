package com.Alex.controller;

import com.Alex.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxmlLoader {
    private BorderPane borderPane;

    public BorderPane getPane(String fileName){
        try {
            URL fileUrl = Main.class.getResource("/sample/"+fileName+".fxml");
            if(fileUrl==null){
                throw new java.io.FileNotFoundException("Fxml file can't be found");
            }
            borderPane = new FXMLLoader().load(fileUrl);
        }catch (Exception e){
            System.out.println("No page "+fileName+" please check FxmlLoader.");
        }
        return borderPane;
    }
}
