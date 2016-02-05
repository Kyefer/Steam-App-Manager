package com.kyefer.manager.model;

import java.util.*;

/**
 * Created by Eddie on 9/30/2015.
 */
public class Game {
    private String name;
    private List<String> genres;

    /**
     * Constructs a Game object that organizes all the genres of the game
     *
     * @param name the name of the game
     */
    public Game(String name) {
        this.name = name;
        this.genres = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }

    public List<String> getGenresNames() {
        Collections.sort(genres);
        return genres;
    }

    public String toString(){
        return String.format("Game[name=%s]", name);//, genres=%s]", name, Arrays.toString(genres.toArray()));
    }
}
