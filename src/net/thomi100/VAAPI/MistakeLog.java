package net.thomi100.VAAPI;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
 * Created by thomi100 on 18.01.2018.
 */
public class MistakeLog {

    /* DO NOT CHANGE THIS FILE UNLESS YOU WANT TO EDIT SOMETHING ON ALL PLATFORMS
    *  CHANGES WILL BE AFFECTED ON ALL DEVICES
    *  TO EDIT SOMETHING, PLEASE DON'T DO IT IN THE API
    *  ~19.01.2018
    * */

    private static ArrayList<String> mistakes = new ArrayList<>();

    public static void logCustom(String prefix, String input) {
        mistakes.add("[" + prefix.toUpperCase() + "] " + input);
    }
    public static void logWiki(String input) {
        mistakes.add("[WIKI] " + input);
    }
    public static void logUnknown(String input) {
        mistakes.add("[UNKNOWN] " + input);
    }
    public static void logPlaceholder(String input) {
        mistakes.add("[PLACEHOLDER] " + input);
    }
    public static void logAction(String input) {
        mistakes.add("[ACTION] " + input);
    }

    public static File printLog() {
        if(mistakes.isEmpty()) return null;
        SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
        String time = time_formatter.format(System.currentTimeMillis());
        String lastUpdated = "unknown";
        try {
            lastUpdated = Config.get("last_updated");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String apiVersion = VoiceAssistantAPI.API_VERSION;
        File file = new File(System.getProperty("user.home") + "/Desktop/mistakes_" + time + ".txt");
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("---------[LOG OF ALL NOT UNDERSTOOD THINGS]--------");
            writer.print("Device identifier: " + MyChanges.DEVICE_TYPE + "\r\n");
            writer.print("API version: " + apiVersion + "\r\n");
            writer.print("Date file printed: " + time + "\r\n");
            writer.print("Strings last updated: " + lastUpdated + "\r\n");
            writer.print("------[PLEASE REPORT MISTAKES TO THE DEVELOPER]-----");
            for(String mistake : mistakes) writer.print("\r\n" + mistake);
            mistakes.clear();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

}