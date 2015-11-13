package com.mattfred.streamit.activities;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import com.mattfred.streamit.R;
import com.mattfred.streamit.model.SubscriptionSource;
import com.mattfred.streamit.utils.Globals;

import java.util.List;

/**
 * Created by matthewfrederick on 11/12/15.
 */
public class SettingsActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            List<SubscriptionSource> sources = Globals.getSources();
            if (sources != null) {
                PreferenceCategory category = (PreferenceCategory) findPreference("pref_key_subscriptions_settings");

                for (SubscriptionSource source : sources) {
                    CheckBoxPreference preference = new CheckBoxPreference(context);
                    preference.setKey(source.getSource());
                    preference.setTitle(source.getDisplay_name());
                    preference.setDefaultValue(false);
                    category.addPreference(preference);
                }
            }
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
