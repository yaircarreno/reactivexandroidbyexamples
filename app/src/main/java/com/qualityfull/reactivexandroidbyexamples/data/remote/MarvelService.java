package com.qualityfull.reactivexandroidbyexamples.data.remote;

import com.qualityfull.reactivexandroidbyexamples.core.Constants;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Characters;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelService {

    @GET("v1/public/characters")
    Observable<Characters> getCharacters(@Query("apikey") String apikey, @Query("ts") String ts, @Query("hash") String hash,
                                         @Query("limit") String limit, @Query("offset") String offset);

    @GET("v1/public/characters")
    Observable<Characters> getCharacters(@Query("apikey") String apikey, @Query("ts") String ts, @Query("hash") String hash,
                                         @Query("limit") String limit, @Query("offset") String offset, @Query("nameStartsWith") String name);

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static MarvelService newMarvelService() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.MARVEL_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(MarvelService.class);
        }
    }
}
