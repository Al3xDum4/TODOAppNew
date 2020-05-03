package com.Alex.model;

import javax.persistence.*;
import java.util.List;

//TODO: User class: One to **** relationship to task. See virtual store implementation

@Entity
@Table(name = "users")
public class User {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO-INCREMENT
    @Column(name = "user_id")
    private int user_id;
    private String username;
    private String password;
    @Column(name = "role")
    private UserRole userRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToMany
    @JoinTable(name = "working_project", joinColumns = @JoinColumn(name = "user_id"),//current entity -> user
            inverseJoinColumns = @JoinColumn(name = "project_id"))//foreign entity -> project
    private List<Project> projects;

    @Column(name = "email")
    private String mailUser;

    @OneToOne(mappedBy = "user")
    private PendingUser pendingUser;

    @Column(name = "status")
    private UserStatus userStatus;

    public User() {
    }

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailUser() {
        return mailUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
