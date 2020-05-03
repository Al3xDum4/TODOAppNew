package com.Alex.controller;

import com.Alex.model.Project;
import com.Alex.model.Task;
import com.Alex.model.User;
import com.Alex.repository.ProjectsRepository;
import com.Alex.repository.TasksRepository;
import com.Alex.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.ResourceBundle;

public class TaskController implements Initializable {
    public TableView<Task> tvTasks;
    public TableColumn<Task, String> clTaskDescription;
    public TableColumn<Task, String> clStartDate;
    public TableColumn<Task, String> clDeadline;
    public TableColumn<Task, String> clProgress;
    public TableColumn<Task, String> clProject;
    public TextField txtFieldSearchTask;
    public TextField txtFieldAddSubTask;
    public Button btnSearchTask;
    public Button btnAddSubTask;
    private Boolean isConnectionSuccessful = false;
    private User user;
    private UserRepository userRepository;
    private TasksRepository tasksRepository;
    private Project project;
    private ProjectsRepository projectsRepository;



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

    public void searchTask(ActionEvent actionEvent) {

    }

    public void addSubTask(ActionEvent actionEvent) {

    }
}
