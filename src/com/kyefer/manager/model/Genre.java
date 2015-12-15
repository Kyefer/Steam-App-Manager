package com.kyefer.manager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eddie on 12/15/2015.
 */
public class Genre implements Comparable{

    private String name;
    private List<Game> games;

    public Genre(String name){
        this.name = name;
        games = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        if (!games.contains(game))
            games.add(game);
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Genre)o).getName());
    }
}
