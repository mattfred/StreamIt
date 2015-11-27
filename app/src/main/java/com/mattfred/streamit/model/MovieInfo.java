package com.mattfred.streamit.model;

import java.util.List;

/**
 * Movie Info object
 */
public class MovieInfo {

    private List<Source> subscription_web_sources;
    private List<Source> free_web_sources;
    private List<Source> purchase_web_sources;
    private List<Channel> channels;

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

    public List<Source> getPurchase_web_sources() {
        return purchase_web_sources;
    }

    public void setPurchase_web_sources(List<Source> purchase_web_sources) {
        this.purchase_web_sources = purchase_web_sources;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
