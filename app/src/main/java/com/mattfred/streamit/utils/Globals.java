package com.mattfred.streamit.utils;

import android.content.Intent;

import com.mattfred.streamit.model.Season;
import com.mattfred.streamit.model.SubscriptionSource;

import java.util.List;

/**
 * Globals static object class
 */
public class Globals {

    private static List<Object> results;
    private static String bitmapURL;
    private static String title;
    private static String imdb_id;
    private static int id;
    private static boolean isMovie;
    private static String source;
    private static int season;
    private static List<Season> seasons;
    private static List<SubscriptionSource> sources;

    public static Intent getShareIntent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, "StreamIt");

        sendIntent.putExtra(Intent.EXTRA_TEXT, Constants.PLAY_LINK);
        sendIntent.setType("text/plain");
        return sendIntent;
    }

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

    public static List<SubscriptionSource> getSources() {
        return sources;
    }

    public static void setSources(List<SubscriptionSource> sources) {
        Globals.sources = sources;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Globals.id = id;
    }

    public static boolean isMovie() {
        return isMovie;
    }

    public static void setIsMovie(boolean isMovie) {
        Globals.isMovie = isMovie;
    }

    public static String getSource() {
        return source;
    }

    public static void setSource(String source) {
        Globals.source = source;
    }

    public static int getSeason() {
        return season;
    }

    public static void setSeason(int season) {
        Globals.season = season;
    }

    public static List<Season> getSeasons() {
        return seasons;
    }

    public static void setSeasons(List<Season> seasons) {
        Globals.seasons = seasons;
    }
}
