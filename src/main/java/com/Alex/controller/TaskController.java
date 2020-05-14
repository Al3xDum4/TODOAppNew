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
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TaskController implements Initializable {

    public TextField txtFieldSearchTask;
    public TextField txtFieldAddSubTask;
    public Button btnSearchTask;
    public Button btnAddSubTask;
    public ComboBox<Task> cmbChooseTask;

    public TreeTableView<Task> treeTableTask_Subtask;
    public TreeTableColumn<Task, String> treeClIssues;
    public TreeTableColumn<Task, String> treeClStart;
    public TreeTableColumn<Task, String> treeClDeadline;
    public TreeTableColumn<Task, String> treeClProgress;
    public TreeTableColumn<SubTask, String> treeClDone;

    private Boolean isConnectionSuccessful = false;

    public ObservableList<Task> taskList;
    public ObservableList<SubTask> subTaskList;

    private User user;
    private Project project;
    private Task task;
    private SubTask subTask;

    private UserRepository userRepository;
    private ProjectsRepository projectsRepository;
    private TasksRepository tasksRepository;
    private SubTaskRepository subTaskRepository;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
        initColumnIssues(treeTableTask_Subtask);
        initColumns();

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
        return taskList;
    }

    public ObservableList<SubTask> getSubTaskList() {
        subTask = new SubTask();
        subTaskList = FXCollections.observableArrayList(subTaskRepository.findAll());
        return subTaskList;
    }

    public void initColumns() {
        treeClIssues.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        treeClStart.setCellValueFactory(new TreeItemPropertyValueFactory<>("startDate"));
        treeClDeadline.setCellValueFactory(new TreeItemPropertyValueFactory<>("deadline"));
    }

    public void initColumnIssues(TreeTableView<Task> tree) {
        List<Object> objects = new ArrayList<>();
        objects.addAll(getTaskList());
        objects.addAll(getSubTaskList());
        TreeItem<Task> root = new TreeItem<>(new Task());

        List<TreeItem<Task>> tskItem = new ArrayList<>();
        for (Object o : objects) {
            if (o instanceof Task) {
                TreeItem taskItem = new TreeItem(o);
                for (SubTask subTask : ((Task) o).getSubTaskList()) {
                    TreeItem taskSubTask = new TreeItem(subTask);
                    taskItem.getChildren().add(taskSubTask);
                }
                tskItem.add(taskItem);
            }
        }

        root.getChildren().addAll(tskItem);
        tree.setRoot(root);
        tree.setShowRoot(false);
        tree.getStylesheets().add("CSS/dashboardTreeTable.css");
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
            subTask.setName(txtFieldAddSubTask.getText());
            subTask.setTask(task);
            subTaskRepository.save(subTask);
            txtFieldAddSubTask.clear();
            cmbChooseTask.getEditor().clear();
        } else if (project == null) {
            cmbChooseTask.setStyle("-fx-border-color: red");
        } else {
            txtFieldAddSubTask.setStyle("-fx-border-color: red");
        }
    }
}
