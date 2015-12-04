package com.mattfred.streamit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import com.mattfred.streamit.R;
import com.mattfred.streamit.adapters.ExpandableListAdapter;
import com.mattfred.streamit.utils.Constants;

import java.util.HashMap;
import java.util.List;

public class ShowSelectionActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupParents();

        expListView = (ExpandableListView) findViewById(R.id.expandable_list);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }

    private void setupParents() {
        int seasons = getIntent().getIntExtra(Constants.SEASONS, 0);
        for (int i = 0; i < seasons; i++) {
            listDataHeader.add("Season " + i + 1);
        }
    }

    private void setupChildren() {

    }

}
