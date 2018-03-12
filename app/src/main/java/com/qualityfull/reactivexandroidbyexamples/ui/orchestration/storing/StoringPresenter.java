package com.qualityfull.reactivexandroidbyexamples.ui.orchestration.storing;

import com.qualityfull.reactivexandroidbyexamples.core.Constants;
import com.qualityfull.reactivexandroidbyexamples.data.RxOperations;
import com.qualityfull.reactivexandroidbyexamples.data.local.entities.SeriesDetail;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.moviedb.Series;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.tvdb.Data;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BasePresenter;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.qualityfull.reactivexandroidbyexamples.util.ObservableLoggerTransformer.debugLog;

public class StoringPresenter extends BasePresenter<StoringMvpView> {

    private final RxOperations rxOperations;
    private CompositeDisposable mCompositeDisposable;


    @Inject
    StoringPresenter(RxOperations rxOperations) {
        this.rxOperations = rxOperations;
    }

    @Override
    public void attachView(StoringMvpView mvpView) {
        super.attachView(mvpView);
        mCompositeDisposable = new CompositeDisposable();

        mCompositeDisposable.add(
                getSeriesLocalStored()
                        .flatMap(seriesDetail -> getSeriesDetailRemote(seriesDetail.getName()))
                        .publish(network ->
                                Observable.merge(
                                        network,
                                        getSeriesLocalStored().takeUntil(network)))
                        .flatMapSingle(this::deleteAndSaveLocally)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(seriesDetail -> {
                            getMvpView().showSeriesDetail(seriesDetail);
                            getMvpView().showLayoutDetail(true);
                            getMvpView().showThumbnail(Constants.BASE_URL_IMG_MOVIE_DB + seriesDetail.getPoster_path());
                        }, throwable -> {
                            getMvpView().showError(rxOperations.getServiceError(throwable));
                        }));
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

        mCompositeDisposable.add(
                getSeriesDetailRemote(name)
                        .compose(debugLog("afterGetSeriesDetailRemote"))
                        .flatMapSingle(this::deleteAndSaveLocally)
                        .compose(debugLog("afterDeleteAndSaveLocally"))
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

    private Observable<SeriesDetail> getSeriesLocalStored() {
        return rxOperations.getSeriesLocalStored().toObservable();
    }

    private Observable<SeriesDetail> getSeriesDetailRemote(String name) {

        Single<Series> seriesMovieDB = rxOperations.getSeriesMovieDB(name);
        Single<Data> seriesTvDB = rxOperations.getSeriesTvDB(seriesMovieDB);

        return Single.zip(seriesMovieDB,
                rxOperations.getCastList(seriesMovieDB),
                seriesTvDB,
                rxOperations.getEpisodeList(seriesTvDB),
                (movies, characters, tvs, episodes) ->
                        SeriesDetail.newSeriesDetail(movies.getId(), tvs.getId(), movies.getName(),
                                movies.getOverview(), movies.getPosterPath(), tvs.getSiteRating(),
                                tvs.getStatus(), characters, episodes)).toObservable();
    }

    private Single<SeriesDetail> deleteAndSaveLocally(SeriesDetail seriesDetail) {
        return rxOperations.deleteStoredLocal()
                .concatWith(rxOperations.storeLocal(seriesDetail))
                .toSingle(() -> seriesDetail);
    }
}
