package com.mattfred.streamit.utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Utility class for hiding the keyboard
 */
public class KeyboardHider {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
