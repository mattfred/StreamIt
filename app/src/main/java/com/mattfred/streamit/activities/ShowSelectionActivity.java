package com.mattfred.streamit.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShowSelectionActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
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

        setupParents();
        setupChildren();
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
        } else {
            stopRepeatingTask();
            hideProgress();
            expListView = (ExpandableListView) findViewById(R.id.expandable_list);
            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);
        }
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
        String region = StreamItPreferences.getString(ShowSelectionActivity.this, Constants.REGION_US, Constants.REGION_US);
        String apiKey = getString(R.string.apiKey);

        String source = getIntent().getStringExtra(Constants.SOURCE);
        GuideBoxAPI.getAPIService().getAvailableEpisodes(region, apiKey, String.valueOf(Globals.getId()),
                season, source, new Callback<ShowEpisodeResults>() {
                    @Override
                    public void success(ShowEpisodeResults results, Response response) {
                        List<String> episodes = new ArrayList<String>();
                        for (ShowEpisode episode : results.getResults()) {
                            episodes.add(String.valueOf(episode.getEpisode_number()) + ": " + episode.getTitle());
                        }
                        listDataChild.put(listDataHeader.get(thisSeason - 1), episodes);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
    }

}
