package com.kyefer.manager.util;

import com.kyefer.manager.model.Game;
import com.kyefer.manager.model.SteamProfile;
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
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A util class to determine all the games that a game has
 * <p>
 * Created by Eddie on 9/30/2015.
 */
public class SteamUtil {

    private static final Logger log = Logger.getLogger(SteamUtil.class.getName());

    private static final String STEAM_KEY = "83EDD98AD612EAD6AA92695C2A548553";

    private static final int MAX_RETRIES = 5;

    /**
     * Polls Steam's API to determine the games a {@link SteamProfile Profile} has and then scrapes steamspy.com to
     * determine their genre by looking at user tags
     *
     * @param profile the profile to find the games for
     */
    public static void loadGames(SteamProfile profile) {

        String gamePollURL = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=" + STEAM_KEY + "&steamid=" + profile.getSteamID() + "&include_appinfo=1&format=json&include_played_free_games=1";
        String result = "";
        try {
            URL stream = new URL(gamePollURL);
            BufferedReader in = new BufferedReader((new InputStreamReader(stream.openStream())));

            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Could not connect to the Internet");
            // throw new IOException("Could not connect to the Internet");
        }

        try {

            JSONObject overall = new JSONObject(result);
            JSONArray gameArray = overall.getJSONObject("response").getJSONArray("games");
            for (int i = 0; i < gameArray.length(); i++) {
                JSONObject gameObject = gameArray.getJSONObject(i);
                int appid = gameObject.getInt("appid");
                String gameName = gameObject.getString("name");


                int tries = 0;
                boolean success = false;
                do {

                    try {
                        long t = System.currentTimeMillis();
                        Document doc = Jsoup.connect("http://steamspy.com/app/" + appid).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").get();
                        Element appData = doc.getElementsByClass("p-r-30").first();
                        Elements appTags = appData.getElementsByAttributeValueStarting("href", "/tag/");
                        for (Element appTag : appTags) {
                            String genreString = appTag.text();
                            Game currentGame = profile.getGames().stream().filter(game -> game.getName().equals(gameName)).findFirst().orElseGet(() -> {
                                Game newGame = new Game(gameName);
                                profile.addGame(newGame);
                                return newGame;
                            });
                            currentGame.addGenre(genreString);
                        }
                        success = true;
                    } catch (IOException e) {
                        log.log(Level.WARNING, "Error loading app: " + gameName + ((++tries < MAX_RETRIES) ? ". Retrying" : ""));
                    }
                } while (tries < MAX_RETRIES && !success);
            }

        } catch (JSONException e) {
            log.log(Level.SEVERE, "Broken reply from Steam");
            /*
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Steam Error");
            alert.setContentText("Error loading data -Steam has given a broken reply");

            alert.showAndWait();
            */
        }
    }


    public static String getUsername(String steamid) {
        //TODO implement this method
        return steamid;
    }
}
