package com.mattfred.streamit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mattfred.streamit.R;
import com.mattfred.streamit.model.Movie;
import com.mattfred.streamit.utils.Caster;
import com.mattfred.streamit.utils.Constants;
import com.mattfred.streamit.utils.Globals;

import java.util.List;

public class MovieDetails extends AppCompatActivity {

    private AdView mAdView;
    private Movie movie;
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.tv_movie_title);
        //imageView = (ImageView) findViewById(R.id.iv_movie_poster);

        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        List<Movie> movies = Caster.castCollection(Globals.getResults(), Movie.class);
        movie = movies.get(getIntent().getIntExtra(Constants.MOVIE_POSITION, 0));
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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
}
