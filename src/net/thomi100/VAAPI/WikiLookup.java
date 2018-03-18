package net.thomi100.VAAPI;

import java.net.URL;
import java.util.Scanner;

public class WikiLookup {

    public static String getDescription(String wikiFile) {
        wikiFile = wikiFile.replace(" ", "_");
        try {
            URL url = new URL("https://de.wikipedia.org/wiki/" + wikiFile);
            Scanner scanner = new Scanner(url.openStream(), "UTF-8");
            while(scanner.hasNextLine()) {
                String ln = scanner.nextLine();

                if(ln.equalsIgnoreCase("<div class=\"plainlinks\" style=\"padding:0 0 1em 0;\"><b>Diese Seite existiert nicht</b>")) {
                    return null;
                }

                if(ln.startsWith("<p>") && !ln.startsWith("<p><a ")) {
                    String appended = "";
                    int ebene = 0;
                    char lastChar = ' ';
                    boolean specialCode = false;
                    for(int i = 0; i < ln.length(); i++) {
                        char c = ln.charAt(i);
                        if(c == '<' || c == '(' || c == '[') {
                            ebene++;
                        }
                        if(lastChar == '&' && c == '#') {
                            specialCode = true;
                        }
                        if(ebene <= 0 && !specialCode) appended += c;
                        if(c == ';' && specialCode) {
                            specialCode = false;
                        }
                        if(c == '>' || c == ')' || c == ']') {
                            ebene--;
                        }
                        lastChar = c;
                    }
                    return appended.trim().replace(" ,", ",").replace(" .", ".").trim();
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}