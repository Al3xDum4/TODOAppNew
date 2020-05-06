package com.Alex.controller;

import com.Alex.model.Project;
import com.Alex.model.SubTask;
import com.Alex.model.Task;
import com.Alex.repository.ProjectsRepository;
import com.Alex.repository.SubTaskRepository;
import com.Alex.repository.TasksRepository;
import com.Alex.repository.UserRepository;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Binding;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import org.hibernate.tool.schema.internal.StandardTableExporter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private UserRepository userRepository;
    private ProjectsRepository projectsRepository;
    private TasksRepository tasksRepository;
    private SubTaskRepository subTaskRepository;
    @FXML
    private TreeTableView<Object> treeTvDashboard;
    @FXML
    private TreeTableColumn<Object, String> treeTvclIssues;
    private ObservableList<Project> projectObservableList;
    private ObservableList<Task> taskObservableList;
    private ObservableList<SubTask> subTaskObservableList;
    private Project project;
    private Task task;
    private SubTask subTask;
    private boolean isConnectionSuccessful = false;
    private TreeItem<String> treeNode;
    private String name;

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
        TreeItem<Object> root = new TreeItem<>();
        treeTvDashboard = new TreeTableView<>(root);
        treeTvDashboard.setShowRoot(false);
        treeTvclIssues = new TreeTableColumn<>();

        treeTvclIssues.setCellValueFactory(param -> {
            Object object = ((param.getValue().getValue()));
            if (object instanceof Project) {
                return new ReadOnlyStringWrapper(String.valueOf(((Project) object).getProjectName()));
            } else if (object instanceof Task) {
                return new ReadOnlyStringWrapper(String.valueOf(((Task) object).getTaskName()));
            } else if (object instanceof SubTask) {
                return new ReadOnlyStringWrapper(String.valueOf(((SubTask) object).getSubTaskName()));
            } else {
                return null;
            }
        });

//        List<Project> prjList = buildData();
//        prjList.stream().forEach((project) -> {
//            final TreeItem prjTreeItem = new TreeItem(project);
//            root.getChildren().add(prjTreeItem);
//            project.getTaskList().stream().forEach((task) -> {
//                prjTreeItem.getChildren().add(new TreeItem(task));
//                task.getSubTaskList().stream().forEach(subTask1 -> {
//                    prjTreeItem.getChildren().add(new TreeItem<>(subTask1));
//                });
//            });
//        });

//        getProjectObservableList().stream().forEach(pr -> {
//            final TreeItem<Object> prjTreeItem = new TreeItem<>(pr);
//            root.getChildren().add(prjTreeItem);
//
//            pr.getTaskList().stream().forEach(tsk -> {
//                final TreeItem<Object> tskTreeItem = new TreeItem<>(tsk);
//                prjTreeItem.getChildren().add(new TreeItem<>(tskTreeItem));
//
//                tsk.getSubTaskList().stream().forEach(sbt->{
//                    final TreeItem<Object> sbtTreeItem = new TreeItem<>(sbt);
//                    tskTreeItem.getChildren().add(new TreeItem<>(sbtTreeItem));
//                });
//            });
//        });
    }

//    private List<Project> buildData() {
//        List<Project> projects = new ArrayList<>();
//
//        projects.add((Project) getProjectObservableList());
//        projects.add((Project) getTaskObservableList());
//        projects.add((Project) getSubTaskObservableList());
//
//        return projects;
//    }

    //            @Override
//            public Object call(Object param) {
//                Object object=((TreeTableColumn.CellDataFeatures<Object>)param).getValue().getValue();
//                if(object instanceof Project){
//                    return new ReadOnlyStringWrapper(String.valueOf(((Project)object).getProjectName()));
//                }else if(object instanceof Task){
//                    return new ReadOnlyStringWrapper(String.valueOf(((Task)object).getTaskName()));
//                }else if(object instanceof SubTask){
//                    return new ReadOnlyStringWrapper(String.valueOf(((SubTask)object).getSubTaskName()));
//                }
//                return null;
//            }
//        });

//    public void initTable() {
//        TreeItem<Project> root = new TreeItem<>();
//        TreeItem<Project> prj = null;
//        TreeItem<Task> tsk;
//        root.setExpanded(true);
//        //prj.setExpanded(true);
//
////        getProjectObservableList().stream().forEach((p -> {
//////            root.getChildren().add(new TreeItem<>(p));
//////        }));
//
//        for (Project p : getProjectObservableList()) {
//            prj = new TreeItem<>(p);
//            root.getChildren().add(prj);
//        }


//        getTaskObservableList().stream().forEach((task1 -> {
//            prj.getChildren().add(new TreeItem<>(task1));
//        }));

//        getTaskObservableList().stream().forEach((task1 -> {
//            prj.getChildren().add(new TreeItem<>(task1).getParent(root));
//        }));

//        treeTvclIssues.setCellValueFactory(new TreeItemPropertyValueFactory<>("projectName"));
//        treeTvDashboard.setShowRoot(true);
//        //root.getChildren().add(tsk);
//        treeTvDashboard.setRoot(root);

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
    // }

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
