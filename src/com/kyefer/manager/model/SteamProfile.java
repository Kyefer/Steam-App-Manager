package com.kyefer.manager.model;

import java.util.*;

/**
 * Created by Eddie on 10/7/2015.
 */
public class SteamProfile {

    private String steamID;
    private List<Game> games;
    private String username;
    private Map<String, Genre> genreMap;

    /**
     * Creates an object that represents a Steam profile
     *
     * @param steamID  the 17 digit ID that correlates to the profile
     * @param username the current username for the account - this can be anything and is not checked against steam's database
     */
    public SteamProfile(String steamID, String username) {
        if (!steamID.matches("\\d{17}"))
            throw new IllegalArgumentException("ID must be a 17 digit number");

        this.steamID = steamID;
        this.username = username;

        this.games = new ArrayList<>();
        this.genreMap = new HashMap<>();
    }

    /**
     * Creates an object that represents a Steam profile
     *
     * @param steamID the 17 digit ID that correlates to the profile
     */
    public SteamProfile(String steamID) {
        this(steamID, steamID);
    }

    /**
     * Gets the the Steam ID that correlates to the profile
     *
     * @return the Steam ID
     */
    public String getSteamID() {
        return steamID;
    }

    /**
     * Gets all the games that the Steam profile has
     *
     * @return all games
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * Adds a {@link Game} to the profile
     *
     * @param newGame the {@link Game} to add
     */
    public void addGame(Game newGame) {
        games.add(newGame);
        generateGenres();
    }

    /**
     * Gets the username correlated to the profile
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the profile
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public void generateGenres() {
        for (Game game : games) {
            for (String genreName : game.getGenresNames()) {
                genreMap.putIfAbsent(genreName, new Genre(genreName));
                genreMap.get(genreName).addGame(game);
            }
        }
    }

    public List<Genre> getGenres() {
        return new ArrayList<>(genreMap.values());
    }
}
