package com.mattfred.streamit.interfaces;

import com.mattfred.streamit.model.Results;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Interface used to hold all retrofit Api calls.
 */
public interface RetrofitInterface {

    @GET("/{region}/{apiKey}/search/title/{searchTitle}/fuzzy")
    void performTitleSearch(@Path("region") String region, @Path("apiKey") String apiKey, @Path("searchTitle") String searchTitle, Callback<Results> cb);

}
