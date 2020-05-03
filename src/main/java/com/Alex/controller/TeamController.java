package com.Alex.controller;

import com.Alex.model.User;
import com.Alex.model.UserRole;
import com.Alex.model.UserStatus;
import com.Alex.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.ResourceBundle;

public class TeamController implements Initializable {
    public ChoiceBox<UserStatus> chBoxUserStatus;
    public ChoiceBox<UserRole> chBoxUserSetRole;
    public TableView<User> tvTeam;
    public TableColumn<User, Integer> tvclTeamId;
    public TableColumn<User, String> tvclTeamName;
    public TableColumn<User, UserRole> tvclTeamRole;
    public TableColumn<User, UserStatus> tvclTeamActive;
    public User user;
    public ObservableList<User> tvUsersList;
    public UserRepository userRepository;
    public Button btnSaveEditUser;
    private boolean isConnectionSuccessful = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
        initColumns();
        tvTeam.setItems(getTvUsersList());
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        userRepository = new UserRepository(entityManager);
    }

    public void initColumns() {
        tvclTeamId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        tvclTeamName.setCellValueFactory(new PropertyValueFactory<>("username"));
        tvclTeamRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        tvclTeamActive.setCellValueFactory(new PropertyValueFactory<>("userStatus"));
    }

    public ObservableList<User> getTvUsersList() {
        user = new User();
        tvUsersList = FXCollections.observableArrayList(userRepository.findAll());
        return tvUsersList;
    }

    public void fillChBoxUserStatus(MouseEvent mouseEvent) {
        ObservableList<UserStatus> statusList =
                FXCollections.observableArrayList(UserStatus.ACTIVE, UserStatus.BLOCK, UserStatus.SUSPENDED);
        chBoxUserStatus.setItems(statusList);
    }

    public void fillChBoxUserRole(MouseEvent mouseEvent) {
        ObservableList<UserRole> roleList =
                FXCollections.observableArrayList(UserRole.ADMIN, UserRole.DEVELOPER, UserRole.PROJECT_MANAGER);
        chBoxUserSetRole.setItems(roleList);
    }

    public void saveEditUser(ActionEvent actionEvent) {
        User user = tvTeam.getSelectionModel().getSelectedItem();
        //user.setUserRole(fillChBoxUserRole();
    }
}
