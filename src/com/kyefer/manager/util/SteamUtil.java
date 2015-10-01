package com.kyefer.manager.util;

import com.kyefer.manager.model.Game;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Eddie on 9/30/2015.
 */
public class SteamUtil {

    private static final String STEAM_KEY = "83EDD98AD612EAD6AA92695C2A548553";


    public static List<Game> getGamesByID(String id64) throws IOException{
        if (!id64.toUpperCase().matches("[0-9]{17}"))
            throw new IllegalArgumentException("ID must be a 17 digit number");

        String gamePollURL = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=" + STEAM_KEY + "&steamid=" + id64 + "&include_appinfo=1&format=json&include_played_free_games=1";
        String result = "";
        List<Game> games = new ArrayList<>();
        try {
            URL stream = new URL(gamePollURL);
            BufferedReader in = new BufferedReader((new InputStreamReader(stream.openStream())));

            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException e) {

        } catch (IOException e) {
            throw new IOException("Could not connect to the Internet");
        }

        try {

            JSONObject overall = new JSONObject(result);
            JSONArray gameArray = overall.getJSONObject("response").getJSONArray("games");

            for (int i = 0; i < gameArray.length(); i++) {
                JSONObject gameObject = gameArray.getJSONObject(i);
                int appid = gameObject.getInt("appid");
                String gameName = gameObject.getString("name");
                try {
                    long t = System.currentTimeMillis();
                    Document doc = Jsoup.connect("http://steamspy.com/app/" + appid).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();
                    Element appData = doc.getElementsByClass("p-r-30").first();
                    Elements appTags = appData.getElementsByAttributeValueStarting("href", "/tag/");
                    for (Element appTag : appTags) {
                        String genreString = appTag.text();
                        Game currentGame = null;
                        try {
                            currentGame = games.stream().filter(game -> game.getName().equals(gameName)).findFirst().get();
                        } catch (NoSuchElementException e){
                            currentGame = new Game(gameName);
                            games.add(currentGame);
                        } finally {
                            currentGame.addGenre(genreString);
                        }
                    }

                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("App Error");
                    alert.setHeaderText("Error getting data");
                    alert.setContentText("Could not load data for app with id " + appid);

                    alert.showAndWait();
                }
            }

        } catch (JSONException ignored) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Steam Error");
            alert.setHeaderText("Error loading Steam Data");
            alert.setContentText("Steam has given a broken reply");

            alert.showAndWait();

        }

        return games;
    }
}
