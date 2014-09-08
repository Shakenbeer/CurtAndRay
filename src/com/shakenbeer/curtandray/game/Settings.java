package com.shakenbeer.curtandray.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.badlogic.androidgames.framework.FileIO;

public class Settings {
    public static final int MAX_LEVEL_NUM = 28;
    
    public static boolean soundEnabled = true;
    public static boolean hardMode = false;
    public static int currentLevel = 1;
    
    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    files.readFile(".curdandray")));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            currentLevel = Integer.parseInt(in.readLine());
            hardMode = Boolean.parseBoolean(in.readLine());
        } catch (IOException e) {
            // :( It's ok we have defaults
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }
    
    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    files.writeFile(".mrnom")));
            out.write(Boolean.toString(soundEnabled));
            out.write(Integer.toString(currentLevel));
            out.write(Boolean.toString(hardMode));
        } catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
    }
}
