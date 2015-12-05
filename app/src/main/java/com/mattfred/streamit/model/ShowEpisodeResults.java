package com.mattfred.streamit.model;

import java.util.List;

/**
 * Created by matthewfrederick on 12/4/15.
 */
public class ShowEpisodeResults {

    private int total_results;
    private List<ShowEpisode> results;

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<ShowEpisode> getResults() {
        return results;
    }

    public void setResults(List<ShowEpisode> results) {
        this.results = results;
    }
}
