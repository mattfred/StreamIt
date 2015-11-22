package com.mattfred.streamit.model;

import java.util.List;

/**
 * Created by Matthew on 11/21/2015.
 */
public class MovieInfo {

    private List<Source> subscription_web_sources;
    private List<Source> free_web_sources;

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
