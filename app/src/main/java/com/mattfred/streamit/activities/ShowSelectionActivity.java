package com.mattfred.streamit.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.mattfred.streamit.ProgressDialog;
import com.mattfred.streamit.R;
import com.mattfred.streamit.adapters.ExpandableListAdapter;
import com.mattfred.streamit.model.Season;
import com.mattfred.streamit.model.ShowEpisode;
import com.mattfred.streamit.model.ShowEpisodeResults;
import com.mattfred.streamit.utils.Constants;
import com.mattfred.streamit.utils.Globals;
import com.mattfred.streamit.utils.GuideBoxAPI;
import com.mattfred.streamit.utils.StreamItPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShowSelectionActivity extends AppCompatActivity {

    private static final String TAG = "ShowSelectionActivity";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, String> urlMap;
    int seasonCount;
    Season currentSeason;
    List<Season> seasonsList;
    Handler handler;
    private ProgressDialog progressDialog;
    // run this ever second for each season
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getShowEpisodeInfo();
            handler.postDelayed(runnable, 1100);
            rinseAndRepeat();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        urlMap = new HashMap<>();

        seasonsList = Globals.getSeasons();
        // first position
        seasonCount = 0;
        // first season
        currentSeason = seasonsList.get(seasonCount);

        setupParents();
        setupChildren();
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

    private void setupParents() {
        listDataHeader = new ArrayList<>();
        Log.d(TAG, "Seasons: " + seasonsList.size());
        for (int i = 0; i < seasonsList.size(); i++) {
            Log.d(TAG, "Adding parent " + seasonsList.get(i).toString() + " in position " + i);
            listDataHeader.add(i, seasonsList.get(i).toString());
        }
        Log.d(TAG, "Header size: " + listDataHeader.size());
    }

    private void setupChildren() {
        listDataChild = new HashMap<>();
        handler = new Handler();

        showProgress("Getting Episodes");

        // start at season 1
        if (listDataHeader.size() != 0) {
            runnable.run();
        }
    }

    private void stopRepeatingTask() {
        handler.removeCallbacks(runnable);
    }

    private void rinseAndRepeat() {
        // if season is the same as the size then all seasons are got
        if (listDataHeader.size() != (seasonCount + 1)) {
            // get ready to get next season
            seasonCount++;
            currentSeason = seasonsList.get(seasonCount);
        }
    }

    private void setupList() {
        stopRepeatingTask();

        // remove empty seasons
        Iterator<String> season = listDataHeader.iterator();
        while (season.hasNext()) {
            String next = season.next();
            if (listDataChild.get(next) == null || listDataChild.get(next).isEmpty())
                season.remove();
        }

        hideProgress();
        expListView = (ExpandableListView) findViewById(R.id.expandable_list);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String episode = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                navigateToWebsite(urlMap.get(episode));
                return false;
            }
        });
    }

    private void navigateToWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void showProgress(String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(ShowSelectionActivity.this);
            progressDialog.setText(message);
            progressDialog.show();
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else {
            progressDialog.setText(message);
        }
    }

    private void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    private void getShowEpisodeInfo() {
        final int localCount = seasonCount;
        final Season localSeason = currentSeason;
        final boolean lastSeason = ((localCount + 1) == seasonsList.size());
        Log.d(TAG, "Getting children for " + localSeason.toString());
        Log.d(TAG, "Last season: " + lastSeason);

        String region = StreamItPreferences.getString(ShowSelectionActivity.this, Constants.REGION_US, Constants.REGION_US);
        String apiKey = getString(R.string.apiKey);

        final String source = getIntent().getStringExtra(Constants.SOURCE);
        final String type = getIntent().getStringExtra(Constants.TYPE);

        GuideBoxAPI.getAPIService().getAvailableEpisodes(region, apiKey, String.valueOf(Globals.getId()),
                localSeason.getSeason_number(), source, new Callback<ShowEpisodeResults>() {
                    @Override
                    public void success(ShowEpisodeResults results, Response response) {
                        List<String> episodes = new ArrayList<>();
                        List<ShowEpisode> sorted = sortEpisodes(results.getResults());
                        for (ShowEpisode episode : sorted) {
                            String title = String.valueOf(episode.getEpisode_number()) + ": " + episode.getTitle();
                            episodes.add(title);
                            String url = getUrl(episode, type);
                            urlMap.put(title, url);
                        }
                        try {
                            listDataChild.put(listDataHeader.get(localCount), episodes);
                            Log.d(TAG, "Adding filled children to " + listDataHeader.get(localCount));
                        } catch (IndexOutOfBoundsException outOfBounds) {
                            listDataChild.put(listDataHeader.get(listDataHeader.size() - 1), episodes);
                        }
                        if (lastSeason) setupList();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                        // ignore server errors
                        if (error.getResponse().getStatus() < 500) {
                            hideProgress();
                            Toast.makeText(ShowSelectionActivity.this, R.string.generic_error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private String getUrl(ShowEpisode episode, String type) {
        if (Constants.FREE.equalsIgnoreCase(type)) {
            return episode.getFree_web_sources().get(0).getLink();
        }
        if (Constants.SUBSCRIPTION.equalsIgnoreCase(type)) {
            return episode.getSubscription_web_sources().get(0).getLink();
        }
        if (Constants.PAID.equalsIgnoreCase(type)) {
            return episode.getPurchase_web_sources().get(0).getLink();
        }
        return "";
    }

    private List<ShowEpisode> sortEpisodes(List<ShowEpisode> episodes) {
        Collections.sort(episodes, new Comparator<ShowEpisode>() {
            @Override
            public int compare(ShowEpisode lhs, ShowEpisode rhs) {
                return compare(lhs.getEpisode_number(), rhs.getEpisode_number());
            }

            private int compare(int first, int second) {
                if (first < second) return -1;
                if (first > second) return 1;
                else return 0;
            }
        });
        return episodes;
    }

}
