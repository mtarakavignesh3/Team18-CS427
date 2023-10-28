package edu.uiuc.cs427app;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;

    private String theme;
    private List<String> savedPlaces;

    public User(String username) {
        this.username = username;
        this.theme = "Light";
        this.savedPlaces = new ArrayList<>();
    }
    public User(String username, String theme) {
        this.username = username;
        this.theme = theme;
        this.savedPlaces = new ArrayList<>();
    }
    public User(String username, String theme, List<String> savedPlaces) {
        this.username = username;
        this.theme = theme;
        this.savedPlaces = savedPlaces;
    }
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getSavedPlaces() {
        return savedPlaces;
    }
}