package com.mattfred.streamit.model;

import java.util.List;

/**
 * Source Result object
 */
public class SourceResult {

    private List<SubscriptionSource> results;

    public List<SubscriptionSource> getResults() {
        return results;
    }

    public void setResults(List<SubscriptionSource> results) {
        this.results = results;
    }
}
