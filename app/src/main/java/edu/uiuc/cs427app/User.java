package edu.uiuc.cs427app;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;

    private String theme;
    private List<String> savedPlaces;

    public User(String username) {
        this.username = username;
        this.savedPlaces = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public List<String> getSavedPlaces() {
        return savedPlaces;
    }
}