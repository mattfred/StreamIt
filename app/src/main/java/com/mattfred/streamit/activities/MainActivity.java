package com.mattfred.streamit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.mattfred.streamit.R;
import com.mattfred.streamit.services.ApiIntentService;

public class MainActivity extends AppCompatActivity {

    private EditText searchBox;

    public void searchClicked(View view) {
        if (validateSearchValue()) {
            String title = searchBox.getText().toString();
            ApiIntentService.titleSearch(MainActivity.this, title);
        } else {
            Toast.makeText(this, R.string.search_empty_toast, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateSearchValue() {
       return !Strings.isNullOrEmpty(searchBox.getText().toString());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchBox = (EditText) findViewById(R.id.et_search_box);
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
}
