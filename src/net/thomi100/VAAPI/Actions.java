package net.thomi100.VAAPI;

import java.util.ArrayList;

/*
 * Created by ThomasChr on 17.01.2018.
 */
public class Actions {

    /* DO NOT CHANGE THIS FILE UNLESS YOU WANT TO EDIT SOMETHING ON ALL PLATFORMS
    *  CHANGES WILL BE AFFECTED ON ALL DEVICES
    *  TO EDIT SOMETHING, PLEASE DON'T DO IT IN THE API
    *  ~19.01.2018
    * */

    public static void performAction(String wholeOutput, Transformer t) {
        if(wholeOutput.contains("*")) {
            ArrayList<String> aufgaben = new ArrayList<>();
            String aktuelleAufgabe = "";
            for(int i = 0; i < wholeOutput.length(); i++) {
                char c = wholeOutput.charAt(i);
                if(c == '*') {
                    if(aktuelleAufgabe.length() == 0) {
                        aktuelleAufgabe = "*" + aktuelleAufgabe;
                    } else {
                        if(aktuelleAufgabe.startsWith("*")) aufgaben.add(aktuelleAufgabe.trim());
                        aktuelleAufgabe = "*";
                    }
                } else {
                    aktuelleAufgabe = aktuelleAufgabe + c;
                }
            }
            if(aktuelleAufgabe.length() > 0) aufgaben.add(aktuelleAufgabe.trim());
            if(aufgaben.size() > 0) {
                for(String aufgabe : aufgaben) {
                    aufgabe = aufgabe.trim().toUpperCase().replace("*", "").replace(" ", "");
                    boolean success = MyChanges.runAction(aufgabe, t);
                    if(!success) MistakeLog.logAction(t.getLastProcessed() + ", " + wholeOutput + " [" + aufgabe + "]");
                }
            }
        }
    }

}
