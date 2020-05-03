package com.Alex;

import com.Alex.controller.Home_PageController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

//done: Home_page block for everything and unblock after login. login and home_page start together
//done: login stage works implementation. Clear fields,
//done: Register stage works implementation
//todo: Role for user. Admin allows in application
//todo: View Project, Task and subTask in dashboard. Able to see all of these in dashboard with treeView table.
//      - The status of each issue must be seen on the corresponding line.
//        status of task or subTask from every choose task in terms of status from MyTask. Here implement observable
//      - Can choose available task.
//      - Each user can add tasks to a specific project, those projects where they have been assigned by the admin.
//todo: MyTask shows only userTasks who choose from dashboard. Here, progress, status
//todo: Project add and editable only for admin role.
//todo: Add subTasks
//todo: thread for connection
//todo: migrate to spring data
//

public class Main extends Application {

    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage = login();

        Stage home_stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("FXML/home_page.fxml");
        Parent root = fxmlLoader.load(resourceAsStream);
        home_stage.setScene(new Scene(root, 1100, 700));
        //home_stage.setOnHidden(e -> Platform.exit());
        //home_stage.show();
        primaryStage.yProperty().addListener((observable, oldValue, newValue) -> home_stage.setY(newValue.doubleValue() - 100));

        home_stage.setOnHidden(e -> Platform.exit());
        home_stage.show();

        primaryStage.initOwner(home_stage);
        primaryStage.initModality(Modality.WINDOW_MODAL);
        primaryStage.show();
    }

    public Stage login() throws IOException {
        Stage loginStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("FXML/login.fxml");
        Parent root2 = fxmlLoader.load(resourceAsStream);
        Scene registerScene = new Scene(root2);
        loginStage.setScene(registerScene);
        loginStage.initStyle(StageStyle.TRANSPARENT);
        return loginStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
