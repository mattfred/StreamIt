package com.mattfred.streamit.model;

/**
 * Created by matthewfrederick on 11/26/15.
 */
public class Season {

    private int season_number;
    private String first_airdate;

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public String getFirst_airdate() {
        return first_airdate;
    }

    public void setFirst_airdate(String first_airdate) {
        this.first_airdate = first_airdate;
    }

    @Override
    public String toString() {
        return "Season: " + season_number;
    }
}
