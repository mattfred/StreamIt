package com.mattfred.streamit.model;

import java.util.List;

/**
 * Created by Matthew on 11/5/2015.
 */
public class Results {

    private List<Movie> movies;
    private int total_results;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }


    @Override
    public String toString() {
        return "Results{" +
                "total_results=" + total_results +
                '}';
    }
}
