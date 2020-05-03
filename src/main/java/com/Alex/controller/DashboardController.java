package com.Alex.controller;

import com.Alex.model.Project;
import com.Alex.model.SubTask;
import com.Alex.model.Task;
import com.Alex.repository.ProjectsRepository;
import com.Alex.repository.SubTaskRepository;
import com.Alex.repository.TasksRepository;
import com.Alex.repository.UserRepository;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import org.hibernate.tool.schema.internal.StandardTableExporter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    UserRepository userRepository;
    ProjectsRepository projectsRepository;
    TasksRepository tasksRepository;
    SubTaskRepository subTaskRepository;

    public TreeTableView<Project> treeTvDashboard;
    public TreeTableColumn<Project, String> treeTvclIssues;
    private boolean isConnectionSuccessful = false;
    ObservableList<Project> projectObservableList;
    ObservableList<Task> taskObservableList;
    ObservableList<SubTask> subTaskObservableList;
    Project project;
    Task task;
    SubTask subTask;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
        initTable();
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        projectsRepository = new ProjectsRepository(entityManager);
        userRepository = new UserRepository(entityManager);
        tasksRepository = new TasksRepository(entityManager);
        subTaskRepository = new SubTaskRepository(entityManager);
    }

    public ObservableList<Project> getProjectObservableList() {
        project = new Project();
        projectObservableList = FXCollections.observableArrayList(projectsRepository.findAll());
        return projectObservableList;
    }

    public ObservableList<Task> getTaskObservableList() {
        task = new Task();
        taskObservableList = FXCollections.observableArrayList(tasksRepository.findAll());
        return taskObservableList;
    }

    public ObservableList<SubTask> getSubTaskObservableList() {
        subTask = new SubTask();
        subTaskObservableList = FXCollections.observableArrayList(subTaskRepository.findAll());
        return subTaskObservableList;
    }

    public void initTable() {
        final TreeItem<Project> root = new TreeItem<>();
        final TreeItem<Project> prj = new TreeItem<>();
        final TreeItem<Task> tsk = new TreeItem<>();

        //prj.getChildren().add(tsk);
        //prj.setExpanded(false);
        root.setExpanded(true);

        getProjectObservableList().stream().forEach((project1 -> {
            root.getChildren().add(new TreeItem<>(project1));
            }));

        getTaskObservableList().stream().forEach((task1 -> {
            tsk.getChildren().add(new TreeItem<>(task1));
        }));

//        getTaskObservableList().stream().forEach((task1 -> {
//            prj.getChildren().add(new TreeItem<>(task1).getParent(root));
//        }));

        treeTvclIssues.setCellValueFactory(new TreeItemPropertyValueFactory<>("projectName"));
        treeTvDashboard.setShowRoot(false);
        //root.getChildren().add(tsk);
        treeTvDashboard.setRoot(root);

//        treeTvclIssues.setCellValueFactory(new TreeItemPropertyValueFactory<>("projectName"));
//        TreeItem<Project> root = new TreeItem<>();
//        //List<TreeItem<Project>> prj = new ArrayList<>();
//        for (Project p : getProjectObservableList()) {
//            root = new TreeItem<>(p);
//            //prj.add(root);
//        }
//        treeTvDashboard.setRoot(root);
//    }
//
//    public void initItem(){
//
//    }
//
//
//    public void editTableColumn() {
//
    }

//    public static class Item{
//        private Project projectItem = new Project();
//        private Task taskItem = new Task();
//        private SubTask subTaskItem = new SubTask();
//
//        public Item() {
//        }
//
//        public Item(Project projectItem) {
//            this.projectItem = projectItem;
//        }
//
//        public Item(Task taskItem) {
//            this.taskItem = taskItem;
//        }
//
//        public Item(SubTask subTaskItem) {
//            this.subTaskItem = subTaskItem;
//        }
//
//        public Item(Project projectItem, Task taskItem, SubTask subTaskItem) {
//            this.projectItem = projectItem;
//            this.taskItem = taskItem;
//            this.subTaskItem = subTaskItem;
//        }
//
//        public Project getProjectItem() {
//            return projectItem;
//        }
//
//        public void setProjectItem(Project projectItem) {
//            this.projectItem = projectItem;
//        }
//
//        public Task getTaskItem() {
//            return taskItem;
//        }
//
//        public void setTaskItem(Task taskItem) {
//            this.taskItem = taskItem;
//        }
//
//        public SubTask getSubTaskItem() {
//            return subTaskItem;
//        }
//
//        public void setSubTaskItem(SubTask subTaskItem) {
//            this.subTaskItem = subTaskItem;
//        }
//    }
}
