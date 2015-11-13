package com.mattfred.streamit.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.mattfred.streamit.R;
import com.mattfred.streamit.broadcast.BroadcastUtil;
import com.mattfred.streamit.model.MovieResult;
import com.mattfred.streamit.model.ShowResult;
import com.mattfred.streamit.model.SourceResult;
import com.mattfred.streamit.utils.Caster;
import com.mattfred.streamit.utils.Constants;
import com.mattfred.streamit.utils.Globals;
import com.mattfred.streamit.utils.GuideBoxAPI;
import com.mattfred.streamit.utils.StreamItPreferences;

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

        if (ApiTask.MovieTitleSearch == task) {
            String title = intent.getStringExtra(TITLE);
            GuideBoxAPI.getAPIService().performMovieTitleSearch(region, apiKey, title, new Callback<MovieResult>() {
                @Override
                public void success(MovieResult movieResult, Response response) {
                    Globals.setResults(Caster.castCollection(movieResult.getResults(), Object.class));
                    LocalBroadcastManager.getInstance(ApiIntentService.this)
                            .sendBroadcast(BroadcastUtil.stop(ApiTask.MovieTitleSearch));
                }

                @Override
                public void failure(RetrofitError error) {
                    LocalBroadcastManager.getInstance(ApiIntentService.this)
                            .sendBroadcast(BroadcastUtil.error(ApiTask.MovieTitleSearch));
                }
            });
        } else if (ApiTask.ShowTitleSearch == task) {
            String title = intent.getStringExtra(TITLE);
            GuideBoxAPI.getAPIService().performShowTitleSearch(region, apiKey, title, new Callback<ShowResult>() {
                @Override
                public void success(ShowResult showResult, Response response) {
                    Globals.setResults(Caster.castCollection(showResult.getResults(), Object.class));
                    LocalBroadcastManager.getInstance(ApiIntentService.this)
                            .sendBroadcast(BroadcastUtil.stop(ApiTask.ShowTitleSearch));
                }

                @Override
                public void failure(RetrofitError error) {
                    LocalBroadcastManager.getInstance(ApiIntentService.this)
                            .sendBroadcast(BroadcastUtil.error(ApiTask.ShowTitleSearch));
                }
            });
        } else if (ApiTask.GetSources == task) {
            GuideBoxAPI.getAPIService().getSubscriptionSources(region, apiKey, new Callback<SourceResult>() {
                @Override
                public void success(SourceResult sourceResult, Response response) {
                    Globals.setSources(sourceResult.getResults());
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
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

    public static void movieTitleSearch(Context context, String title) {
        Bundle bundle = new Bundle(2);
        bundle.putString(TITLE, title);
        bundle.putSerializable(TASK, ApiTask.MovieTitleSearch);
        baseIntent(context, bundle);
    }

    public static void showTitleSearch(Context context, String title) {
        Bundle bundle = new Bundle(2);
        bundle.putString(TITLE, title);
        bundle.putSerializable(TASK, ApiTask.ShowTitleSearch);
        baseIntent(context, bundle);
    }

    public static void getSubscriptionSources(Context context) {
        Bundle bundle = new Bundle(1);
        bundle.putSerializable(TASK, ApiTask.GetSources);
        baseIntent(context, bundle);
    }
}
