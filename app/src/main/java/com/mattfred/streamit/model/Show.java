package com.mattfred.streamit.model;

/**
 * Show object
 */
public class Show {

    private int id;
    private String title;
    private String[] alternate_titles;
    private int container_show;
    private String first_aired;
    private String imdb_id;
    private String tvdb;
    private String themoviedb;
    private String freebase;
    private int wikipedia_id;
    private String artwork_208x117;
    private String artwork_304x171;
    private String artwork_448x252;
    private String artwork_608x342;

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

    public String[] getAlternate_titles() {
        return alternate_titles;
    }

    public void setAlternate_titles(String[] alternate_titles) {
        this.alternate_titles = alternate_titles;
    }

    public int getContainer_show() {
        return container_show;
    }

    public void setContainer_show(int container_show) {
        this.container_show = container_show;
    }

    public String getFirst_aired() {
        return first_aired;
    }

    public void setFirst_aired(String first_aired) {
        this.first_aired = first_aired;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getTvdb() {
        return tvdb;
    }

    public void setTvdb(String tvdb) {
        this.tvdb = tvdb;
    }

    public String getThemoviedb() {
        return themoviedb;
    }

    public void setThemoviedb(String themoviedb) {
        this.themoviedb = themoviedb;
    }

    public String getFreebase() {
        return freebase;
    }

    public void setFreebase(String freebase) {
        this.freebase = freebase;
    }

    public int getWikipedia_id() {
        return wikipedia_id;
    }

    public void setWikipedia_id(int wikipedia_id) {
        this.wikipedia_id = wikipedia_id;
    }

    public String getArtwork_208x117() {
        return artwork_208x117;
    }

    public void setArtwork_208x117(String artwork_208x117) {
        this.artwork_208x117 = artwork_208x117;
    }

    public String getArtwork_304x171() {
        return artwork_304x171;
    }

    public void setArtwork_304x171(String artwork_304x171) {
        this.artwork_304x171 = artwork_304x171;
    }

    public String getArtwork_448x252() {
        return artwork_448x252;
    }

    public void setArtwork_448x252(String artwork_448x252) {
        this.artwork_448x252 = artwork_448x252;
    }

    public String getArtwork_608x342() {
        return artwork_608x342;
    }

    public void setArtwork_608x342(String artwork_608x342) {
        this.artwork_608x342 = artwork_608x342;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
