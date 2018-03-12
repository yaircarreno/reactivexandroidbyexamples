package com.qualityfull.reactivexandroidbyexamples.data;

import android.content.SharedPreferences;
import android.util.Pair;

import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio3.sqlite.queries.Query;
import com.qualityfull.reactivexandroidbyexamples.core.Constants;
import com.qualityfull.reactivexandroidbyexamples.data.local.entities.SeriesDetail;
import com.qualityfull.reactivexandroidbyexamples.data.local.tables.SeriesDetailTable;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.ServiceError;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.Cast;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.Credits;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.IdExternal;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.SeriesResponse;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.Data;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.DataSeries;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.DataSeriesItem;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.Episode;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.Episodes;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.Series;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.Token;
import com.qualityfull.reactivexandroidbyexamples.data.model.util.NetWorkUtils;
import com.qualityfull.reactivexandroidbyexamples.data.remote.error.RetrofitException;
import com.qualityfull.reactivexandroidbyexamples.util.ApiUtil;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;
import timber.log.Timber;

@Singleton
public class RxOperations {

    private final DataManager mDataManager;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    StorIOSQLite storIOSQLite;

    @Inject
    RxOperations(DataManager datamanager) {
        this.mDataManager = datamanager;
    }

    private Single<SeriesResponse> getSeriesFromMovieDB(String apiKey, String name, String page) {
        return mDataManager.getSeries(apiKey, name, page);
    }

    private Single<Credits> getCreditsFromMovieDB(Integer tvId, String apiKey) {
        return mDataManager.getCredits(tvId, apiKey);
    }

    private Single<IdExternal> getIdExternalFromMovieDB(Integer tvId, String apiKey) {
        return mDataManager.getIdExternal(tvId, apiKey);
    }

    private Single<DataSeriesItem> getSeriesFromTvDB(Long id, String token) {
        return mDataManager.getSeries(id, token);
    }

    private Single<Episodes> getEpisodesFromTvDB(Long id, String token) {
        return mDataManager.getEpisodes(id, token);
    }

    public Single<String> getTokenStored() {
        return Single.fromCallable(() -> sharedPreferences.getString(Constants.KEY_TOKEN_JWT_TV_DB, ""))
                .flatMap(this::validateTokenStored);
    }

    private Single<String> getToken(String apikey, String userkey, String username) {
        return mDataManager.getToken(apikey, userkey, username).map(Token::getToken);
    }

    private Completable storeToken(String token) {
        return Completable.fromAction(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.KEY_TOKEN_JWT_TV_DB, token);
            editor.apply();
        });
    }

    private Single<String> validateTokenStored(String tokenStored) {
        if (ApiUtil.isTokenValid(tokenStored)) {
            Timber.i("Request token stored locally");
            return Single.just(tokenStored);
        } else {
            Timber.i("Request token remotely ");
            return getToken(Constants.API_KEY_TV_DB_CODE, Constants.USER_KEY_TV_DB_CODE, Constants.USER_NAME_TV_DB)
                    .flatMap(tokenRemote -> storeToken(tokenRemote).toSingle(() -> tokenRemote));
        }
    }

    public Completable storeLocal(SeriesDetail seriesDetail) {
        return storIOSQLite
                .put()
                .object(seriesDetail)
                .prepare()
                .asRxCompletable();
    }

    public Completable deleteStoredLocal() {
        return storIOSQLite
                .delete()
                .byQuery(DeleteQuery.builder()
                        .table(SeriesDetailTable.TABLE)
                        .build())
                .prepare()
                .asRxCompletable();
    }

    public Single<SeriesDetail> getSeriesLocalStored() {
        return storIOSQLite
                .get()
                .object(SeriesDetail.class)
                .withQuery(Query.builder()
                        .table(SeriesDetailTable.TABLE)
                        .build())
                .prepare()
                .asRxSingle()
                .map(seriesDetailOptional ->
                        seriesDetailOptional.isPresent() ? seriesDetailOptional.get() : null);
    }

    public Single<List<Series>> getSeries(String name, String token) {
        return mDataManager.getSeries(name, token).map(DataSeries::getData);
    }

    public Single<com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.Series> getSeriesMovieDB(String name) {
        return getSeriesFromMovieDB(Constants.API_KEY_CODE, name, Constants.PAGE_NUMBER)
                .filter(seriesResponse -> NetWorkUtils.isListValid(seriesResponse.getResults()))
                .map(seriesResponse -> seriesResponse.getResults().get(0)).toSingle();
    }

    public Single<String> getCastList(Single<com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.Series> seriesMovieDB) {
        return seriesMovieDB
                .flatMap(series -> getCreditsFromMovieDB(series.getId(), Constants.API_KEY_CODE))
                .flattenAsObservable(Credits::getCast)
                .filter(cast -> NetWorkUtils.isValid(cast.getName()))
                .map(Cast::getName)
                .take(10)
                .reduce((name1, name2) -> name1 + ", " + name2)
                .toSingle();
    }

    public Single<Data> getSeriesTvDB(Single<com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.Series> seriesMovieDB) {
        return seriesMovieDB
                .flatMap(series -> getIdExternalFromMovieDB(series.getId(), Constants.API_KEY_CODE))
                .flatMap(idExternal -> getTokenStored().map(token -> new Pair<>(idExternal, token)))
                .flatMap(pair -> getSeriesFromTvDB(pair.first.getTvdbId().longValue(), pair.second))
                .map(DataSeriesItem::getData);
    }

    public Single<String> getEpisodeList(Single<Data> seriesTvDB) {
        return seriesTvDB
                .flatMap(data -> getTokenStored().map(token -> new Pair<>(data, token)))
                .flatMap(pair -> getEpisodesFromTvDB(pair.first.getId().longValue(), pair.second))
                .flattenAsObservable(Episodes::getData)
                .filter(episode -> NetWorkUtils.isValid(episode.getEpisodeName()))
                .map(Episode::getEpisodeName)
                .take(10)
                .reduce((name1, name2) -> name1 + ", " + name2)
                .toSingle();
    }

    public void clearTokenStored() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_TOKEN_JWT_TV_DB, "");
        editor.apply();
    }

    public String getServiceError(Throwable throwable) {

        ServiceError serviceError;
        String descriptionError = "Service error DB API";

        if (throwable instanceof RetrofitException) {

            try {
                serviceError = ((RetrofitException) throwable).getErrorBodyAs(ServiceError.class);

                if (NetWorkUtils.isValid(serviceError.getStatusMessage())) {
                    return serviceError.getStatusMessage();
                } else if (NetWorkUtils.isListValid(serviceError.getErrors())) {
                    return serviceError.getErrors().get(0);
                } else if (NetWorkUtils.isValid(serviceError.getError())) {
                    return serviceError.getError();
                }
            } catch (IOException e) {
                Timber.e(e.getMessage());
                return descriptionError;
            }
        } else {
            Timber.e(throwable.getMessage());
        }
        return descriptionError;
    }
}
