package com.qualityfull.reactivexandroidbyexamples.data.remote;

import com.qualityfull.reactivexandroidbyexamples.core.Constants;
import com.qualityfull.reactivexandroidbyexamples.data.model.request.tvdb.Client;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.DataSeries;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.DataSeriesItem;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.Episodes;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.Token;
import com.qualityfull.reactivexandroidbyexamples.data.remote.error.RxErrorHandlingCallAdapterFactory;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvDBService {

    @POST("/login")
    Single<Token> getToken(@Body Client client);

    @GET("/search/series")
    Single<DataSeries> getSeries(@Query("name") String name, @Header("Authorization") String authHeader);

    @GET("/series/{id}")
    Single<DataSeriesItem> getSeries(@Path("id") Long id, @Header("Authorization") String authHeader);

    @GET("/series/{id}/episodes")
    Single<Episodes> getEpisodes(@Path("id") Long id, @Header("Authorization") String authHeader);


    /******** Helper class that sets up a new services *******/
    class Creator {

        public static TvDBService newTvDBService() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TV_BD_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                    .build();
            return retrofit.create(TvDBService.class);
        }
    }
}
