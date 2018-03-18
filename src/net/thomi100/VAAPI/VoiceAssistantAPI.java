package net.thomi100.VAAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Created by thomi100 on 17.01.2018.
 */
public class VoiceAssistantAPI {

    /* DO NOT CHANGE THIS FILE UNLESS YOU WANT TO EDIT SOMETHING ON ALL PLATFORMS
    *  CHANGES WILL BE AFFECTED ON ALL DEVICES
    *  TO EDIT SOMETHING, PLEASE DON'T DO IT IN THE API
    *  ~19.01.2018
    * */

    public static Transformer getTransformer(HashMap<String, String> voicemap, ArrayList<String> unknownmap) {
        return new Transformer(voicemap, unknownmap);
    }

    public static ArrayList<String> getUnknownmap() throws IOException {
        return newPackage.getUnknowns();
    }

    public static HashMap<String, String> getVoicemap() throws IOException {
        return newPackage.getFile();
    }

    public static String getConfigValue(String key) throws IOException {
        return Config.get(key);
    }

    public static HashMap<String, String> getConfigAll() throws IOException {
        return Config.getAll();
    }

    public static void start() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                MistakeLog.printLog();
                System.out.println("[VAAPI] Program quit.");
            }
        });
        PersonalData.map = MyChanges.loadPersonalData();
    }

    public static void quit() {
        MistakeLog.printLog();
        MyChanges.savePersonalData(PersonalData.map);
        System.out.println("[VAAPI] Program ended.");
        System.exit(0);
    }

    public static String API_VERSION = "1.3.0";
    public static String ASSISTANT_NAME = "Lia";
    public static int NEEDED_SIMILARITY = 25;

}
