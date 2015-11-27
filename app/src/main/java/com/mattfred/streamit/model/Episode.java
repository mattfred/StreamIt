package com.mattfred.streamit.model;

import java.util.List;

/**
 * Episode Object
 */
public class Episode {

    private int free;
    private int tv_everywhere;
    private int paid;
    private int subscription;
    private int total;
    private List<Source> all_sources;

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getTv_everywhere() {
        return tv_everywhere;
    }

    public void setTv_everywhere(int tv_everywhere) {
        this.tv_everywhere = tv_everywhere;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getSubscription() {
        return subscription;
    }

    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Source> getAll_sources() {
        return all_sources;
    }

    public void setAll_sources(List<Source> all_sources) {
        this.all_sources = all_sources;
    }
}
