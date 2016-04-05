package com.kyefer.manager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to wrap a genre of game (or tag) to all the {@link Game Games} that share the same genre
 *
 * Created by Eddie on 12/15/2015.
 */
public class Genre implements Comparable, Serializable{

    private String name;
    private List<Game> games;

    /**
     * Constructs a Genre object with a given name
     * A Genre contains all the Game objects that share the same genre name
     *
     * @param name the name of the genre
     */
    public Genre(String name){
        this.name = name;
        games = new ArrayList<>();
    }

    /**
     * Returns the name of the genre
     * @return the name of the genre
     */
    public String getName() {
        return name;
    }

    /**
     * Returns all the games that share the genre name
     * @return all the games that share the genre name
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * Adds a game to the List of all the games that share the same genre
     * @param game
     */
    public void addGame(Game game) {
        if (!games.contains(game))
            games.add(game);
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Genre)o).getName());
    }
}
