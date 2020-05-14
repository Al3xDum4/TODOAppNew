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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    public Button btn_showPassword;
    public Button btnLoginRegisterScene;
    public Button btn_showPassword1;
    public TextField txtFieldPassRegister;
    public TextField txtFieldConfirmPassRegister;
    public UserRepository userRepository;
    public Boolean isConnectionSuccessful = false;
    public Stage registerWindow = new Stage();
    public User user;

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

    @FXML
    private void registerUser(ActionEvent actionEvent) {
        User user = userRepository.findByUsername(txtFieldUsernameRegisterScene.getText());
        if (txtFieldUsernameRegisterScene.getText().isEmpty()) {
            iconUsernameRegister.setFill(Color.RED);
            txtFieldUsernameRegisterScene.setPromptText("enter your username");
            pwdFieldPasswordRegisterScene.setEditable(false);
            pwdFieldConfirmPasswordRegisterScene.setEditable(false);
        } else if (txtFieldUsernameRegisterScene.getText().length() < 8) {
            iconUsernameRegister.setFill(Color.RED);
            lblRegisterUsernameMessage.setVisible(true);
            lblRegisterUsernameMessage.setText("must contain at least 8 characters");
        } else if (user != null) {
            iconUsernameRegister.setFill(Color.RED);
            lblRegisterUsernameMessage.setVisible(true);
        } else {
            if (pwdFieldPasswordRegisterScene.getText().isEmpty()) {
                iconPasswordRegister.setFill(Color.RED);
                pwdFieldPasswordRegisterScene.setPromptText("enter your password");
                //pwdFieldConfirmPasswordRegisterScene.setEditable(false);
            } else if (!pwdFieldPasswordRegisterScene.getText().matches("(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")) {
                iconPasswordRegister.setFill(Color.RED);
                lblRegisterPasswordMessage.setVisible(true);
                lblRegisterPasswordMessage.setText("must contain least 8 characters of which 1 digit)");
                //pwdFieldConfirmPasswordRegisterScene.setEditable(true);
            } else if(!pwdFieldConfirmPasswordRegisterScene.getText().equals(pwdFieldPasswordRegisterScene.getText())){
                iconConfrimPasswordRegister.setFill(Color.RED);
                lblRegisterPasswordMessage.setVisible(true);
                lblRegisterPasswordMessage.setText("confirm your password");
            }else if (pwdFieldConfirmPasswordRegisterScene.getText().isEmpty()) {
                iconConfrimPasswordRegister.setFill(Color.RED);
                pwdFieldConfirmPasswordRegisterScene.setPromptText("confirm your password");
                btnRegisterRegisterScene.setDisable(true);
                lblRegisterPasswordMessage.setVisible(true);
                lblRegisterPasswordMessage.setText("please fill in the blanks");
            } else {
                user = new User();
                user.setUsername(txtFieldUsernameRegisterScene.getText());
                user.setPassword(pwdFieldPasswordRegisterScene.getText());
                user.setMailUser(txtFieldEmailRegisterScene.getText());
                userRepository.save(user);
                registerWindow = (Stage) btnRegisterRegisterScene.getScene().getWindow();
                registerWindow.close();
            }
        }
    }

