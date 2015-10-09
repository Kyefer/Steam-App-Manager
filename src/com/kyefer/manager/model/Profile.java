package com.kyefer.manager.model;

import com.kyefer.manager.util.SteamUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eddie on 10/7/2015.
 */
public class Profile {

    private String steamid;
    private ObservableList<Game> games;
    private boolean hasRetrievedFromSteam;

    public Profile() {
        hasRetrievedFromSteam = false;
        games = FXCollections.observableArrayList();
    }

    public void setSteamID(String steamid) {
        if (!steamid.matches("[0-9]{17}"))
            throw new IllegalArgumentException("ID must be a 17 digit number");
        this.steamid = steamid;
    }

    public String getSteamID() {
        return steamid;
    }

    public ObservableList<Game> getGames() {
        return games;
    }

    public void addGame(Game newGame) {
        games.add(newGame);
    }

    public void addAllGamesFromSteam() {
        if (!hasRetrievedFromSteam)
            try {
                SteamUtil.getGamesByID(steamid).stream().forEach(this::addGame);
                hasRetrievedFromSteam = true;
            } catch (IOException e) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Connection Error");
                alert.setHeaderText(null);
                alert.setContentText("Could not connect to the internet");

                alert.showAndWait();
            }
    }
}
