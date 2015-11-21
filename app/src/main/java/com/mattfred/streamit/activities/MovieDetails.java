package com.mattfred.streamit.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mattfred.streamit.R;
import com.mattfred.streamit.model.MovieInfo;
import com.mattfred.streamit.model.Source;
import com.mattfred.streamit.utils.Constants;
import com.mattfred.streamit.utils.Globals;
import com.mattfred.streamit.utils.GuideBoxAPI;
import com.mattfred.streamit.utils.StreamItPreferences;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieDetails extends AppCompatActivity {

    private AdView mAdView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Globals.isMovie()) {
            getDetails();
        } else {
            getShowDetials();
        }

        TextView textView = (TextView) findViewById(R.id.tv_movie_title);
        textView.setText(Globals.getTitle());

        imageView = (ImageView) findViewById(R.id.iv_movie_icon);
        loadImage();

        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private void loadImage() {
        ImageLoader loader = new ImageLoader();
        loader.execute(Globals.getBitmapURL());
    }

    public void showIMDB(View view) {
        String url = "http://www.imdb.com/title/" + Globals.getImdb_id();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupListView(List<Source> movieInfo) {
        String[] sources;
        if (movieInfo.size() < 1) {
            sources = new String[]{"No Online Streaming Available."};
        } else {
            sources = new String[movieInfo.size()];
            for (int i = 0; i < movieInfo.size(); i++) {
                Source source = movieInfo.get(i);
                sources[i] = source.getDisplay_name();
            }
        }

        ListView listView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MovieDetails.this,
                R.layout.item_source, R.id.text_view, sources);
        listView.setAdapter(adapter);
    }

    private class ImageLoader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];

            try {
                InputStream in = (InputStream) new URL(url).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                in.close();
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private void getDetails() {
        String region = StreamItPreferences.getString(MovieDetails.this, Constants.REGION, Constants.REGION_US);
        String apiKey = getString(R.string.apiKey);

        GuideBoxAPI.getAPIService().getMovieDetials(region, apiKey, String.valueOf(Globals.getId()), new Callback<MovieInfo>() {
            @Override
            public void success(MovieInfo movieDetails, Response response) {
                setupListView(movieDetails.getSubscription_web_sources());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void getShowDetials() {
        String region = StreamItPreferences.getString(MovieDetails.this, Constants.REGION, Constants.REGION_US);
        String apiKey = getString(R.string.apiKey);

        GuideBoxAPI.getAPIService().getShowDetials(region, apiKey, String.valueOf(Globals.getId()), new Callback<MovieInfo>() {
            @Override
            public void success(MovieInfo movieDetails, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
