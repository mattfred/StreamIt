package com.mattfred.streamit.utils;

import java.util.List;

/**
 * Created by Matthew on 11/5/2015.
 */
public class Globals {

    private static List<Object> results;
    private static String bitmapURL;
    private static String title;
    private static String imdb_id;

    public static List<Object> getResults() {
        return results;
    }

    public static void setResults(List<Object> results) {
        Globals.results = results;
    }

    public static String getBitmapURL() {
        return bitmapURL;
    }

    public static void setBitmapURL(String bitmapURL) {
        Globals.bitmapURL = bitmapURL;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        Globals.title = title;
    }

    public static String getImdb_id() {
        return imdb_id;
    }

    public static void setImdb_id(String imdb_id) {
        Globals.imdb_id = imdb_id;
    }
}
