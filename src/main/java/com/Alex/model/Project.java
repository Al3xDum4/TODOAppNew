package com.Alex.model;

import javafx.scene.control.DatePicker;

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
    private DatePicker projectStartDate;
    private DatePicker projectDeadline;
    private String projectDescription;
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

    public DatePicker getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(DatePicker projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public DatePicker getProjectDeadline() {
        return projectDeadline;
    }

    public void setProjectDeadline(DatePicker projectDeadline) {
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


}
