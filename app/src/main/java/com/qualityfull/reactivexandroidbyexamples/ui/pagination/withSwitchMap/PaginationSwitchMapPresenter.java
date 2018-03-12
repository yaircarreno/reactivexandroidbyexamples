package com.qualityfull.reactivexandroidbyexamples.ui.pagination.withSwitchMap;

import android.annotation.SuppressLint;

import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.qualityfull.reactivexandroidbyexamples.data.DataManager;
import com.qualityfull.reactivexandroidbyexamples.data.model.Pager;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Characters;
import com.qualityfull.reactivexandroidbyexamples.data.model.util.NetWorkUtils;
import com.qualityfull.reactivexandroidbyexamples.injection.scope.ConfigPersistent;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BasePresenter;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@ConfigPersistent
public class PaginationSwitchMapPresenter extends BasePresenter<PaginationSwitchMapMvpView> {

    private final DataManager mDataManager;
    private CompositeDisposable mCompositeDisposable;
    private Pager pagerModel;

    @Inject
    PaginationSwitchMapPresenter(DataManager dataManager, Pager pager) {
        mDataManager = dataManager;
        pagerModel = pager;
    }

    @Override
    public void attachView(PaginationSwitchMapMvpView mvpView) {
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

    /**
     * Management scroll events how component Rx.
     *
     * @param observable : Observable from RecycleView component.
     */
    @SuppressLint("RxDefaultScheduler")
    void rxScrollEvents(Observable<RecyclerViewScrollEvent> observable) {
        checkViewAttached();
        mCompositeDisposable.add(
                observable
                        .filter(s -> getMvpView().totalItemsShowed())
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .switchMap(scrollEvent -> sendRequestToApiObservable()
                                .subscribeOn(Schedulers.io()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(character -> {
                            pagerModel.updateItemList(character.getData().getResults());
                            getMvpView().loadCharacters(pagerModel.getItemList());
                            getMvpView().showLoader(false);
                        }, throwable -> {
                            Timber.e(throwable);
                            getMvpView().showLoader(false);
                        }));
    }

    /**
     * Observable to load data from API.
     */
    private Observable<Characters> sendRequestToApiObservable() {
        getMvpView().showLoader(true);
        return mDataManager.getCharacters(Pager.LIMIT, this.pagerModel.getOffset())
                .filter(NetWorkUtils::isDataResponseValid);
    }

    /**
     * Load the characters from the API.
     */
    void loadCharacters() {
        checkViewAttached();
        mCompositeDisposable.add(
                sendRequestToApiObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(character -> {
                            pagerModel.updateItemList(character.getData().getResults());
                            getMvpView().loadCharacters(pagerModel.getItemList());
                            getMvpView().showLoader(false);
                        }, throwable -> {
                            Timber.e(throwable);
                            getMvpView().showLoader(false);
                        }));
    }
}
