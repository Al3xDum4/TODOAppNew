package com.Alex.controller;

import com.Alex.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;

public class FxmlLoader {
    private Pane pane;
    //private Stage stage;

    public Pane getPane(String fileName) {
        try {
            InputStream fileUrl = Main.class.getResourceAsStream("/FXML/" + fileName + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("Fxml file can't be found");
            }
            pane = new FXMLLoader().load(fileUrl);
        } catch (Exception e) {
            System.out.println("No pane " + "'" + fileName + "'" + " please check FxmlLoader.");
        }
        return pane;
    }

    public Stage getStage(String fileName) {
        Stage newStage = new Stage();
        try {
            //Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("/FXML/" + fileName + ".fxml");
            if (resourceAsStream == null) {
                throw new java.io.FileNotFoundException("Fxml file can't be found");
            }
            Parent root = fxmlLoader.load(resourceAsStream);
            Scene scene = new Scene(root, 800, 600);
            newStage.setScene(scene);
            newStage.show();

        } catch (Exception e) {
            System.out.println("No stage " + "'" + fileName + "'" + " please check FxmlLoader.");

        }
        return newStage;
    }
}
