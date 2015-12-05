package com.mattfred.streamit.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.common.base.Strings;
import com.mattfred.streamit.AnalyticsTrackers;
import com.mattfred.streamit.R;
import com.mattfred.streamit.model.AvailableContentResults;
import com.mattfred.streamit.model.MovieInfo;
import com.mattfred.streamit.model.Season;
import com.mattfred.streamit.model.SeasonResults;
import com.mattfred.streamit.model.Source;
import com.mattfred.streamit.utils.Constants;
import com.mattfred.streamit.utils.Globals;
import com.mattfred.streamit.utils.GuideBoxAPI;
import com.mattfred.streamit.utils.KeyboardHider;
import com.mattfred.streamit.utils.StreamItPreferences;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieDetails extends AppCompatActivity implements View.OnClickListener {

    private AdView mAdView;
    private ImageView imageView;
    private Button free, subscription, paid;
    private MovieInfo info;
    private List<Source> tvPaid, tvSubscription, tvFree;
    private ProgressBar progressBar;
    private List<Season> seasons;
    private Source source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.main_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardHider.hideKeyboard(MovieDetails.this);
            }
        });

        TextView textView = (TextView) findViewById(R.id.tv_movie_title);
        textView.setText(Globals.getTitle());

        free = (Button) findViewById(R.id.btn_free);
        free.setClickable(false);
        free.setOnClickListener(this);

        subscription = (Button) findViewById(R.id.btn_subscription);
        subscription.setClickable(false);
        subscription.setOnClickListener(this);

        paid = (Button) findViewById(R.id.btn_paid);
        paid.setClickable(false);
        paid.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        if (Globals.isMovie()) {
            getDetails();
        } else {
            getShowDetails();
        }

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
        navigateToWebsite(url);
    }

    private void navigateToWebsite(String url) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupButtonText() {
        if (Globals.isMovie()) {
            String freeString = getString(R.string.free_source) + info.getFree_web_sources().size();
            free.setText(freeString);
            free.setEnabled(true);

            String subscriptionString = getString(R.string.subscription_source) + info.getSubscription_web_sources().size();
            subscription.setText(subscriptionString);
            subscription.setEnabled(true);

            String paidText = getString(R.string.paid_sources) + info.getPurchase_web_sources().size();
            paid.setText(paidText);
            paid.setEnabled(true);

        } else {
            String freeString = getString(R.string.free_source) + tvFree.size();
            free.setText(freeString);

            String subscriptionString = getString(R.string.subscription_source) + tvSubscription.size();
            subscription.setText(subscriptionString);

            String paidString = getString(R.string.paid_sources) + tvPaid.size();
            paid.setText(paidString);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == free.getId()) {
            if (Globals.isMovie()) {
                showDialog(toStringArray(info.getFree_web_sources()), info.getFree_web_sources());
            } else {
                showDialog(toStringArray(tvFree), tvFree);
            }
        } else if (v.getId() == subscription.getId()) {
            if (Globals.isMovie()) {
                showDialog(toStringArray(info.getSubscription_web_sources()), info.getSubscription_web_sources());
            } else {
                showDialog(toStringArray(tvSubscription), tvSubscription);
            }
        } else if (v.getId() == paid.getId()) {
            if (Globals.isMovie()) {
                showDialog(toStringArray(info.getPurchase_web_sources()), info.getPurchase_web_sources());
            } else {
                showDialog(toStringArray(tvPaid), tvPaid);
            }
        }
    }

    private <T> String[] toStringArray(List<T> list) {

        if (list.isEmpty()) {
            return new String[]{getString(R.string.no_sources)};
        }

        String[] array = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).toString();
        }
        return array;
    }

    private void showDialog(final String[] array, final List<Source> sources) {

        AlertDialog dialog = new AlertDialog.Builder(MovieDetails.this)
                .setTitle(R.string.sources_title)
                .setItems(array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        source = sources.get(which);
                        if (Globals.isMovie()) {
                            if (!Strings.isNullOrEmpty(source.getLink())) {
                                navigateToWebsite(source.getLink());
                            }
                        } else {

                            if (seasons == null || seasons.isEmpty()) {
                                getShowSeasons(true);
                            } else {
                                Intent intent = new Intent(MovieDetails.this, ShowSelectionActivity.class);
                                intent.putExtra(Constants.SEASONS, seasons.size());
                                intent.putExtra(Constants.SOURCE, source.getSource());
                                startActivity(intent);
                            }
                        }
                    }
                }).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void getDetails() {
        String region = StreamItPreferences.getString(MovieDetails.this, Constants.REGION_US, Constants.REGION_US);
        String apiKey = getString(R.string.apiKey);

        GuideBoxAPI.getAPIService().getMovieDetails(region, apiKey, String.valueOf(Globals.getId()), new Callback<MovieInfo>() {
            @Override
            public void success(MovieInfo movieDetails, Response response) {
                info = movieDetails;
                setupButtonText();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void getShowDetails() {
        String region = StreamItPreferences.getString(MovieDetails.this, Constants.REGION_US, Constants.REGION_US);
        String apiKey = getString(R.string.apiKey);

        GuideBoxAPI.getAPIService().getAvailableContent(region, apiKey, String.valueOf(Globals.getId()), new Callback<AvailableContentResults>() {
            @Override
            public void success(AvailableContentResults availableContentResults, Response response) {
                setupSources(availableContentResults.getResults().getWeb().getEpisodes().getAll_sources());
                setupButtonText();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getShowSeasons(false);
                    }
                }, 1000);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void getShowSeasons(final boolean startIntent) {
        String region = StreamItPreferences.getString(MovieDetails.this, Constants.REGION_US, Constants.REGION_US);
        String apiKey = getString(R.string.apiKey);

        GuideBoxAPI.getAPIService().getShowSeasons(region, apiKey, String.valueOf(Globals.getId()), new Callback<SeasonResults>() {
            @Override
            public void success(SeasonResults seasonResults, Response response) {
                seasons = seasonResults.getResults();
                if (startIntent) {
                    Intent intent = new Intent(MovieDetails.this, ShowSelectionActivity.class);
                    intent.putExtra(Constants.SEASONS, seasons.size());
                    intent.putExtra(Constants.SOURCE, source.getSource());
                    startActivity(intent);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void setupSources(List<Source> sources) {
        tvFree = new ArrayList<>();
        tvSubscription = new ArrayList<>();
        tvPaid = new ArrayList<>();

        for (Source source : sources) {
            if (source.getType().equalsIgnoreCase("free")) tvFree.add(source);
            if (source.getType().equalsIgnoreCase("subscription")) tvSubscription.add(source);
            if (source.getType().equalsIgnoreCase("paid")) tvPaid.add(source);
        }
    }

    private void trackScreen() {
        Tracker tracker = AnalyticsTrackers.getInstance(MovieDetails.this).get(AnalyticsTrackers.Target.APP);
        tracker.setScreenName("Movie Details");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
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
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
