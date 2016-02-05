package com.kyefer.manager.util;

import com.kyefer.manager.model.Game;
import com.kyefer.manager.model.SteamProfile;
import sun.rmi.runtime.Log;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eddie on 2/5/2016.
 */
public class ProfileIO {

    private static final Logger log = Logger.getLogger(ProfileIO.class.getName());

    private static final String GAME_GENRE_DELIMINATOR = "=";
    private static final String GENRE_DELIMINATOR = "~";

    public static SteamProfile openProfile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            SteamProfile profile = new SteamProfile(line);
            while ((line = br.readLine()) != null) {
                String[] partition = line.split(GAME_GENRE_DELIMINATOR);
                Game newGame = new Game(partition[0]);
                for (String genre : partition[1].split(GENRE_DELIMINATOR))
                    newGame.addGenre(genre.trim());
                profile.addGame(newGame);
            }
            return profile;
        }
    }

    public static void saveProfile(File file, SteamProfile profile) {
        try {
            file.delete();
            file.createNewFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(profile.getSteamID());
                bw.newLine();
                for (Game game : profile.getGames()) {
                    bw.write(game.getName() + GAME_GENRE_DELIMINATOR);
                    bw.write(String.join(GENRE_DELIMINATOR, game.getGenresNames()));
                    bw.newLine();
                }
            } catch (IOException e) {
                log.log(Level.WARNING, "Error reading from " + file.getPath());
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, file.getPath() + " could not be created/found");
        }


    }

}
