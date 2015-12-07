package com.mattfred.streamit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mattfred.streamit.AnalyticsTrackers;
import com.mattfred.streamit.R;
import com.mattfred.streamit.adapters.MovieAdapter;
import com.mattfred.streamit.model.Movie;
import com.mattfred.streamit.model.Show;
import com.mattfred.streamit.utils.Globals;

import java.util.List;

public class MovieListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private List<Object> objects;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        objects = Globals.getResults();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);
        updateDisplay();
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        trackScreen();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void updateDisplay() {
        MovieAdapter adapter = new MovieAdapter(this, objects);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        if (id == R.id.search) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        if (id == R.id.share) {
            startActivity(Globals.getShareIntent());
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
            Globals.setId(movie.getId());
            Globals.setIsMovie(true);
        } else {
            Show show = (Show) object;
            Globals.setBitmapURL(show.getArtwork_608x342());
            Globals.setTitle(show.getTitle());
            Globals.setImdb_id(show.getImdb_id());
            Globals.setId(show.getId());
            Globals.setIsMovie(false);
        }
        startActivity(new Intent(this, MovieDetails.class));
    }

    private void trackScreen() {
        Tracker tracker = AnalyticsTrackers.getInstance(MovieListActivity.this).get(AnalyticsTrackers.Target.APP);
        tracker.setScreenName("Movie List");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
