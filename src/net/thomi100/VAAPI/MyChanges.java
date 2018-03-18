package net.thomi100.VAAPI;

import net.thomi100.Desktop.DesktopMain;

import javax.print.URIException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/*
 * Created by thomi100 on 20.01.2018.
 */
public class MyChanges {

    /* Here, you have to edit all Actions which come from the Actions.class file or other files.
    *  Handle it and do, what you want. Don't copy this file every time.
    *  ~19.01.2018
    * */

    public static boolean DEBUG = false;
    public static final String DEVICE_TYPE = "DESKTOP";

    public static boolean runAction(String code, Transformer transformer) {
        if(code.equalsIgnoreCase("RL_VAF")) {

            try {
                DesktopMain.transformer = new Transformer(VoiceAssistantAPI.getVoicemap(), VoiceAssistantAPI.getUnknownmap());
            } catch (IOException e) {
                e.printStackTrace();
                DesktopMain.showAlert("Das Programm konnte nicht reloaded werden.");
                return false;
            }

            return true;
        }
        if(code.equalsIgnoreCase("QUIT")) {
            VoiceAssistantAPI.quit();
            return true;
        }
        if(code.equalsIgnoreCase("NOTE")) {
            String note = transformer.startsWithText;
            if(note != null) {
                PersonalData.putInList("notes", note);
                return true;
            }
        }
        if(code.equalsIgnoreCase("NAME")) {
            String name = transformer.startsWithText;
            if(name != null) {
                PersonalData.addOne("name", name);
                return true;
            }
        }
        return false;
    }

    public static String getPlaceholder(String placeholder, Transformer transformer) {
        if(placeholder.equalsIgnoreCase("DEVICE")) {
            return newPackage.firstUpper(DEVICE_TYPE);
        }
        if(placeholder.equalsIgnoreCase("NOTES")) {
            ArrayList<String> notes = PersonalData.getList("notes");
            String note = "";
            for(String n : notes) {
                note = note + "&&&" + n;
            }
            if(note.startsWith("&&&")) note = note.substring(3);
            if(!note.contains(",")) note = note.replace("&&&", ", ");
            else if(!note.contains(";")) note = note.replace("&&&", "; ");
            else note = note.replace("&&&", " und ");

            return note;
        }
        if(placeholder.equalsIgnoreCase("WIKI_LOOKUP")) {
            String name = transformer.startsWithText;
            if(name != null) {
                String description = WikiLookup.getDescription(name);
                if(description != null) {
                    return description;
                }
            }
            MistakeLog.logWiki(transformer.getLastProcessed());
            return transformer.getUnknowns().get(new Random().nextInt(transformer.getUnknowns().size()));
        }
        if(placeholder.equalsIgnoreCase("NOTE_REMOVE")) {
            String note = transformer.startsWithText;
            if(note != null) {
                if(PersonalData.removeFromList("notes", note)) {
                    return "Ich habe die Notiz " + note + " entfernt.";
                }
            }
            return "Ich habe die Notiz " + ((note != null) ? note + " " : "") + "nicht gefunden.";
        }
        return null;
    }

    public static void savePersonalData(HashMap<String, String> map) {
        try {
            String path = DesktopMain.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            new File(path).mkdirs();
            File file = new File(path + "personalData.txt");
            if(file.exists()) file.delete();
            file.createNewFile();
            PrintWriter pw = new PrintWriter(file);
            for(String key : map.keySet()) {
                String value = map.get(key);
                pw.write(key + "§" + value);
            }
            pw.close();
        } catch (URISyntaxException|IOException e) {
            e.printStackTrace();
            DesktopMain.showAlert(e.getMessage());
            SoundUtils.sendDoubleBeep();
        }
    }

    public static HashMap<String, String> loadPersonalData() {
        HashMap<String, String> map = new HashMap<>();
        try {
            String path = DesktopMain.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            new File(path).mkdirs();
            File file = new File(path + "personalData.txt");
            if(!file.exists()) {
                file.createNewFile();
                return new HashMap<>();
            }
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String ln = scanner.nextLine();
                if(ln.length() <= 0 || !ln.contains("§")) continue;

                String[] things = ln.split("§");
                String key = things[0];
                String value = things[1];
                map.put(key, value);
            }
            scanner.close();
            return map;
        } catch (URISyntaxException|IOException e) {
            e.printStackTrace();
            DesktopMain.showAlert(e.getMessage());
            SoundUtils.sendDoubleBeep();
        }
        return new HashMap<>();
    }

}
