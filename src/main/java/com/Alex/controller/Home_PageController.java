package com.Alex.controller;

import com.Alex.model.User;
import com.Alex.repository.UserRepository;
import com.sun.xml.bind.v2.TODO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//DONE: Add scene for different operations
//DONE: Make all the tabs hidden. Login window opens when you click an option in the menu
//TODO: After login success show other tabs. Clear text fields. Show message.
//TODO: After clicking register, empty text boxes, show message user registration.
//DONE: Create other entities. Task, Subtask + repository gor each.
//TODO: create tab for registering tasks
//TODO: create control for registering subtask
//TODO: create tab for assign task. Two comboboxes, one for tasks, one for all users.
//DONE: add button to show password
//TODO: check if username is already exists
//TODO: check if password contain certain characters
//TODO: save encrypted(encoded) password in DB. Be careful how to read them unencrypted
//TODO: implement login action
//TODO: user role (administrator, coordinator, developer, tester, etc...)
//TODO: to add or remove project, only administrator/coordinator


public class Home_PageController implements Initializable {
    @FXML
    public Label lblInformation;
    //    public Label lblInformationLoginScene;
//    public Label lblLoginUsernameMessage;
//    public Label lblLoginPasswordMessage;
    public Label lblRegisterUsernameMessage;
    public Label lblRegisterPasswordMessage;

    //    public TextField txtFieldUsernameLoginScene;
    public TextField txtFieldUsernameRegisterScene;
    public TextField txtFieldEmailRegisterScene;
    public TextField txtFieldSearchHomepage;
    //    public TextField txtFieldPassword;
//
//    public PasswordField pwdFieldPasswordLoginScene;
    public PasswordField pwdFieldConfirmPasswordRegisterScene;
    public PasswordField pwdFieldPasswordRegisterScene;

    //    public Button btnLoginLoginScene;
//    public Button btnRegisterLoginScene;
    public Button btnRegisterRegisterScene;
    public Button btn_team;
    public Button btn_dashboard;
    public Button btn_tasks;
    public Button btn_calendar;
    public Button btn_projects;
    public Button btn_chat;
    //    public Button btn_showPassword;
    public Button btn_login;

    public BorderPane borderPane;
    //    public BorderPane loginBorderPane;
//    public BorderPane registerBorderPane;
//
//    public FontAwesomeIconView iconUsernameLogin;
//    public FontAwesomeIconView iconPasswordLogin;
    public FontAwesomeIconView iconUsernameRegister;
    public MaterialDesignIconView iconEmailRegister;
    public FontAwesomeIconView iconPasswordRegister;
    public FontAwesomeIconView iconConfrimPasswordRegister;

    public ImageView profilePicture;
    public HBox hb_dashboard;

    public ComboBox<String> cmbMyAccount;
    public BorderPane registerPane;
    public MenuItem mnuItemLogin;

    private UserRepository userRepository;
    private User user;
    private boolean isConnectionSuccessful = false;

    Stage loginWindow = new Stage();
    //Stage registerWindow = new Stage();

//    double x = 0, y = 0;

    public Home_PageController() {
    }

//    public void pressed(MouseEvent event) {
//        x = event.getSceneX();
//        y = event.getSceneY();
//    }
//
//    public void dragged(MouseEvent event, Stage stage) {
//        stage.setX(event.getScreenX() - x);
//        stage.setY(event.getScreenY() - y);
//    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is allowed");
            isConnectionSuccessful = false;
        }
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userRepository = new UserRepository(entityManager);
    }

    @FXML
    private void registerUser(ActionEvent actionEvent) {
        if (txtFieldUsernameRegisterScene.getText().isEmpty()) {
            iconUsernameRegister.setFill(Color.RED);
            txtFieldUsernameRegisterScene.setPromptText("enter your username");
        }

        if (pwdFieldPasswordRegisterScene.getText().isEmpty()) {
            iconPasswordRegister.setFill(Color.RED);
            pwdFieldPasswordRegisterScene.setPromptText("enter your password");
        }

        if (pwdFieldConfirmPasswordRegisterScene.getText().isEmpty()) {
            iconConfrimPasswordRegister.setFill(Color.RED);
            pwdFieldConfirmPasswordRegisterScene.setPromptText("confirm your password");
        }

//        if(txtFieldUsernameRegisterScene.getText()!=null) {
        user = userRepository.findByUsername(txtFieldUsernameRegisterScene.getText());
        if (pwdFieldPasswordRegisterScene.getText().equals(pwdFieldConfirmPasswordRegisterScene.getText()) && user == null) {
            user = new User();
            user.setUsername(txtFieldUsernameRegisterScene.getText());
            user.setPassword(pwdFieldPasswordRegisterScene.getText());
            user.setMailUser(txtFieldEmailRegisterScene.getText());
            userRepository.save(user);
        } else if (user != null) {
                lblRegisterUsernameMessage.setVisible(true);
        } else {
            lblRegisterPasswordMessage.setVisible(true);
        }
    }

//    @FXML
//    private void loginUser(ActionEvent actionEvent) {
//        User user = userRepository.findByUsername(txtFieldUsernameLoginScene.getText());
//        if (txtFieldUsernameLoginScene.getText().isEmpty()) {
//            iconUsernameLogin.setFill(Color.RED);
//        }
//        if (!txtFieldUsernameLoginScene.getText().equals(user.getUsername())) {
//            lblLoginUsernameMessage.setVisible(true);
//        }
//
//        loginWindow = (Stage) btnRegisterLoginScene.getScene().getWindow();
//        loginWindow.close();
//    }

//    public void showPassword(ActionEvent actionEvent) {
//        if (!txtFieldPassword.isVisible()) {
//            txtFieldPassword.setText(pwdFieldPasswordLoginScene.getText());
//            txtFieldPassword.setEditable(false);
//            txtFieldPassword.setVisible(true);
//            pwdFieldPasswordLoginScene.setVisible(false);
//        } else {
//            txtFieldPassword.setVisible(false);
//            pwdFieldPasswordLoginScene.setVisible(true);
//        }
//    }

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

//    @FXML
//    public void goToRegisterScene(ActionEvent actionEvent) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("FXML/register.fxml");
//        Parent root3 = fxmlLoader.load(resourceAsStream);
//        Scene registerScene = new Scene(root3);
//        registerWindow = new Stage();
//        registerWindow.setScene(registerScene);
//        registerWindow.initStyle(StageStyle.TRANSPARENT);
//        loginWindow.close();
//        registerWindow.show();
//        //loginWindow.close();
//    }

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


}