//    public boolean checkTxtFldReg() {
//        this.user = userRepository.findByUsername(txtFieldUsernameRegisterScene.getText());
//        if (txtFieldUsernameRegisterScene.getText().isEmpty()) {
//            iconUsernameRegister.setFill(Color.RED);
//            txtFieldUsernameRegisterScene.setPromptText("enter your username");
//            pwdFieldPasswordRegisterScene.setEditable(false);
//            pwdFieldConfirmPasswordRegisterScene.setEditable(false);
//            return false;
//        } else if (txtFieldUsernameRegisterScene.getText().length() < 8) {
//            iconUsernameRegister.setFill(Color.RED);
//            lblRegisterUsernameMessage.setVisible(true);
//            lblRegisterUsernameMessage.setText("must contain at least 8 characters");
//            pwdFieldPasswordRegisterScene.setEditable(false);
//            pwdFieldConfirmPasswordRegisterScene.setEditable(false);
//            return false;
//        } else if (user != null) {
//            iconUsernameRegister.setFill(Color.RED);
//            lblRegisterUsernameMessage.setVisible(true);
//            pwdFieldPasswordRegisterScene.setEditable(false);
//            pwdFieldConfirmPasswordRegisterScene.setEditable(false);
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public boolean checkPwdFldPassReg() {
//        if (pwdFieldPasswordRegisterScene.getText().isEmpty()) {
//            iconPasswordRegister.setFill(Color.RED);
//            pwdFieldPasswordRegisterScene.setPromptText("enter your password");
//            pwdFieldConfirmPasswordRegisterScene.setEditable(false);
//            return false;
//        } else if (!pwdFieldPasswordRegisterScene.getText().matches("(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")) {
//            iconPasswordRegister.setFill(Color.RED);
//            lblRegisterPasswordMessage.setVisible(true);
//            lblRegisterPasswordMessage.setText("must contain least 8 characters of which 1 digit)");
//            pwdFieldConfirmPasswordRegisterScene.setEditable(false);
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public boolean checkPwdFldConfPassReg() {
//        if (pwdFieldConfirmPasswordRegisterScene.getText().isEmpty()) {
//            iconConfrimPasswordRegister.setFill(Color.RED);
//            pwdFieldConfirmPasswordRegisterScene.setPromptText("confirm your password");
//            btnRegisterRegisterScene.setDisable(true);
//            lblRegisterPasswordMessage.setVisible(true);
//            lblRegisterPasswordMessage.setText("please fill in the blanks");
//            return false;
//        } else if (!pwdFieldConfirmPasswordRegisterScene.getText().equals(pwdFieldPasswordRegisterScene.getText())) {
//            iconConfrimPasswordRegister.setFill(Color.RED);
//            pwdFieldConfirmPasswordRegisterScene.setPromptText("confirm your password");
//            btnRegisterRegisterScene.setDisable(true);
//            lblRegisterPasswordMessage.setVisible(true);
//            lblRegisterPasswordMessage.setText("no match with password");
//            return false;
//        } else {
//            return true;
//        }
//    }

    public void clearErrorIssueTxtUserRegister(KeyEvent keyEvent) {
        iconUsernameRegister.setFill(Color.BLACK);
        lblRegisterUsernameMessage.setVisible(false);
        txtFieldUsernameRegisterScene.setPromptText("username");
    }

    public void clearErrorIssuePwdRegister(KeyEvent keyEvent) {
        iconPasswordRegister.setFill(Color.BLACK);
        lblRegisterPasswordMessage.setVisible(false);
        pwdFieldPasswordRegisterScene.setPromptText("password");
    }

    public void clearErrorIssuePwdConfirmRegister(KeyEvent keyEvent) {
        iconConfrimPasswordRegister.setFill(Color.BLACK);
        pwdFieldConfirmPasswordRegisterScene.setPromptText("confirm password");
    }

    @FXML
    public void clearTxtFieldUserReg(MouseEvent mouseEvent) {
        txtFieldUsernameRegisterScene.clear();
    }

    @FXML
    public void clearPwdFldPassReg(MouseEvent mouseEvent) {
        pwdFieldPasswordRegisterScene.clear();
    }

    @FXML
    public void clearPwdFldConfPassReg(MouseEvent mouseEvent) {
        pwdFieldConfirmPasswordRegisterScene.clear();
    }

    public void showPassword(ActionEvent actionEvent) {
        if (!txtFieldPassRegister.isVisible()) {
            txtFieldPassRegister.setText(pwdFieldPasswordRegisterScene.getText());
            txtFieldPassRegister.setEditable(false);
            txtFieldPassRegister.setVisible(true);
            pwdFieldPasswordRegisterScene.setVisible(false);
        } else {
            txtFieldPassRegister.setVisible(false);
            pwdFieldPasswordRegisterScene.setVisible(true);
        }
    }

    public void showConfirmPassword(ActionEvent actionEvent) {
        if (!txtFieldConfirmPassRegister.isVisible()) {
            txtFieldConfirmPassRegister.setText(pwdFieldConfirmPasswordRegisterScene.getText());
            txtFieldConfirmPassRegister.setEditable(false);
            txtFieldConfirmPassRegister.setVisible(true);
            pwdFieldConfirmPasswordRegisterScene.setVisible(false);
        } else {
            txtFieldConfirmPassRegister.setVisible(false);
            pwdFieldConfirmPasswordRegisterScene.setVisible(true);
        }
    }

    public void goBackToLoginScene(ActionEvent actionEvent) {
        registerWindow = (Stage) btnLoginRegisterScene.getScene().getWindow();
        registerWindow.close();
    }
}
