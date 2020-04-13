package com.Alex.controller;

import com.Alex.model.User;
import com.Alex.repository.UserRepository;
import com.sun.xml.bind.v2.TODO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//TODO: Add button to show password
//TODO: Check if username already exists
//TODO: Check if password contains certain characters
//TODO: Save encoded password in DB
//TODO: Add scene for different operations
//TODO: Make all the tabs hidden. Login window opens when you click an option in the menu
//TODO: After login success show other tabs. Clear text fields. Show message.
//TODO: After clicking register, empty text boxes, show message user registration.
//TODO: Create other entities. Task, Subtask + repository gor each.
//TODO: create tab for registering tasks
//TODO: create control for registering subtask
//TODO: create tab for assign task. Two comboboxes, one for tasks, one for all users.
//TODO: add button to show password
//TODO: check if username is already exists
//TODO: check if username already exists
//TODO: check if password contain certain characters
//TODO: save encrypted(encoded) password in DB. Be careful how to read them unencrypted
//TODO: implement login action

public class Controller implements Initializable {
    @FXML
    public Label lblInformation;
    public Label lblInformationLoginScene;

    public TextField txtFieldUsernameLoginScene;
    public TextField txtFieldUsernameRegisterScene;
    public TextField txtFieldEmailRegisterScene;
    public TextField txtFieldSearchHomepage;

    public PasswordField pwdFieldPasswordLoginScene;
    public PasswordField pwdFieldConfirmPasswordRegisterScene;
    public PasswordField pwdFieldPasswordRegisterScene;

    public Button btnLoginLoginScene;
    public Button btnRegisterLoginScene;
    public Button btnRegisterRegisterScene;
    public Button btn_team;

    public BorderPane borderPane;
    public BorderPane loginBorderPane;
    public BorderPane registerBorderPane;

    public ImageView profilePicture;
    public AnchorPane hb_team;
    public HBox hb_dashboard;
    public VBox vboxTaskItems;
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

    @FXML
    public void displayMyAccountScreen(MouseEvent mouseEvent) {
    }

    @FXML
    public void displayTeamScreen(ActionEvent actionEvent) {
        FxmlLoader fxmlLoader = new FxmlLoader();
        Pane pane = fxmlLoader.getPane("team");
        borderPane.setCenter(pane);
    }
}
