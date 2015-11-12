package com.mattfred.streamit.model;

import java.util.List;

/**
 * Created by Matthew on 11/5/2015.
 */
public class MovieResult {

    private List<Movie> results;
    private int total_results;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
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
        return "MovieResult{" +
                "total_results=" + total_results +
                '}';
    }
}
