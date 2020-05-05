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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class MyProjectController implements Initializable {
    public Button btnAddProject;
    public Button btnRemoveProject;
    public TableColumn<Project, String> tvclName;
    public TableColumn<Project, String> tvclStart;
    public TableColumn<Project, String> tvclDeadline;
    public TableView<Project> tvProjects_tasks;
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
    public ProjectsRepository projectsRepository;
    public Project project;
    public UserRepository userRepository;
    public TreeView treeViewProject_Task;
    private boolean isConnectionSuccessful = false;
    public ObservableList<Project> tvProjectsList;
    public Task task;
    public TasksRepository tasksRepository;
    public SubTaskRepository subTaskRepository;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
        //loadTreeView(treeViewProject_Task);
//        initColumns();
//        tvProjects_tasks.refresh();
//        tvProjects_tasks.setItems(getTvProjectsList());
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        projectsRepository = new ProjectsRepository(entityManager);
        userRepository = new UserRepository(entityManager);
        tasksRepository = new TasksRepository(entityManager);
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
            projectsRepository.deleteById(project.getId_project());
            txtFieldProject.clear();
        } else if (txtFieldProject.getText().length() < 1 && project == null) {
            txtFieldProject.setStyle("-fd-border-color: red");
            lblInfoProject.setText("try again, pick an existing project");
        }
    }

    @FXML
    public void searchProject(ActionEvent actionEvent) {

    }

    @FXML
    public void loadComboBoxProject(MouseEvent mouseEvent) {
        cmbChooseProject.setStyle("-fx-border-color: black");
        ObservableList<Project> prjList = FXCollections.observableArrayList(getTvProjectsList());
        cmbChooseProject.setItems(prjList);
    }

    public void addTask(ActionEvent actionEvent) {
        Project project = cmbChooseProject.getValue();
        if (txtFieldAddTask.getText().length() >= 1 && project != null) {
            task = new Task();
            task.setTaskName(txtFieldAddTask.getText());
            task.setTaskStartDate(Date.from(dateTaskStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            task.setTaskDeadline(Date.from(dateTaskDeadline.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            tasksRepository.save(task);
            txtFieldAddTask.clear();
        } else if (project == null) {
            cmbChooseProject.setStyle("-fx-border-color: red");
        } else {
            txtFieldAddTask.setStyle("-fx-border-color: red");
        }
    }

    public void clearTxtFldAddTask(KeyEvent keyEvent) {
        txtFieldAddTask.setStyle("-fx-border-color: black");
    }

    public void removeTask(ActionEvent actionEvent) {

    }

//    public void loadTreeView(TreeView<TreeItem> tree) {
//        ArrayList<TreeItem> products = new ArrayList<>();
//
//        TreeItem project = new TreeItem<>();
//        project.getChildren().addAll(getProject());
//
//        TreeItem task = new TreeItem();
//        task.getChildren().addAll(getTask());
//
//        TreeItem subTask = new TreeItem();
//        subTask.getChildren().addAll(getSubTask());
//
//        products.add(project);
//        products.add(task);
//        products.add(subTask);
//
//        TreeItem root = new TreeItem();
//        root.getChildren().addAll(products);
//        tree.setRoot(root);
//    }
//
//    private ArrayList<TreeItem> getProject() {
//        ArrayList<TreeItem> project = new ArrayList<>();
//        ObservableList<Project> prjList = FXCollections.observableArrayList(projectsRepository.findAll());
//        for (Project p : prjList) {
//            project.add(new TreeItem(p));
//        }
//        return project;
//    }
//
//    private ArrayList<TreeItem> getTask() {
//        ArrayList<TreeItem> tasks = new ArrayList<TreeItem>();
//        Project project = new Project();
//        ObservableList<Task> tasks1 = FXCollections.observableArrayList(tasksRepository.findAllTaskAssign(project.getId_project()));
//        for (Task t : tasks1) {
//            tasks.add(new TreeItem(t));
//        }
//        return tasks;
//    }
//
//    private ArrayList<TreeItem> getSubTask() {
//        ArrayList<TreeItem> subTasks = new ArrayList<TreeItem>();
//        Task task = new Task();
//        ObservableList<SubTask> subTasks1 = FXCollections.observableArrayList(subTaskRepository.findAllSubTaskAssign(task.getId_task()));
//        for (SubTask sbt : subTasks1) {
//            subTasks.add(new TreeItem(sbt));
//        }
//        return subTasks;
//    }
}
