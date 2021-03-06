package com.Alex.controller;

import com.Alex.model.User;
import com.Alex.repository.UserRepository;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public BorderPane loginBorderPane;
    public Label lblInformationLoginScene;
    public FontAwesomeIconView iconUsernameLogin;
    public TextField txtFieldUsernameLoginScene;
    public FontAwesomeIconView iconPasswordLogin;
    public PasswordField pwdFieldPasswordLoginScene;
    public Button btn_showPassword;
    public TextField txtFieldPassword;
    public Button btnLoginLoginScene;
    public Button btnRegisterLoginScene;
    public Label lblLoginUsernameMessage;
    public Label lblLoginPasswordMessage;

    public UserRepository userRepository;
    private Boolean isConnectionSuccessful = false;

    Stage loginWindow = new Stage();
    Stage registerWindow = new Stage();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userRepository = new UserRepository(entityManager);
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

    @FXML
    public void loginUser(ActionEvent actionEvent) {
        User user = userRepository.findByUsername(txtFieldUsernameLoginScene.getText());
        if (user != null) {
            if (pwdFieldPasswordLoginScene.getText().equals(user.getPassword())) {
                lblInformationLoginScene.setText("successfully connected !");
                loginWindow = (Stage) btnLoginLoginScene.getScene().getWindow();
                loginWindow.close();
            } else if (!pwdFieldPasswordLoginScene.getText().equals(user.getPassword()) && pwdFieldPasswordLoginScene.getText().length() >= 1) {
                iconPasswordLogin.setFill(Color.RED);
                lblLoginPasswordMessage.setVisible(true);
            } else {
                iconPasswordLogin.setFill(Color.RED);
                pwdFieldPasswordLoginScene.setPromptText("enter your password");
            }
        } else if (txtFieldUsernameLoginScene.getText().length() < 1) {
            iconUsernameLogin.setFill(Color.RED);
            txtFieldUsernameLoginScene.setPromptText("enter your username");
        } else {
            iconUsernameLogin.setFill(Color.RED);
            lblLoginUsernameMessage.setVisible(true);
        }
    }

    @FXML
    public void clearErrorIssueTxtUserLogin(KeyEvent keyEvent) {
        iconUsernameLogin.setFill(Color.BLACK);
        lblLoginUsernameMessage.setVisible(false);
        txtFieldUsernameLoginScene.setPromptText("username");
    }

    @FXML
    public void clearTxtField(MouseEvent mouseEvent) {
        txtFieldUsernameLoginScene.clear();
    }

    @FXML
    public void clearErrorIssuePwdUserLogin(KeyEvent keyEvent) {
        iconPasswordLogin.setFill(Color.BLACK);
        lblLoginPasswordMessage.setVisible(false);
        pwdFieldPasswordLoginScene.setPromptText("password");
    }

    @FXML
    public void clearPwdField(MouseEvent mouseEvent) {
        pwdFieldPasswordLoginScene.clear();
    }

    @FXML
    public void goToRegisterScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("FXML/register.fxml");
        Parent root3 = fxmlLoader.load(resourceAsStream);
        Scene registerScene = new Scene(root3);
        registerWindow = new Stage();
        registerWindow.setScene(registerScene);
        registerWindow.initStyle(StageStyle.TRANSPARENT);
        loginWindow.close();
        registerWindow.show();
    }
}
