package com.mattfred.streamit.activities;

import android.app.ListActivity;
import android.os.Bundle;

import com.mattfred.streamit.R;
import com.mattfred.streamit.adapters.MovieAdapter;
import com.mattfred.streamit.utils.Globals;

public class MovieListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        updateDisplay();
    }

    protected void updateDisplay() {
        MovieAdapter adapter = new MovieAdapter(this, R.layout.item_movie, Globals.getResults().getMovies());
        setListAdapter(adapter);
    }
}
