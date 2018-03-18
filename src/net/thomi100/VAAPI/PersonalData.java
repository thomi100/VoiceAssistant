package net.thomi100.VAAPI;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Created by thomi100 on 20.01.2018.
 */
public class PersonalData {

    public static HashMap<String, String> map = new HashMap<>();

    public static boolean removeFromList(String key, String obj) {
        ArrayList<String> list = getList(key);

        if(list.contains(obj)) {
            list.remove(obj);
            map.remove(key);
            String s = "";
            for(String t : list) {
                s = s + ";" + t;
            }
            if(s.startsWith(";")) s = s.substring(1);
            map.put(key, s);
            return true;
        }

        return false;

    }

    public static ArrayList<String> getList(String key) {
        ArrayList<String> list = new ArrayList<>();

        if(map.containsKey(key)) {
            String values = map.get(key);
            if(values.contains(";")) {
                for(String s : values.split(";")) list.add(s);
            } else {
                list.add(values);
            }
        }

        return list;
    }

    public static void putInList(String key, String value) {
        if(map.containsKey(key)) {
            String v = map.get(key);
            v = v + ";" + value;
            map.remove(key);
            map.put(key, v);
        } else {
            map.put(key, value);
        }
    }

    public static void addOne(String key, String value) {
        if(!map.containsKey(key)) {
            map.put(key, value);
        } else {
            map.remove(key);
            map.put(key, value);
        }
    }

}