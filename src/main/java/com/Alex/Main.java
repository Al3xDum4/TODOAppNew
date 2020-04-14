package com.Alex;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.InputStream;

public class Main extends Application {

    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("FXML/home_page.fxml");
        Parent root = fxmlLoader.load(resourceAsStream);
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
