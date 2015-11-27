package com.mattfred.streamit.utils;

import com.mattfred.streamit.interfaces.RetrofitInterface;

import retrofit.RestAdapter;

/**
 * Guide Box API builder
 */
public class GuideBoxAPI {

    public static RetrofitInterface getAPIService() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return adapter.create(RetrofitInterface.class);
    }
}
