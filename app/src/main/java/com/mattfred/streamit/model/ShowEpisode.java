package com.mattfred.streamit.model;

import java.util.List;

/**
 * Show episode object
 */
public class ShowEpisode {

    private int id;
    private String content_type;
    private int season_number;
    private int episode_number;
    private String title;
    private List<Source> purchase_web_sources;
    private List<Source> subscription_web_sources;
    private List<Source> free_web_sources;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Source> getPurchase_web_sources() {
        return purchase_web_sources;
    }

    public void setPurchase_web_sources(List<Source> purchase_web_sources) {
        this.purchase_web_sources = purchase_web_sources;
    }

    public List<Source> getSubscription_web_sources() {
        return subscription_web_sources;
    }

    public void setSubscription_web_sources(List<Source> subscription_web_sources) {
        this.subscription_web_sources = subscription_web_sources;
    }

    public List<Source> getFree_web_sources() {
        return free_web_sources;
    }

    public void setFree_web_sources(List<Source> free_web_sources) {
        this.free_web_sources = free_web_sources;
    }
}
