package com.Alex.controller;

import com.Alex.model.User;
import com.Alex.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;

public class Controller {
    @FXML
    public Label lblInformation;
    public Label lblInformationLoginScene;
    public TextField txtFieldUsernameLoginScene;
    public PasswordField pwdFieldPasswordLoginScene;
    public Button btnLoginLoginScene;
    public Button btnRegisterLoginScene;
    public TextField txtFieldUsernameRegisterScene;
    public TextField txtFieldEmailRegisterScene;
    public PasswordField pwdFieldConfirmPasswordRegisterScene;
    public Button btnRegisterRegisterScene;
    public PasswordField pwdFieldPasswordRegisterScene;
    public BorderPane loginBorderPane;
    private UserRepository userRepository;
    private boolean isConnectionSuccessful = false;


    public void initialize() {
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

    //TODO: add scene for different
    public Controller() {
    }

    @FXML
    private void registerUser(ActionEvent actionEvent) {
        //TODO: Add button to show password
        //TODO: Check if username already exists
        //TODO: Check if password contains certain characters
        //TODO: Save encoded password in DB
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
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("register.fxml");
        BorderPane borderPane = fxmlLoader.load(resourceAsStream);

        loginBorderPane.getChildren().setAll(borderPane);

//        Scene registerScene = new Scene(registerParent);
//        Stage window = new Stage();
//        window.setScene(registerScene);
//        window.show();
    }
}
