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
    private Stage stage;

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

    public Pane getStage(String fileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("/FXML/" + fileName + ".fxml");
            if (resourceAsStream == null) {
                throw new java.io.FileNotFoundException("Fxml file can't be found");
            }
            //Parent borderPaneParent = fxmlLoader.load(resourceAsStream);
            //Scene borderPaneScene = new Scene(borderPaneParent, 800, 600);

            pane = fxmlLoader.load(resourceAsStream);
            Scene scene = new Scene(pane,800,600);
            stage.setScene(scene);

            //stage = (Stage) borderPaneScene.getWindow();
            //stage.show();

        } catch (Exception e) {
            System.out.println("No stage " + "'" + fileName + "'" + " please check FxmlLoader.");

        }
        return pane;
    }
}
