package com.qualityfull.reactivexandroidbyexamples.ui.pagination.loadMore;

import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.qualityfull.reactivexandroidbyexamples.data.DataManager;
import com.qualityfull.reactivexandroidbyexamples.data.model.Pager;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Characters;
import com.qualityfull.reactivexandroidbyexamples.data.model.util.NetWorkUtils;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BasePresenter;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

public class PaginationLoadMorePresenter extends BasePresenter<PaginationLoadMoreMvpView> {

    private final DataManager mDataManager;
    private CompositeDisposable mCompositeDisposable;
    private Pager pagerModel;
    private Subject<String> pagerSubject;

    @Inject
    PaginationLoadMorePresenter(DataManager dataManager, Pager pager) {
        mDataManager = dataManager;
        pagerModel = pager;
    }

    @Override
    public void attachView(PaginationLoadMoreMvpView mvpView) {
        super.attachView(mvpView);
        mCompositeDisposable = new CompositeDisposable();
        pagerSubject = PublishSubject.create();

        mCompositeDisposable.add(
                pagerSubject.concatMap(s -> sendRequestToApiObservable(s).subscribeOn(Schedulers.io()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(character -> {
                            pagerModel.updateItemList(character.getData().getResults());
                            getMvpView().loadCharacters(pagerModel.getItemList());
                            getMvpView().showLoader(false);
                            getMvpView().showButtonLoadMore(false);
                        }, throwable -> {
                            Timber.e(throwable);
                            getMvpView().showLoader(false);
                        }));
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
                        .subscribe(scrollEvent -> getMvpView().showButtonLoadMore(true), Timber::e));
    }


    /**
     * Management click events how component Rx.
     * Also avoid multiple clicks.
     *
     * @param clicks : Observable from Button component.
     */
    void rxButtonEvents(Observable<Object> clicks) {
        checkViewAttached();
        mCompositeDisposable.add(
                clicks
                        .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .subscribe(s -> loadCharacters(), Timber::e));
    }

    /**
     * Load the characters from the API.
     */
    void loadCharacters() {
        checkViewAttached();
        getMvpView().showLoader(true);
        pagerSubject.onNext(this.pagerModel.getOffset());
    }

    private Observable<Characters> sendRequestToApiObservable(String offset) {
        return mDataManager.getCharacters(Pager.LIMIT, offset)
                .filter(NetWorkUtils::isDataResponseValid);
    }
}
