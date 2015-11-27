package com.mattfred.streamit.model;

import java.io.Serializable;

/**
 * Movie Object
 */
public class Movie implements Serializable {

    private int id;
    private String title;
    private int release_year;
    private String themoviedb;
    private String[] alternate_titles;
    private String imdb;
    private boolean pre_order;
    private String release_date;
    private String rating;
    private String rottentomatoes;
    private String metacritic;
    private String common_sense_media;
    private String poster_120x171;
    private String poster_240x342;
    private String poster_400x570;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getThemoviedb() {
        return themoviedb;
    }

    public void setThemoviedb(String themoviedb) {
        this.themoviedb = themoviedb;
    }

    public String[] getAlternate_titles() {
        return alternate_titles;
    }

    public void setAlternate_titles(String[] alternate_titles) {
        this.alternate_titles = alternate_titles;
    }

    public String getImdb_id() {
        return imdb;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb = imdb_id;
    }

    public boolean isPre_order() {
        return pre_order;
    }

    public void setPre_order(boolean pre_order) {
        this.pre_order = pre_order;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRottentomatoes() {
        return rottentomatoes;
    }

    public void setRottentomatoes(String rottentomatoes) {
        this.rottentomatoes = rottentomatoes;
    }

    public String getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(String metacritic) {
        this.metacritic = metacritic;
    }

    public String getCommon_sense_media() {
        return common_sense_media;
    }

    public void setCommon_sense_media(String common_sense_media) {
        this.common_sense_media = common_sense_media;
    }

    public String getPoster_120x171() {
        return poster_120x171;
    }

    public void setPoster_120x171(String poster_120x171) {
        this.poster_120x171 = poster_120x171;
    }

    public String getPoster_240x342() {
        return poster_240x342;
    }

    public void setPoster_240x342(String poster_240x342) {
        this.poster_240x342 = poster_240x342;
    }

    public String getPoster_400x570() {
        return poster_400x570;
    }

    public void setPoster_400x570(String poster_400x570) {
        this.poster_400x570 = poster_400x570;
    }
}
