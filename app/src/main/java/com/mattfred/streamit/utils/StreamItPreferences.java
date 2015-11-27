package com.mattfred.streamit.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Preference helper class
 */
public class StreamItPreferences {

    public static String getString(Context context, String name, String defaultValue) {
        return getPreferences(context).getString(name, defaultValue);
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCES_FILE, Context.MODE_PRIVATE);
    }
}
