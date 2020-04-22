package com.Alex.controller;

import com.Alex.model.Project;
import com.Alex.model.User;
import com.Alex.repository.ProjectsRepository;
import com.Alex.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MyProjectController implements Initializable {
    public Button btnAddProject;
    public Button btnRemoveProject;
    public TableColumn<Project, String> tvclName;
    public TableColumn<Project, String> tvclStart;
    public TableColumn<Project, String> tvclDeadline;
    public TableColumn<Project, User> tvclAlloes;
    public TableColumn<Project, User> tvclCoordinator;
    public TableView tvProjects;
    public TextField txtFieldProject;
    public Button btnSearch;
    public DatePicker dtpkStart;
    public DatePicker dtpkDeadline;
    public ComboBox cmbCoordinator;
    public ComboBox cmdAllows;
    ProjectsRepository projectsRepository;
    Project project;
    private boolean isConnectionSuccessful = false;
    public ObservableList<Project> tvProjectsList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is allowed");
            isConnectionSuccessful = false;
        }

        tvProjectsList.addAll(project);
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        projectsRepository = new ProjectsRepository(entityManager);
    }


//    private void fillTable() {
//        tvProjectsList.addAll(project);
//    }

    @FXML
    public void addProject(ActionEvent actionEvent) {
        Project project = projectsRepository.findByName(txtFieldProject.getText());
        if (txtFieldProject.getText().length() > 1 && project == null && dtpkStart.getValue() != null && dtpkDeadline != null) {
            project = new Project();
            project.setProjectName(txtFieldProject.getText());
            project.setProjectStartDate(dtpkStart);
            project.setProjectDeadline(dtpkDeadline);
            projectsRepository.save(project);
        } else if (txtFieldProject.getText().length() < 1 && project != null) {
            txtFieldProject.setStyle("-fx-border-color: red");
        } else if (dtpkStart.getValue() == null) {
            dtpkStart.setStyle("-fx-border-color: red");
        } else {
            dtpkDeadline.setStyle("-fx-border-color: red");
        }
    }

    @FXML
    public void removeProject(ActionEvent actionEvent) {

    }

    @FXML
    public void searchProject(ActionEvent actionEvent) {

    }
}
