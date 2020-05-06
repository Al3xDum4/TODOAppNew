package com.Alex.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subTasks")
public class SubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subTask_id")
    private int id_subTask;
    private String subTaskName;
    private String subTaskDescription;
    private boolean subTaskCompleted;

    //@Column(name="task")
    @ManyToOne
    private Task task;


    public SubTask() {
    }

    public int getId_subTask() {
        return id_subTask;
    }

    public void setId_subTask(int id_subTask) {
        this.id_subTask = id_subTask;
    }

    public String getSubTaskName() {
        return subTaskName;
    }

    public void setSubTaskName(String subTaskName) {
        this.subTaskName = subTaskName;
    }

    public String getSubTaskDescription() {
        return subTaskDescription;
    }

    public void setSubTaskDescription(String subTaskDescription) {
        this.subTaskDescription = subTaskDescription;
    }

    public boolean isSubTaskCompleted() {
        return subTaskCompleted;
    }

    public void setSubTaskCompleted(boolean subTaskCompleted) {
        this.subTaskCompleted = subTaskCompleted;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return this.subTaskName;
    }
}
