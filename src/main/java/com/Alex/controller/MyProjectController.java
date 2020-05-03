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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class MyProjectController implements Initializable {
    public Button btnAddProject;
    public Button btnRemoveProject;
    public TableColumn<Project, String> tvclName;
    public TableColumn<Project, String> tvclStart;
    public TableColumn<Project, String> tvclDeadline;
    public TableView<Project> tvProjects;
    public TextField txtFieldProject;
    public Button btnSearch;
    public DatePicker dtpkStart;
    public DatePicker dtpkDeadline;
    public ComboBox<User> cmbCoordinator;
    public ComboBox<User> cmdAllows;
    public Label lblInfoProject;
    public TextField txtFieldAddTask;
    public ComboBox<Project> cmbChooseProject;
    public DatePicker dateTaskStart;
    public DatePicker dateTaskDeadline;
    public Button btnAddTask;
    public Button btnRemoveTask;
    ProjectsRepository projectsRepository;
    Project project;
    UserRepository userRepository;
    private boolean isConnectionSuccessful = false;
    public ObservableList<Project> tvProjectsList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
        initColumns();
        tvProjects.refresh();
        tvProjects.setItems(getTvProjectsList());
        //tvProjects.refresh();
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        projectsRepository = new ProjectsRepository(entityManager);
        userRepository = new UserRepository(entityManager);

    }

    private void initColumns() {
        tvclName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        tvclStart.setCellValueFactory(new PropertyValueFactory<>("projectStartDate"));
        tvclDeadline.setCellValueFactory(new PropertyValueFactory<>("projectDeadline"));

        tvclName.setSortType(TableColumn.SortType.DESCENDING);

    }

    public ObservableList<Project> getTvProjectsList() {
        project = new Project();
        tvProjectsList = FXCollections.observableArrayList(projectsRepository.findAll());
        if (tvProjectsList == null) {
            return FXCollections.observableArrayList();
        }
        return tvProjectsList;
    }


    @FXML
    public void addProject(ActionEvent actionEvent) {
        Project project = projectsRepository.findByName(txtFieldProject.getText());
        if (txtFieldProject.getText().length() > 1 && project == null && dtpkStart.getValue() != null && dtpkDeadline != null) {
            project = new Project();
            project.setProjectName(txtFieldProject.getText());
            project.setProjectStartDate(Date.from(dtpkStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            project.setProjectDeadline(Date.from(dtpkDeadline.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            projectsRepository.save(project);
            txtFieldProject.clear();
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
        Project project = projectsRepository.findByName(txtFieldProject.getText());
        if (txtFieldProject.getText().length() > 1 && project != null) {
 //           project = new Project();
//            project.setProjectName(txtFieldProject.getText());
            projectsRepository.deleteById(project.getId_project());
            txtFieldProject.clear();
        }else if(txtFieldProject.getText().length()<1 && project== null){
            txtFieldProject.setStyle("-fd-border-color: red");
            lblInfoProject.setText("try again, pick an existing project");
        }
    }

    @FXML
    public void searchProject(ActionEvent actionEvent) {

    }

    public void addTask(ActionEvent actionEvent) {
        Project project = cmbChooseProject.getValue();

    }

    public void removeTask(ActionEvent actionEvent) {

    }
}
