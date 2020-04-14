package com.Alex.controller;

import com.Alex.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.InputStream;
import java.net.URL;

public class FxmlLoader {
    private Pane pane;

    public Pane getPane(String fileName){
        try {
            InputStream fileUrl = Main.class.getResourceAsStream("/FXML/"+fileName+".fxml");
            if(fileUrl==null){
                throw new java.io.FileNotFoundException("Fxml file can't be found");
            }
            pane = new FXMLLoader().load(fileUrl);
        }catch (Exception e){
            System.out.println("No page "+"'"+fileName+"'"+" please check FxmlLoader.");
        }
        return pane;
    }
}
