package com.mattfred.streamit.model;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Container to hold items used in the movie results list
 */
public class ResultAndView {

    public TextView textView;
    public ImageView imageView;
    public Movie movie;
    public Show show;
    public Bitmap bitmap;
    public boolean isMovie;
    public ProgressBar progressBar;
}
