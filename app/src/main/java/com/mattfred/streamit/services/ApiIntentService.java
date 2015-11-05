package com.mattfred.streamit.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Intent Service class used for making all Api Calls. This will take Api calls off of the UI thread.
 */
public class ApiIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by the subclass's constructor.
     */
    public ApiIntentService() {
        super("ApiIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
