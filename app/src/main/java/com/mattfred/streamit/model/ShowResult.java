package com.mattfred.streamit.model;

import java.util.List;

/**
 * Created by Matthew on 11/11/2015.
 */
public class ShowResult {

    private List<Show> results;
    private int total_results;

    public List<Show> getResults() {
        return results;
    }

    public void setResults(List<Show> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public String toString() {
        return "ShowResult{" +
                "total_results=" + total_results +
                '}';
    }
}
