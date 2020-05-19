package com.Alex.controller;

import com.Alex.model.Project;
import com.Alex.model.SubTask;
import com.Alex.model.Task;
import com.Alex.model.User;
import com.Alex.repository.ProjectsRepository;
import com.Alex.repository.SubTaskRepository;
import com.Alex.repository.TasksRepository;
import com.Alex.repository.UserRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public TreeTableColumn<Project, Boolean> treeTvclChoose;
    public TreeTableColumn treeTvclAssign;
    public TreeTableColumn treeTvclProgress;
    public TreeTableColumn<Object, String> treeTvclStart;
    public TreeTableColumn<Object, String> treeTvclDeadline;

    private UserRepository userRepository;
    private ProjectsRepository projectsRepository;
    private TasksRepository tasksRepository;
    private SubTaskRepository subTaskRepository;
    @FXML
    private TreeTableView<Project> treeTvDashboard;
    @FXML
    private TreeTableColumn<Project, String> treeTvclIssues;
    private ObservableList<Project> projectObservableList;
    private ObservableList<Task> taskObservableList;
    private ObservableList<SubTask> subTaskObservableList;
    private Project project;
    private Task task;
    private SubTask subTask;
    private boolean isConnectionSuccessful = false;
    private User user;
    CheckBoxTreeTableCell<Object, Boolean> cell;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            persistenceConnection();
        } catch (Exception ex) {
            System.out.println("Connection is not allowed");
            isConnectionSuccessful = false;
        }
        treeTvDashboard.setEditable(true);
        setupColumnIssues(treeTvDashboard);
        initColumns();
        //chooseTask();
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

    public void setupColumnIssues(TreeTableView<Project> tree) {
        List<Object> objects = new ArrayList<>();
        objects.addAll(getProjectObservableList());
        objects.addAll(getTaskObservableList());
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

//    public void chooseTask() {
//        treeTvclChoose.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Project, Boolean>, //
//                ObservableValue<Boolean>>() {
//
//            @Override
//            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<Project, Boolean> param) {
//                TreeItem<Project> treeItem = param.getValue();
//                Project prj = treeItem.getValue();
//
//                    SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(prj.isChoose());
//                    booleanProp.addListener((observable, oldValue, newValue) -> prj.setChoose(newValue));
//                    return booleanProp;
//                }
//        });
//    }

    public void initColumns() {
        treeTvclIssues.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        treeTvclStart.setCellValueFactory(new TreeItemPropertyValueFactory<>("startDate"));
        treeTvclDeadline.setCellValueFactory(new TreeItemPropertyValueFactory<>("deadline"));

        treeTvclChoose.setCellFactory(new Callback<TreeTableColumn<Project, Boolean>, TreeTableCell<Project, Boolean>>() {
            @Override
            public TreeTableCell<Project, Boolean> call(TreeTableColumn<Project, Boolean> objectBooleanTreeTableColumn) {
                return new CheckBoxTreeTableCell<Project, Boolean>() {
                    @Override
                    public void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            TreeItem item = treeTvDashboard.getTreeItem(getIndex());
                            if (item == null || !(item.getValue() instanceof Task)) {
                                setGraphic(null);
                            } else {
                                setAlignment(Pos.CENTER);
                            }
                        }
                    }
                };
            }
        });
    }
}
