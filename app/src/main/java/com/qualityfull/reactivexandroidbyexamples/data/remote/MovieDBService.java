package com.qualityfull.reactivexandroidbyexamples.data.remote;

import com.qualityfull.reactivexandroidbyexamples.core.Constants;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.Credits;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.IdExternal;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.SeriesResponse;
import com.qualityfull.reactivexandroidbyexamples.data.remote.error.RxErrorHandlingCallAdapterFactory;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBService {

    @GET("/3/search/tv")
    Single<SeriesResponse> getSeries(
            @Query(Constants.API_KEY) String apiKey,
            @Query(Constants.QUERY) String name,
            @Query(Constants.PAGE) String page);

    @GET("/3/tv/{tv_id}/external_ids")
    Single<IdExternal> getIdExternal(
            @Path("tv_id") Integer tv_id,
            @Query(Constants.API_KEY) String apiKey);

    @GET("/3/tv/{tv_id}/credits")
    Single<Credits> getCredits(
            @Path("tv_id") Integer tv_id,
            @Query(Constants.API_KEY) String apiKey);

    /******** Helper class that sets up a new services *******/
    class Creator {
        public static MovieDBService newMovieDBService() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.MOVIE_BD_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                    .build();
            return retrofit.create(MovieDBService.class);
        }
    }
}
