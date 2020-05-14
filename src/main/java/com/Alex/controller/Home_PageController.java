package com.Alex.controller;

import com.Alex.model.User;
import com.Alex.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Home_PageController implements Initializable {
    @FXML
    public TextField txtFieldSearchHomepage;
    public Button btn_team;
    public Button btn_dashboard;
    public Button btn_tasks;
    public Button btn_calendar;
    public Button btn_projects;
    public Button btn_chat;
    public Button btn_login;
    public BorderPane borderPane;
    public ImageView profilePicture;
    public HBox hb_dashboard;
    public ComboBox<String> cmbMyAccount;
    public MenuItem mnuItemLogin;
    public Label lbl_infoBottom;
    public UserRepository userRepository;
    private boolean isConnectionSuccessful = false;
    public User user;
    Stage loginWindow = new Stage();

    public Home_PageController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }

        dashboardOnStart();
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userRepository = new UserRepository(entityManager);
    }

    @FXML
    public void loadLoginWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("FXML/login.fxml");
        Parent root2 = fxmlLoader.load(resourceAsStream);
        Scene registerScene = new Scene(root2);
        loginWindow = new Stage();
        loginWindow.setScene(registerScene);
        loginWindow.initStyle(StageStyle.TRANSPARENT);
        loginWindow.show();
    }

    @FXML
    public void displayMyAccountItems(MouseEvent mouseEvent) {
        ObservableList<String> myAccount = FXCollections.observableArrayList("Edit", "Login", "Register");
        cmbMyAccount.setItems(myAccount);
    }

    public void loginOptionFromMyAccountButton(ActionEvent actionEvent) {
        FxmlLoader fxmlLoader = new FxmlLoader();
        cmbMyAccount.setOnAction(event -> {
            switch (cmbMyAccount.getValue()) {
                case "Login":
                    fxmlLoader.getStage("login");
                    break;
                case "Edit":
                    fxmlLoader.getStage("edit");
                    break;
                case "Register":
                    fxmlLoader.getStage("register");
                    break;
            }
        });
    }

    @FXML
    public void displayTeamScreen(ActionEvent actionEvent) {
        FxmlLoader fxmlLoader = new FxmlLoader();
        Pane pane = fxmlLoader.getPane("team");
        borderPane.setCenter(pane);
    }

    public void displayDashboardScreen(ActionEvent actionEvent) {
        FxmlLoader fxmlLoader = new FxmlLoader();
        Pane pane = fxmlLoader.getPane("dashboard");
        borderPane.setCenter(pane);
    }

    public void displayTasksScreen(ActionEvent actionEvent) {
        FxmlLoader fxmlLoader = new FxmlLoader();
        Pane pane = fxmlLoader.getPane("mytasks");
        borderPane.setCenter(pane);
    }

    public void displayCalendarScreen(ActionEvent actionEvent) {
        FxmlLoader fxmlLoader = new FxmlLoader();
        Pane pane = fxmlLoader.getPane("calendar");
        borderPane.setCenter(pane);
    }

    public void displayProjectsScreen(ActionEvent actionEvent) {
        FxmlLoader fxmlLoader = new FxmlLoader();
        Pane pane = fxmlLoader.getPane("myprojects");
        borderPane.setCenter(pane);
    }

    public void displayChatScreen(ActionEvent actionEvent) {
        FxmlLoader fxmlLoader = new FxmlLoader();
        Pane pane = fxmlLoader.getPane("chat");
        borderPane.setCenter(pane);
    }

    public void dashboardOnStart(){
        FxmlLoader fxmlLoader = new FxmlLoader();
        Pane pane = fxmlLoader.getPane("dashboard");
        borderPane.setCenter(pane);
    }
}
