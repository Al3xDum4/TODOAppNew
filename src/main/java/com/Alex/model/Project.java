package com.Alex.model;

import javafx.scene.control.TreeItem;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int project_id;
    @Column(name = "name")
    private String projectName;
    @Column(name = "start_date")
    private Date projectStartDate;
    @Column(name = "deadline")
    private Date projectDeadline;
    private String projectDescription;
    private boolean projectCompleted;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> taskList;

    @ManyToMany(mappedBy = "projects")
    private List<User> users;

    public Project() {
    }

    public int getId_project() {
        return project_id;
    }

    public void setId_project(int id_project) {
        this.project_id = id_project;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public Date getProjectDeadline() {
        return projectDeadline;
    }

    public void setProjectDeadline(Date projectDeadline) {
        this.projectDeadline = projectDeadline;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public boolean isProjectCompleted() {
        return projectCompleted;
    }

    public void setProjectCompleted(boolean projectCompleted) {
        this.projectCompleted = projectCompleted;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return this.projectName;
    }
}
