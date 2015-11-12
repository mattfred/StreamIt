package com.mattfred.streamit.utils;

import java.util.List;

/**
 * Created by Matthew on 11/5/2015.
 */
public class Globals {

    private static List<Object> results;

    public static List<Object> getResults() {
        return results;
    }

    public static void setResults(List<Object> results) {
        Globals.results = results;
    }
}
