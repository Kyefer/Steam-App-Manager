package com.kyefer.manager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Eddie on 9/30/2015.
 */
public class Game {
    private final StringProperty nameProperty;
    private List<String> genres;

    public Game(String name){
        nameProperty = new SimpleStringProperty(name);
        genres = new ArrayList<>();
    }

    public String getName(){
        return nameProperty.get();
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public void addGenre(String genre){
        genres.add(genre);
    }

    public List<String> getGenres(){
        Collections.sort(genres);
        return genres;
    }
}
