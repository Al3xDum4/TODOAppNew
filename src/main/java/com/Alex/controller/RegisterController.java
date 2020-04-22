package com.Alex.controller;

import com.Alex.model.User;
import com.Alex.repository.UserRepository;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {


    public BorderPane registerPane;
    public Label lblInformation;
    public FontAwesomeIconView iconUsernameRegister;
    public TextField txtFieldUsernameRegisterScene;
    public MaterialDesignIconView iconEmailRegister;
    public TextField txtFieldEmailRegisterScene;
    public FontAwesomeIconView iconPasswordRegister;
    public PasswordField pwdFieldPasswordRegisterScene;
    public FontAwesomeIconView iconConfrimPasswordRegister;
    public PasswordField pwdFieldConfirmPasswordRegisterScene;
    public Button btnRegisterRegisterScene;
    public Label lblRegisterUsernameMessage;
    public Label lblRegisterPasswordMessage;

    UserRepository userRepository;
    public Boolean isConnectionSuccessful = false;
    Stage registerWindow = new Stage();
    User user;

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
}
