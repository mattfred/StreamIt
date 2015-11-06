package com.mattfred.streamit.utils;

import com.mattfred.streamit.model.Results;

/**
 * Created by Matthew on 11/5/2015.
 */
public class Globals {

    private static Results results;

    public static Results getResults() {
        return results;
    }

    public static void setResults(Results results) {
        Globals.results = results;
    }
}
