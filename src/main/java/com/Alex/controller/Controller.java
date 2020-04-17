package com.Alex.controller;

import com.Alex.model.User;
import com.Alex.repository.UserRepository;
import com.sun.xml.bind.v2.TODO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
//TODO: Make all the tabs hidden. Login window opens when you click an option in the menu
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

public class Controller implements Initializable {
    @FXML
    public Label lblInformation;
    public Label lblInformationLoginScene;
    public Label lblLoginUsernameMessage;
    public Label lblLoginPasswordMessage;
    public Label lblRegisterUsernameMessage;
    public Label lblRegisterPasswordMessage;

    public TextField txtFieldUsernameLoginScene;
    public TextField txtFieldUsernameRegisterScene;
    public TextField txtFieldEmailRegisterScene;
    public TextField txtFieldSearchHomepage;
    public TextField txtFieldPassword;

    public PasswordField pwdFieldPasswordLoginScene;
    public PasswordField pwdFieldConfirmPasswordRegisterScene;
    public PasswordField pwdFieldPasswordRegisterScene;

    public Button btnLoginLoginScene;
    public Button btnRegisterLoginScene;
    public Button btnRegisterRegisterScene;
    public Button btn_team;
    public Button btn_dashboard;
    public Button btn_tasks;
    public Button btn_calendar;
    public Button btn_projects;
    public Button btn_chat;
    public Button btn_showPassword;

    public BorderPane borderPane;
    public BorderPane loginBorderPane;
    public BorderPane registerBorderPane;

    public FontAwesomeIconView iconUsernameLogin;
    public FontAwesomeIconView iconPasswordLogin;

    public ImageView profilePicture;
    public AnchorPane hb_team;
    public HBox hb_dashboard;
    public VBox vboxTaskItems;


    public ComboBox<String> cmbMyAccount;
    private UserRepository userRepository;
    private boolean isConnectionSuccessful = false;

    public Controller() {
    }

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
        User user = userRepository.findByUsername(txtFieldUsernameRegisterScene.getText());

        if (pwdFieldPasswordRegisterScene.getText().equals(pwdFieldConfirmPasswordRegisterScene.getText()) && user == null) {
            user = new User();

            user.setUsername(txtFieldUsernameRegisterScene.getText());
            user.setPassword(pwdFieldPasswordRegisterScene.getText());

            userRepository.save(user);
        } else {
            lblInformation.setText("This username is already register.Please pick other username");
        }
    }

    @FXML
    private void loginUser(ActionEvent actionEvent) {
        if (txtFieldUsernameLoginScene.getText().length() < 1 || !userRepository.findById(txtFieldUsernameLoginScene.getText()).isPresent()) {
            iconUsernameLogin.setFill(Color.RED);
            lblLoginUsernameMessage.setVisible(true);
        } else {
            User user = userRepository.findById(txtFieldUsernameLoginScene.getText()).get();
            if (user.getPassword().equals(pwdFieldPasswordLoginScene.getText())) {
                //System.out.println(txtFieldUsername.getText());
                //System.out.println(txtFieldPassword.getText());
                //tabPane.getTabs().remove(tabLogin);
                //tabPane.getTabs().add(tabMain);
                //lblUsername.setTextFill(Color.BLACK);
                //lblInfo.setVisible(false);
                lblInformationLoginScene.setText("successfully login");


            } else {
                iconPasswordLogin.setFill(Color.RED);
                lblLoginPasswordMessage.setVisible(true);
            }
        }
    }

    @FXML
    public void goToRegisterScene(ActionEvent actionEvent) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("FXML/register.fxml");
//        BorderPane borderPane = fxmlLoader.load(resourceAsStream);
//
//        loginBorderPane.getChildren().setAll(borderPane);

//        Scene registerScene = new Scene(registerParent);
//        Stage window = new Stage();
//        window.setScene(registerScene);
//        window.show();
    }

//    public void goToLoginScreen(ActionEvent actionEvent) {
//        FxmlLoader fxmlLoader = new FxmlLoader();
//        //fxmlLoader.getStage("login");
//        if (cmbMyAccount.getValue().contains("Login")) {
//            fxmlLoader.getStage("login");
//        }
//    }
//
//    public String getChoice(ComboBox<String> cmbMyAccount) {
//        String option = cmbMyAccount.getValue();
//        return option;
//    }

    @FXML
    public void displayMyAccountItems(MouseEvent mouseEvent) {
        ObservableList<String> myAccount = FXCollections.observableArrayList("Edit", "Login", "Register");
        cmbMyAccount.setItems(myAccount);
    }
    public void loginOptionFromMyAccountButton(ActionEvent actionEvent){
        FxmlLoader fxmlLoader = new FxmlLoader();
        cmbMyAccount.setOnAction(e -> {
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

    public void showPassword(ActionEvent actionEvent) {
        if (!txtFieldPassword.isVisible()) {
            txtFieldPassword.setText(pwdFieldPasswordLoginScene.getText());
            txtFieldPassword.setEditable(false);
            txtFieldPassword.setVisible(true);
            pwdFieldPasswordLoginScene.setVisible(false);

        } else {
            txtFieldPassword.setVisible(false);
            pwdFieldPasswordLoginScene.setVisible(true);
        }
    }
}
