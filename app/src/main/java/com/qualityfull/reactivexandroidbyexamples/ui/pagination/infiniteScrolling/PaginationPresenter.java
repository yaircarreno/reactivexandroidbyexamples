package com.qualityfull.reactivexandroidbyexamples.ui.pagination.infiniteScrolling;

import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.qualityfull.reactivexandroidbyexamples.data.DataManager;
import com.qualityfull.reactivexandroidbyexamples.data.model.Pager;
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
public class PaginationPresenter extends BasePresenter<PaginationMvpView> {

    private final DataManager mDataManager;
    private final Pager pagerModel;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    PaginationPresenter(DataManager dataManager, Pager pager) {
        mDataManager = dataManager;
        pagerModel = pager;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(PaginationMvpView mvpView) {
        super.attachView(mvpView);
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
    void rxScrollEvents(Observable<RecyclerViewScrollEvent> observable) {
        checkViewAttached();


        mCompositeDisposable.add(
                observable
                        .filter(s -> getMvpView().totalItemsShowed())
                        .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .subscribe(scrollEvent -> {
                            getMvpView().showLoader(true);
                            getMvpView().sendRequestToApi();
                        }, Timber::e));
    }


    /**
     * Load the characters from the API.
     */
    void loadCharacters() {
        checkViewAttached();
        mCompositeDisposable.add(
                mDataManager.getCharacters(Pager.LIMIT, this.pagerModel.getOffset())
                        .filter(NetWorkUtils::isDataResponseValid)
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
