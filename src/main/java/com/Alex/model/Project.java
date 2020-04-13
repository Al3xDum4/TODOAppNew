package com.Alex.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_project;
    private String projectName;
    private Date projectStartDate;
    private Date projectDeadline;
    private String projectDescription;
    private List<User> projectAssignUsers;
    private List<Task> tasksList;
    private boolean projectCompleted;

    public Project() {
    }

    public int getId_project() {
        return id_project;
    }

    public void setId_project(int id_project) {
        this.id_project = id_project;
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

    public List<User> getProjectAssignUsers() {
        return projectAssignUsers;
    }

    public void setProjectAssignUsers(List<User> projectAssignUsers) {
        this.projectAssignUsers = projectAssignUsers;
    }

    public List<Task> getTasksList() {
        return tasksList;
    }

    public void setTasksList(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    public boolean isProjectCompleted() {
        return projectCompleted;
    }

    public void setProjectCompleted(boolean projectCompleted) {
        this.projectCompleted = projectCompleted;
    }


}
