package com.mattfred.streamit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mattfred.streamit.R;
import com.mattfred.streamit.adapters.MovieAdapter;
import com.mattfred.streamit.model.Movie;
import com.mattfred.streamit.model.Show;
import com.mattfred.streamit.utils.Globals;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private List<Object> objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object object = objects.get(position);
        if (object instanceof Movie) {
            Movie movie = (Movie) object;
            Globals.setBitmapURL(movie.getPoster_400x570());
            Globals.setTitle(movie.getTitle());
            Globals.setImdb_id(movie.getImdb_id());
        } else {
            Show show = (Show) object;
            Globals.setBitmapURL(show.getArtwork_608x342());
            Globals.setTitle(show.getTitle());
            Globals.setImdb_id(show.getImdb_id());
        }
        startActivity(new Intent(this, MovieDetails.class));
    }
}
