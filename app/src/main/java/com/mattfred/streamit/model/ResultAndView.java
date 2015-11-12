package com.mattfred.streamit.model;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by matthewfrederick on 11/8/15.
 */
public class ResultAndView {

    public Movie movie;
    public Show show;
    public View view;
    public Bitmap bitmap;
    public boolean isMovie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public void setIsMovie(boolean isMovie) {
        this.isMovie = isMovie;
    }
}
