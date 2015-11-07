package com.mattfred.streamit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.mattfred.streamit.R;
import com.mattfred.streamit.adapters.MovieAdapter;
import com.mattfred.streamit.utils.Globals;

public class MovieListActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        listView = (ListView) findViewById(R.id.list_view);
        updateDisplay();
    }

    protected void updateDisplay() {
        MovieAdapter adapter = new MovieAdapter(this, R.layout.item_movie, Globals.getResults().getResults());
        listView.setAdapter(adapter);
    }
}
