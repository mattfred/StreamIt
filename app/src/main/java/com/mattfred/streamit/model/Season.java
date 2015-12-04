package com.mattfred.streamit.model;

/**
 * Created by matthewfrederick on 12/3/15.
 */
public class Season {

    private int season_number;

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    @Override
    public String toString() {
        return "Season " + String.valueOf(season_number);
    }
}
