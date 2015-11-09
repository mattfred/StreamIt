package com.mattfred.streamit.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.common.base.Strings;
import com.mattfred.streamit.ProgressDialog;
import com.mattfred.streamit.R;
import com.mattfred.streamit.broadcast.BroadcastUtil;
import com.mattfred.streamit.services.ApiIntentService;
import com.mattfred.streamit.services.ApiTask;

public class MainActivity extends AppCompatActivity {

    private EditText searchBox;
    private ProgressDialog progressDialog;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        searchBox = (EditText) findViewById(R.id.et_search_box);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchClicked(View view) {
        if (validateSearchValue()) {
            String title = searchBox.getText().toString();
            showProgress(getString(R.string.searching));
            ApiIntentService.titleSearch(MainActivity.this, title);
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
                    if (ApiTask.TitleSearch == task) {
                        startActivity(new Intent(MainActivity.this, MovieListActivity.class));
                    }
                } else if (BroadcastUtil.ERROR.equals(intent.getAction())) {
                    if (ApiTask.TitleSearch == task) {
                        Toast.makeText(MainActivity.this, R.string.search_error, Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, BroadcastUtil.stopFilter());
        broadcastManager.registerReceiver(broadcastReceiver, BroadcastUtil.errorFilter());
    }

    private void removeReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
