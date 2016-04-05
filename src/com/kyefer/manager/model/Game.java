package com.kyefer.manager.model;

import java.io.Serializable;
import java.util.*;

/**
 * A class to wrap a Steam game name with the genres (or tags) that are associated with that game
 *
 * Created by Eddie on 9/30/2015.
 */
public class Game implements Serializable {
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

    /**
     * Gets the name of the Game
     * @return the name of the Game
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a genre to the game
     * @param genre the genre to add to the game
     */
    public void addGenre(String genre) {
        genres.add(genre);
    }

    /**
     * Gets all the name of the genres that the game is a part of. Not to be confused with the Genre class
     * @return the names of all the genres
     */
    public List<String> getGenresNames() {
        Collections.sort(genres);
        return genres;
    }

    @Override
    public String toString(){
        return String.format("Game[name=%s], genres=%s]", name, Arrays.toString(genres.toArray()));
    }
}
