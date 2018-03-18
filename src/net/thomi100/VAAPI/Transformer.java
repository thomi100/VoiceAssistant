package net.thomi100.VAAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/*
 * Created by ThomasChr on 17.01.2018.
 */
public class Transformer {

    /* DO NOT CHANGE THIS FILE UNLESS YOU WANT TO EDIT SOMETHING ON ALL PLATFORMS
    *  CHANGES WILL BE AFFECTED ON ALL DEVICES
    *  TO EDIT SOMETHING, PLEASE DON'T DO IT IN THE API
    *  ~19.01.2018
    * */

    private HashMap<String, String> hashMap;
    private ArrayList<String> unknowns;

    public Transformer(HashMap<String, String> hashMap, ArrayList<String> unknowns) {
        this.hashMap = hashMap;
        this.unknowns = unknowns;
    }

    private String lastProcessed = null;
    private int similarity = -1;
    private String entry = null;
    private String output = null;
    private String transformedOutput = null;
    public String startsWithText = null;

    /* returns the last (unformatted) input, the user has given, before processing */
    public String getLastProcessed() { return lastProcessed; }

    /* returns the percentage of the similarity, after processing */
    public int getSimilarity() { return similarity; }

    /* returns the entry = original key from HashMap, after processing */
    public String getEntry() { return entry; }

    /* returns the unformatted output, after processing */
    public String getOutput() { return output; }

    /* returns the final answer, after transforming*/
    public String getFinal() { return transformedOutput; }

    public String process(String input) {
        lastProcessed = input;
        input = input.toLowerCase().trim();

        String highestChance = null;
        Integer pP = 0; // possiblity Percent = percentage of the similarity

        for(String abfrage : hashMap.keySet()) {
            String abfrageOrgin = abfrage;
            abfrage = abfrage.toLowerCase().trim();

            if(abfrage.startsWith(">")) {
                String ab = abfrage.substring(1);
                if(input.toLowerCase().startsWith(ab.toLowerCase())) {
                    highestChance = abfrageOrgin;
                    pP = 90;
                    startsWithText = lastProcessed.trim().substring(ab.length() + 1);
                    break;
                }
            } else
            if(!abfrage.contains("&")) {

                if(input.equals(abfrage)) {
                    highestChance = abfrageOrgin;
                    pP = 100;
                    break;
                } else if(input.contains(abfrage)) {
                    int similarity = calculateSimilarity(abfrage, input);
                    if(similarity > pP) {
                        highestChance = abfrageOrgin;
                        pP = similarity;
                    }
                }

            } else {
                boolean containsAll = true;
                for(String neededWord : abfrage.split("&")) {
                    if(!input.contains(neededWord)) {
                        containsAll = false;
                    }
                }
                if(containsAll) {
                    int similarity = calculateSimilarity(abfrage, input);
                    if(similarity > pP) {
                        highestChance = abfrageOrgin;
                        pP = similarity;
                    }
                }
            }
        }

        if(highestChance != null && pP > VoiceAssistantAPI.NEEDED_SIMILARITY) {
            entry = highestChance;
            similarity = pP;
            output = hashMap.get(highestChance);
            return output;
        }
        return null;
    }

    public String transform() {
        String answer = output;
        if(answer != null) {
            while(answer.contains("{") && answer.contains("}")) {
                int indexStart = answer.indexOf("{");
                int indexEnd = answer.indexOf("}");
                String placeholder = answer.substring(indexStart, indexEnd + 1);
                String replacedBy = MyChanges.getPlaceholder(placeholder.replace("{", "").replace("}", "").replace(" ", "_").trim().toUpperCase(), this);
                answer = answer.replace(placeholder, (replacedBy != null) ? replacedBy : "x");
                if(replacedBy == null) MistakeLog.logPlaceholder(getLastProcessed() + " [" + placeholder + "]");
            }
            if(answer.contains("<") && startsWithText != null) {
                answer = answer.replace("<", startsWithText);
            }
            if(answer.contains("*")) {
                Actions.performAction(answer, this);
                answer = answer.split("\\*")[0];
            }
            if(answer.contains("&")) {
                String[] splitted = answer.split("&");
                transformedOutput = splitted[new Random().nextInt(splitted.length)];
                return transformedOutput;
            } else {
                transformedOutput = answer.trim();
                return transformedOutput;
            }
        } else {
            MistakeLog.logUnknown(getLastProcessed());
            return unknowns.get(new Random().nextInt(unknowns.size()));
        }
    }

    public void close() {
        similarity = -1;
        entry = null;
        output = null;
        transformedOutput = null;
        lastProcessed = null;
    }

    public static int calculateSimilarity(String first, String second) {
        if(first.equalsIgnoreCase(second)) return 100;
        Double similarity = stringSimilarityScore(bigram(first), bigram(second)) * 100;
        return similarity.intValue();
    }

    /** Credit: https://coderwall.com/p/ge3o9q/generate-a-string-similarity-score-with-java */
    private static double stringSimilarityScore(List<char[]> bigram1, List<char[]> bigram2) {
        List<char[]> copy = new ArrayList<>(bigram2);
        int matches = 0;
        for (int i = bigram1.size(); --i >= 0;) {
            char[] bigram = bigram1.get(i);
            for (int j = copy.size(); --j >= 0;) {
                char[] toMatch = copy.get(j);
                if (bigram[0] == toMatch[0] && bigram[1] == toMatch[1]) {
                    copy.remove(j);
                    matches += 2;
                    break;
                }
            }
        }
        return (double) matches / (bigram1.size() + bigram2.size());
    }

    private static List<char[]> bigram(String input) {
        ArrayList<char[]> bigram = new ArrayList<>();
        for (int i = 0; i < input.length() - 1; i++) {
            char[] chars = new char[2];
            chars[0] = input.charAt(i);
            chars[1] = input.charAt(i+1);
            bigram.add(chars);
        }
        return bigram;
    }

    public ArrayList<String> getUnknowns() {
        return unknowns;
    }

}
