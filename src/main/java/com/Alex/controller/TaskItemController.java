package com.Alex.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskItemController implements Initializable {
    public BorderPane borderpaneTaskItem;
    public TextField txtFieldTaskName;
    public HBox hboxTaskUsersAssign;
    public Label lblTaskCreateDate;
    public Label lblTaskDeadline;
    public ProgressBar progressbarTask;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
