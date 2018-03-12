package com.qualityfull.reactivexandroidbyexamples.data;

import com.qualityfull.reactivexandroidbyexamples.core.Constants;
import com.qualityfull.reactivexandroidbyexamples.data.model.Pager;
import com.qualityfull.reactivexandroidbyexamples.data.model.request.tvdb.Client;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Characters;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.Credits;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.IdExternal;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.SeriesResponse;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.DataSeries;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.DataSeriesItem;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.Episodes;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.Token;
import com.qualityfull.reactivexandroidbyexamples.data.remote.MarvelService;
import com.qualityfull.reactivexandroidbyexamples.data.remote.MovieDBService;
import com.qualityfull.reactivexandroidbyexamples.data.remote.TvDBService;
import com.qualityfull.reactivexandroidbyexamples.util.ApiUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import timber.log.Timber;

@Singleton
public class DataManager {

    private final MarvelService mMarvelService;
    private final TvDBService mTvDBService;
    private final MovieDBService mMovieDBService;
    private final String HASH;

    @Inject
    public DataManager(MarvelService marvelService, TvDBService tvDBService, MovieDBService movieDBService) {
        this.mMarvelService = marvelService;
        this.mTvDBService = tvDBService;
        this.mMovieDBService = movieDBService;
        this.HASH = ApiUtil.convertPassMd5(Constants.TS + Constants.PRIVATE_KEY_MARVEL + Constants.PUBLIC_KEY_MARVEL);
    }

    public Observable<Characters> getCharacters(Pager pager) {
        if (ApiUtil.isWithQuery(pager.getQuery())) {
            return this.getCharacters(Pager.LIMIT, pager.getOffset(), pager.getQuery());
        } else {
            return this.getCharacters(Pager.LIMIT, pager.getOffset());
        }
    }

    public Observable<Characters> getCharacters(String limit, String offset) {
        Timber.i("Execute getCharacters " + offset);
        return this.mMarvelService.getCharacters(Constants.PUBLIC_KEY_MARVEL, Constants.TS, HASH, limit, offset);
    }

    private Observable<Characters> getCharacters(String limit, String offset, String name) {
        Timber.i("Execute getCharacters " + offset);
        return this.mMarvelService.getCharacters(Constants.PUBLIC_KEY_MARVEL, Constants.TS, HASH, limit, offset, name);
    }

    public Single<Token> getToken(String apikey, String userkey, String username) {
        return this.mTvDBService.getToken(new Client(apikey, userkey, username));
    }

    public Single<DataSeries> getSeries(String name, String token) {
        return this.mTvDBService.getSeries(name, "Bearer " + token);
    }

    public Single<DataSeriesItem> getSeries(Long id, String token) {
        return this.mTvDBService.getSeries(id, "Bearer " + token);
    }

    public Single<Episodes> getEpisodes(Long id, String token) {
        return this.mTvDBService.getEpisodes(id, "Bearer " + token);
    }

    public Single<SeriesResponse> getSeries(String apiKey, String name, String page) {
        return this.mMovieDBService.getSeries(apiKey, name, page);
    }

    public Single<Credits> getCredits(Integer tvId, String apiKey) {
        return this.mMovieDBService.getCredits(tvId, apiKey);
    }

    public Single<IdExternal> getIdExternal(Integer tvId, String apiKey) {
        return this.mMovieDBService.getIdExternal(tvId, apiKey);
    }
}