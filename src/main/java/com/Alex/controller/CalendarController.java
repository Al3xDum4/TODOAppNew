package com.Alex.controller;

import com.Alex.repository.ProjectsRepository;
import com.Alex.repository.SubTaskRepository;
import com.Alex.repository.TasksRepository;
import com.Alex.repository.UserRepository;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    public StackPane stkCalendar;

    private UserRepository userRepository;
    private ProjectsRepository projectsRepository;
    private TasksRepository tasksRepository;
    private SubTaskRepository subTaskRepository;

    private boolean isConnectionSuccessful = false;


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
        projectsRepository = new ProjectsRepository(entityManager);
        userRepository = new UserRepository(entityManager);
        tasksRepository = new TasksRepository(entityManager);
        subTaskRepository = new SubTaskRepository(entityManager);
    }

    public void setupCalendar() {


    }
}
