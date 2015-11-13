package com.mattfred.streamit.interfaces;

import com.mattfred.streamit.model.MovieResult;
import com.mattfred.streamit.model.ShowResult;
import com.mattfred.streamit.model.SourceResult;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Interface used to hold all retrofit Api calls.
 */
public interface RetrofitInterface {

    @GET("/{region}/{apiKey}/search/movie/title/{searchTitle}/fuzzy")
    void performMovieTitleSearch(@Path("region") String region, @Path("apiKey") String apiKey, @Path("searchTitle") String searchTitle, Callback<MovieResult> cb);

    @GET("/{region}/{apiKey}/search/title/{searchTitle}/fuzzy")
    void performShowTitleSearch(@Path("region") String region, @Path("apiKey") String apiKey, @Path("searchTitle") String searchTitle, Callback<ShowResult> cb);

    @GET("/{region}/{apiKey}/sources/subscription")
    void getSubscriptionSources(@Path("region") String region, @Path("apiKey") String apiKey, Callback<SourceResult> cb);
}
