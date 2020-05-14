package com.Alex.controller;

import com.Alex.model.*;
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
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MyProjectController implements Initializable {
    public Button btnAddProject;
    public Button btnRemoveProject;
    public TextField txtFieldProject;
    public Button btnSearch;
    public DatePicker dtpkStart;
    public DatePicker dtpkDeadline;
    public ComboBox<User> cmbCoordinator;
    public Label lblInfoProject;
    public TextField txtFieldAddTask;
    public ComboBox<Project> cmbChooseProject;
    public DatePicker dateTaskStart;
    public DatePicker dateTaskDeadline;
    public Button btnAddTask;
    public Button btnRemoveTask;
    public ProjectsRepository projectsRepository;
    public Project project;

    public TreeTableView<Project> treeTableProjects_Task;
    public TreeTableColumn<Project, String> treeTvClIssues;
    public TreeTableColumn<Object, String> treeTvClStart;
    public TreeTableColumn<Object, String> treeTvClDeadline;

    private boolean isConnectionSuccessful = false;
    public ObservableList<Project> tvProjectsList;
    public ObservableList<Task> tvTasksList;
    public ObservableList<SubTask> tvSubTasksList;
    public ObservableList<User> tvUsersList;
    public Task task;
    private User user;
    public TasksRepository tasksRepository;
    public SubTaskRepository subTaskRepository;
    public UserRepository userRepository;
    public SubTask subTask;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
        initColumnIssues(treeTableProjects_Task);
        initColumns();
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
        treeTvClIssues.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        treeTvClStart.setCellValueFactory(new TreeItemPropertyValueFactory<>("startDate"));
        treeTvClDeadline.setCellValueFactory(new TreeItemPropertyValueFactory<>("deadline"));
    }

    public ObservableList<Project> getTvProjectsList() {
        project = new Project();
        tvProjectsList = FXCollections.observableArrayList(projectsRepository.findAll());
        return tvProjectsList;
    }

    public ObservableList<Task> getTvTasksList() {
        task = new Task();
        tvTasksList = FXCollections.observableArrayList(tasksRepository.findAll());
        return tvTasksList;
    }

    public ObservableList<User> getTvUsersList() {
        user = new User();
        tvUsersList = FXCollections.observableArrayList(userRepository.findAll());
        return tvUsersList;
    }

    @FXML
    public void addProject(ActionEvent actionEvent) {
        Project project = projectsRepository.findByName(txtFieldProject.getText());
        if (txtFieldProject.getText().length() > 1 && project == null && dtpkStart.getValue() != null && dtpkDeadline != null) {
            project = new Project();
            project.setName(txtFieldProject.getText());
            project.setStartDate(Date.from(dtpkStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            project.setDeadline(Date.from(dtpkDeadline.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            projectsRepository.save(project);
            txtFieldProject.clear();
            dtpkStart.getEditor().clear();
            dtpkDeadline.getEditor().clear();
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
            task.setName(txtFieldAddTask.getText());
            task.setProject(project);
            task.setStartDate(Date.from(dateTaskStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            task.setDeadline(Date.from(dateTaskDeadline.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            tasksRepository.save(task);
            txtFieldAddTask.clear();
            dateTaskStart.getEditor().clear();
            dateTaskDeadline.getEditor().clear();
            cmbChooseProject.getSelectionModel().clearSelection();
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

    public void initColumnIssues(TreeTableView<Project> tree) {
        List<Object> objects = new ArrayList<>();
        objects.addAll(getTvProjectsList());
        objects.addAll(getTvTasksList());
        TreeItem<Project> root = new TreeItem<>(new Project());

        List<TreeItem<Project>> prjItem = new ArrayList<>();
        for (Object o : objects) {
            if (o instanceof Project) {
                TreeItem projectItem = new TreeItem(o);
                for (Task task : ((Project) o).getTaskList()) {
                    TreeItem projectTask = new TreeItem(task);
                    projectItem.getChildren().add(projectTask);
                }
                prjItem.add(projectItem);
            }
        }
        root.getChildren().addAll(prjItem);
        tree.setRoot(root);
        tree.setShowRoot(false);
        tree.getStylesheets().add("CSS/dashboardTreeTable.css");
    }

    public void loadCoordinatorButton(MouseEvent mouseEvent) {
        cmbCoordinator.setStyle("-fx-border-color: black");
        ObservableList<User> usrLst = FXCollections.observableArrayList(getTvUsersList());
        cmbCoordinator.setItems(usrLst.filtered(u -> u.getUserRole().equals(UserRole.ADMIN) ||
                u.getUserRole().equals(UserRole.PROJECT_MANAGER)));
    }
}
