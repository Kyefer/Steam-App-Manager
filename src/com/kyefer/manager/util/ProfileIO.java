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
 * A util class for saving and loading saved profiles
 * <p>
 * Created by Eddie on 2/5/2016.
 */
public class ProfileIO {

    private static final Logger log = Logger.getLogger(ProfileIO.class.getName());

    private static final String GAME_GENRE_DELIMINATOR = "=";
    private static final String GENRE_DELIMINATOR = "~";

    /**
     * Opens a profile file, reads and parses the content into a {@link SteamProfile} and returns it
     *
     * @param file the file to open
     * @return a {@link SteamProfile} wrapping the content of the file
     * @throws IOException if any error occurs while reading the file
     */
    public static SteamProfile openProfile(File file) throws IOException {
        SteamProfile profile = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fis);
            profile = (SteamProfile) objectInputStream.readObject();
            objectInputStream.close();
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return profile;
    }

    public static void saveProfile(File file, SteamProfile profile) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(profile);
            oos.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

}
