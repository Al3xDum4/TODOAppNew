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
    private String name;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "deadline")
    private Date deadline;
    private String projectDescription;
    private boolean projectCompleted;
    private boolean choose;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date projectStartDate) {
        this.startDate = projectStartDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date projectDeadline) {
        this.deadline = projectDeadline;
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

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
