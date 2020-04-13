package com.Alex.controller;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class ScreenController {
    private HashMap<String, VBox> screenMap = new HashMap<>();
    private Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    protected void addScreen(String name, VBox vBox){
        screenMap.put(name, vBox);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name){
        main.setRoot( screenMap.get(name) );
    }
}
