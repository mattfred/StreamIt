package com.mattfred.streamit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mattfred.streamit.R;
import com.mattfred.streamit.adapters.MovieAdapter;
import com.mattfred.streamit.utils.Constants;
import com.mattfred.streamit.utils.Globals;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private List<Object> objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        objects = Globals.getResults();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);
        updateDisplay();
    }

    protected void updateDisplay() {
        MovieAdapter adapter = new MovieAdapter(this, R.layout.item_movie, objects);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra(Constants.MOVIE_POSITION, position);
        startActivity(intent);
    }
}
