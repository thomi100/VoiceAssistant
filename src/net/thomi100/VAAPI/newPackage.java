package net.thomi100.VAAPI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
 * Created by thomi100 on 17.01.2018.
 */
class newPackage {

    /* DO NOT CHANGE THIS FILE UNLESS YOU WANT TO EDIT SOMETHING ON ALL PLATFORMS
    *  CHANGES WILL BE AFFECTED ON ALL DEVICES
    *  TO EDIT SOMETHING, PLEASE DON'T DO IT IN THE API
    *  ~19.01.2018
    * */

    // TODO: Textdatei verschlüsseln
    // TODO: letzte getFile() überall abspeichern

    /* Eingabe, Ausgabe */
    static HashMap<String, String> getFile() throws IOException {
        HashMap<String, String> output = new HashMap<>();
        URL url = new URL("http://www.thomi100.net/dev/VoiceAssistantFiles.txt");
        Scanner s = new Scanner(url.openStream());
        while(s.hasNextLine()) {
            String line = s.nextLine();
            if(line.length() > 0 && !line.startsWith("#")) {
                if(line.contains(";")) {
                    String[] inout = line.split(";");
                    String in = inout[0];
                    String out = inout[1];
                    if(inout[0].startsWith("*")) {
                        String device = in.split(" ")[0].replace("*", "");
                        if(MyChanges.DEBUG) System.out.println("[DeviceCheck] " + device + " ? " + device.equalsIgnoreCase(MyChanges.DEVICE_TYPE));
                        if(device.equalsIgnoreCase(MyChanges.DEVICE_TYPE)) {
                            output.put(in.replace("*" + device, ""), out);
                        } else {
                            continue;
                        }
                    } else output.put(in, out);
                }
            }
        }
        s.close();
        return output;
    }

    /* Eingabe, Ausgabe */
    static ArrayList<String> getUnknowns() throws IOException {
        ArrayList<String> output = new ArrayList<>();
        URL url = new URL("http://www.thomi100.net/dev/unknowns.txt");
        Scanner s = new Scanner(url.openStream());
        while(s.hasNextLine()) {
            String line = s.nextLine();
            if(line.length() > 0 && !line.startsWith("#")) {
                output.add(line);
            }
        }
        s.close();
        return output;
    }

    public static String firstUpper(String customText){
        String char0 = customText.substring(0, 1);
        String elses = customText.substring(1, customText.length());
        return char0.toUpperCase() + elses.toLowerCase();
    }

}