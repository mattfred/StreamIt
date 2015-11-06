package com.mattfred.streamit.broadcast;

import android.content.Intent;
import android.content.IntentFilter;

import com.mattfred.streamit.services.ApiTask;

/**
 * Created by Matthew on 11/5/2015.
 */
public class BroadcastUtil {

    public static final String STOP = BroadcastUtil.class.getSimpleName() + ".STOP";
    public static final String ERROR = BroadcastUtil.class.getSimpleName() + ".ERROR";
    public static final String TASK = "task";

    public static Intent stop(ApiTask task) {
        Intent intent = new Intent(STOP);
        intent.putExtra(TASK, task);
        return intent;
    }

    public static Intent error(ApiTask task) {
        Intent intent = new Intent(ERROR);
        intent.putExtra(TASK, task);
        return intent;
    }

    public static IntentFilter stopFilter() {
        return new IntentFilter(STOP);
    }

    public static IntentFilter errorFilter() {
        return new IntentFilter(ERROR);
    }
}
