package com.mattfred.streamit.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mattfred.streamit.R;
import com.mattfred.streamit.model.Movie;

import java.util.List;

/**
 * Created by Matthew on 11/5/2015.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context, int resource, List<Movie> movies) {
        super(context, resource, movies);
        this.context = context;
        this.movies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_movie, parent, false);
        Movie movie = movies.get(position);
        TextView textView = (TextView) view.findViewById(R.id.tv_movie_title);
        textView.setText(movie.getTitle());
        return view;
    }
}
