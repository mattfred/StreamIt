package com.mattfred.streamit.utils;

import com.mattfred.streamit.interfaces.RetrofitInterface;

import retrofit.RestAdapter;

/**
 * Created by Matthew on 11/5/2015.
 */
public class GuideBoxAPI {

    public static RetrofitInterface getAPIService() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();

        return adapter.create(RetrofitInterface.class);
    }
}
