package com.mattfred.streamit;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by matthewfrederick on 11/22/15.
 */
public class StreamIt extends Application {

    private static GoogleAnalytics analytics;
    private static Tracker tracker;

    public static GoogleAnalytics analytics() {
        return analytics;
    }

    public static Tracker tracker() {
        return tracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        analytics = GoogleAnalytics.getInstance(this);
        tracker = analytics.newTracker("UA-46348904-2");
        tracker.enableAutoActivityTracking(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableExceptionReporting(true);
    }
}
