package com.mattfred.streamit.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mattfred.streamit.R;
import com.mattfred.streamit.utils.Constants;
import com.mattfred.streamit.utils.GuideBoxAPI;
import com.mattfred.streamit.utils.StreamItPreferences;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Intent Service class used for making all Api Calls. This will take Api calls off of the UI thread.
 */
public class ApiIntentService extends IntentService {

    private static final String TITLE = "title";
    private static final String TASK = "task";

    /**
     * Creates an IntentService.  Invoked by the subclass's constructor.
     */
    public ApiIntentService() {
        super("ApiIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String region = StreamItPreferences.getString(ApiIntentService.this, Constants.REGION, Constants.REGION_US);
        String apiKey = getString(R.string.apiKey);
        ApiTask task = (ApiTask) intent.getSerializableExtra(TASK);

        if (ApiTask.TitleSearch == task) {
            String title = intent.getStringExtra(TITLE);
            GuideBoxAPI.getAPIService().performTitleSearch(region, apiKey, title, new Callback<JSONObject>() {
                @Override
                public void success(JSONObject jsonObject, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }

    }

    private static void baseIntent(Context context, @Nullable Bundle bundle) {
        Intent intent = new Intent(context, ApiIntentService.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startService(intent);
    }

    public static void titleSearch(Context context, String title) {
        Bundle bundle = new Bundle(2);
        bundle.putString(TITLE, title);
        bundle.putSerializable(TASK, ApiTask.TitleSearch);
        baseIntent(context, bundle);
    }
}
