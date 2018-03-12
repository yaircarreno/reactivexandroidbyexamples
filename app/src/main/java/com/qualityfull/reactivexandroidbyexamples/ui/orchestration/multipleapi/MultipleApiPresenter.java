package com.qualityfull.reactivexandroidbyexamples.ui.orchestration.multipleapi;

import com.qualityfull.reactivexandroidbyexamples.core.Constants;
import com.qualityfull.reactivexandroidbyexamples.data.RxOperations;
import com.qualityfull.reactivexandroidbyexamples.data.local.entities.SeriesDetail;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.Series;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.Data;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BasePresenter;
import javax.inject.Inject;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MultipleApiPresenter extends BasePresenter<MultipleApiMvpView> {

    private final RxOperations rxOperations;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    MultipleApiPresenter(RxOperations rxOperations) {
        this.rxOperations = rxOperations;
    }

    @Override
    public void attachView(MultipleApiMvpView mvpView) {
        super.attachView(mvpView);
        mCompositeDisposable = new CompositeDisposable();

    }

    @Override
    public void detachView() {
        super.detachView();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    void showDetailSeries(String name) {

        checkViewAttached();
        getMvpView().showLoader(true);
        getMvpView().showLayoutDetail(false);
        getMvpView().enableShowButton(false);

        Single<Series> seriesMovieDB = rxOperations.getSeriesMovieDB(name);
        Single<Data> seriesTvDB = rxOperations.getSeriesTvDB(seriesMovieDB);

        mCompositeDisposable.add(
                Single.zip(seriesMovieDB,
                        rxOperations.getCastList(seriesMovieDB),
                        seriesTvDB,
                        rxOperations.getEpisodeList(seriesTvDB),
                        (movies, characters, tvs, episodes) ->
                                SeriesDetail.newSeriesDetail(movies.getId(), tvs.getId(), movies.getName(),
                                        movies.getOverview(), movies.getPosterPath(), tvs.getSiteRating(),
                                        tvs.getStatus(), characters, episodes))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(seriesDetail -> {
                            getMvpView().showSeriesDetail(seriesDetail);
                            getMvpView().showLoader(false);
                            getMvpView().enableShowButton(true);
                            getMvpView().showLayoutDetail(true);
                            getMvpView().showThumbnail(Constants.BASE_URL_IMG_MOVIE_DB + seriesDetail.getPoster_path());
                        }, throwable -> {
                            getMvpView().showError(rxOperations.getServiceError(throwable));
                            getMvpView().showLoader(false);
                            getMvpView().enableShowButton(true);
                        }));
    }
}
