package com.Alex.model;

import javax.persistence.*;

//TODO: User class: One to **** relationship to task. See virtual store implementation

@Entity
@Table(name = "user")
public class User {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO-INCREMENT
    private int id;
    private String username;
    private String password;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
