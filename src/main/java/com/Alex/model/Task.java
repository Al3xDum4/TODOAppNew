package com.Alex.model;

import javafx.beans.property.BooleanProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int id_task;
    @Column(name = "name")
    private String name;

    @ManyToOne
    private User user;

    @ManyToOne
    //@Column(name = "project")
    private Project project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<SubTask> subTaskList;

    private String taskDescription;
    private Date startDate;
    private Date deadline;
    private boolean choose;
    private boolean inProgress;
    private boolean taskCompleted;

    public Task() {
    }

    public int getId_task() {
        return id_task;
    }

    public void setId_task(int id_task) {
        this.id_task = id_task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date taskStartDate) {
        this.startDate = taskStartDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date taskDeadline) {
        this.deadline = taskDeadline;
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    public List<SubTask> getSubTaskList() {
        return subTaskList;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSubTaskList(List<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
