package net.thomi100.VAAPI;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/*
 * Created by ThomasChr on 17.01.2018.
 */
public class Config {

    /* DO NOT CHANGE THIS FILE UNLESS YOU WANT TO EDIT SOMETHING ON ALL PLATFORMS
    *  CHANGES WILL BE AFFECTED ON ALL DEVICES
    *  TO EDIT SOMETHING, PLEASE DON'T DO IT IN THE API
    *  ~19.01.2018
    * */

    public static HashMap<String, String> getAll() throws IOException {
        HashMap<String, String> output = new HashMap<>();
        URL url = new URL("http://www.thomi100.net/dev/config.txt");
        Scanner s = new Scanner(url.openStream());
        while(s.hasNextLine()) {
            String line = s.nextLine();
            if(line.contains("=") && line.length() > 0 && !line.startsWith("#")) {
                String[] splitted = line.split("=");
                output.put(splitted[0], splitted[1]);
            }
        }
        s.close();
        return output;
    }

    public static String get(String key) throws IOException {
        HashMap<String, String> output = new HashMap<>();
        URL url = new URL("http://www.thomi100.net/dev/config.txt");
        Scanner s = new Scanner(url.openStream());
        while(s.hasNextLine()) {
            String line = s.nextLine();
            if(line.contains("=") && line.length() > 0 && !line.startsWith("#")) {
                String[] splitted = line.split("=");
                if(splitted[0].equalsIgnoreCase(key)) {
                    s.close();
                    return splitted[1];
                }
            }
        }
        s.close();
        return null;
    }

}
