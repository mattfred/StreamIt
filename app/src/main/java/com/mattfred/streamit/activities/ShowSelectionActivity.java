package com.mattfred.streamit.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.mattfred.streamit.ProgressDialog;
import com.mattfred.streamit.R;
import com.mattfred.streamit.adapters.ExpandableListAdapter;
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

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, String> urlMap;
    int season;
    Handler handler;
    private ProgressDialog progressDialog;
    // run this ever second for each season
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getShowEpisodeInfo();
            handler.postDelayed(runnable, 1000);
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
        return super.onOptionsItemSelected(item);
    }

    private void setupParents() {
        listDataHeader = new ArrayList<>();
        int seasons = getIntent().getIntExtra(Constants.SEASONS, 0);
        for (int i = 0; i < seasons; i++) {
            listDataHeader.add("Season " + (i + 1));
        }
    }

    private void setupChildren() {
        listDataChild = new HashMap<>();
        handler = new Handler();

        showProgress("Getting Episodes");

        // start at season 1
        if (listDataHeader.size() != 0) {
            season = 1;
            runnable.run();
        }
    }

    private void stopRepeatingTask() {
        handler.removeCallbacks(runnable);
    }

    private void rinseAndRepeat() {
        // if season is the same as the size then all seasons are got
        if (listDataHeader.size() != season) {
            // get ready to get next season
            season++;
        }
    }

    private void setupList() {
        stopRepeatingTask();

        // remove empty seasons
        Iterator<String> season = listDataHeader.iterator();
        while (season.hasNext()) {
            if (listDataChild.get(season.next()).isEmpty()) season.remove();
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
        final int thisSeason = season;
        final boolean lastSeason = (season == getIntent().getIntExtra(Constants.SEASONS, 0));
        String region = StreamItPreferences.getString(ShowSelectionActivity.this, Constants.REGION_US, Constants.REGION_US);
        String apiKey = getString(R.string.apiKey);

        final String source = getIntent().getStringExtra(Constants.SOURCE);
        final String type = getIntent().getStringExtra(Constants.TYPE);
        GuideBoxAPI.getAPIService().getAvailableEpisodes(region, apiKey, String.valueOf(Globals.getId()),
                season, source, new Callback<ShowEpisodeResults>() {
                    @Override
                    public void success(ShowEpisodeResults results, Response response) {
                        List<String> episodes = new ArrayList<>();
                        if (results.getResults() == null || results.getResults().isEmpty()) {
                            listDataChild.put(listDataHeader.get(thisSeason - 1), episodes);
                        } else {
                            List<ShowEpisode> sorted = sortEpisodes(results.getResults());
                            for (ShowEpisode episode : sorted) {
                                String title = String.valueOf(episode.getEpisode_number()) + ": " + episode.getTitle();
                                episodes.add(title);
                                String url = getUrl(episode, type);
                                urlMap.put(title, url);
                            }
                            listDataChild.put(listDataHeader.get(thisSeason - 1), episodes);
                        }
                        if (lastSeason) setupList();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
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
