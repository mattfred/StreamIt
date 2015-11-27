package com.mattfred.streamit.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.common.base.Strings;
import com.mattfred.streamit.AnalyticsTrackers;
import com.mattfred.streamit.ProgressDialog;
import com.mattfred.streamit.R;
import com.mattfred.streamit.broadcast.BroadcastUtil;
import com.mattfred.streamit.services.ApiIntentService;
import com.mattfred.streamit.services.ApiTask;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText searchBox;
    private ProgressDialog progressDialog;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;

    private RadioButton movieRB;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        searchBox = (EditText) findViewById(R.id.et_search_box);
        movieRB = (RadioButton) findViewById(R.id.rd_movie);

        registerReceiver();
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
        removeReceiver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    public void searchClicked(View view) {
        if (validateSearchValue()) {
            String title = searchBox.getText().toString();
            showProgress(getString(R.string.searching));

            if (movieRB.isChecked()) {
                Log.i(TAG, "Movie Search");
                ApiIntentService.movieTitleSearch(MainActivity.this, title);
            } else {
                Log.i(TAG, "Show Search");
                ApiIntentService.showTitleSearch(MainActivity.this, title);
            }
        } else {
            Toast.makeText(this, R.string.search_empty_toast, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateSearchValue() {
        return !Strings.isNullOrEmpty(searchBox.getText().toString());
    }

    private void showProgress(String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(MainActivity.this);
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

    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                hideProgress();
                final ApiTask task = (ApiTask) intent.getSerializableExtra(BroadcastUtil.TASK);

                if (BroadcastUtil.STOP.equals(intent.getAction())) {
                    if (ApiTask.MovieTitleSearch == task) {
                        startActivity(new Intent(MainActivity.this, MovieListActivity.class));
                    } else if (ApiTask.ShowTitleSearch == task) {
                        startActivity(new Intent(MainActivity.this, MovieListActivity.class));
                    }
                } else if (BroadcastUtil.ERROR.equals(intent.getAction())) {
                    if (ApiTask.MovieTitleSearch == task || ApiTask.ShowTitleSearch == task) {
                        Toast.makeText(MainActivity.this, R.string.search_error, Toast.LENGTH_LONG).show();
                    }
                } else if (BroadcastUtil.NO_RESULTS.equals(intent.getAction())) {
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.no_results_title)
                            .setMessage(R.string.no_results_message)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    dialog.show();
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, BroadcastUtil.stopFilter());
        broadcastManager.registerReceiver(broadcastReceiver, BroadcastUtil.errorFilter());
        broadcastManager.registerReceiver(broadcastReceiver, BroadcastUtil.noResultsFilter());
    }

    private void trackScreen() {
        Tracker tracker = AnalyticsTrackers.getInstance(MainActivity.this).get(AnalyticsTrackers.Target.APP);
        tracker.setScreenName("Main Activity");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void removeReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
