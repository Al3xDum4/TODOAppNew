package com.Alex.controller;

import com.Alex.model.Project;
import com.Alex.model.SubTask;
import com.Alex.model.Task;
import com.Alex.model.User;
import com.Alex.repository.ProjectsRepository;
import com.Alex.repository.SubTaskRepository;
import com.Alex.repository.TasksRepository;
import com.Alex.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
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
    public ComboBox<Task> cmbChooseTask;
    private Boolean isConnectionSuccessful = false;
    private User user;
    private UserRepository userRepository;
    private TasksRepository tasksRepository;
    private Project project;
    private ProjectsRepository projectsRepository;
    public ObservableList<Task> taskList;
    private Task task;
    private SubTaskRepository subTaskRepository;
    private SubTask subTask;


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
        projectsRepository = new ProjectsRepository(entityManager);
        tasksRepository = new TasksRepository(entityManager);
        subTaskRepository = new SubTaskRepository(entityManager);
    }

    public void searchTask(ActionEvent actionEvent) {

    }

    public ObservableList<Task> getTaskList() {
        task = new Task();
        taskList = FXCollections.observableArrayList(tasksRepository.findAll());
        if (taskList == null) {
            return FXCollections.observableArrayList();
        }
        return taskList;
    }

    public void loadComboBoxTask() {
        cmbChooseTask.setStyle("-fx-border-color: black");
        ObservableList<Task> tskList = FXCollections.observableArrayList(getTaskList());
        cmbChooseTask.setItems(tskList);
    }

    public void addSubTask(ActionEvent actionEvent) {
        Task task = cmbChooseTask.getValue();
        if (txtFieldAddSubTask.getText().length() >= 1 && task != null) {
            subTask = new SubTask();
            subTask.setSubTaskName(txtFieldAddSubTask.getText());
            subTask.setTask(task);
            subTaskRepository.save(subTask);
            txtFieldAddSubTask.clear();
        } else if (project == null) {
            cmbChooseTask.setStyle("-fx-border-color: red");
        } else {
            txtFieldAddSubTask.setStyle("-fx-border-color: red");
        }
    }
}
