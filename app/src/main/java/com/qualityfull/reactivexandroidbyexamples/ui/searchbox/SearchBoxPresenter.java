package com.qualityfull.reactivexandroidbyexamples.ui.searchbox;

import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.qualityfull.reactivexandroidbyexamples.data.DataManager;
import com.qualityfull.reactivexandroidbyexamples.data.model.Pager;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Characters;
import com.qualityfull.reactivexandroidbyexamples.data.model.util.NetWorkUtils;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BasePresenter;
import com.qualityfull.reactivexandroidbyexamples.util.ViewUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

public class SearchBoxPresenter extends BasePresenter<SearchBoxMvpView> {

    private final DataManager mDataManager;
    private CompositeDisposable mCompositeDisposable;
    private Subject<Pager> pagerSubject;
    private Pager pagerModel;

    @Inject
    SearchBoxPresenter(DataManager dataManager, Pager pager) {
        mDataManager = dataManager;
        pagerModel = pager;
    }

    @Override
    public void attachView(SearchBoxMvpView mvpView) {
        super.attachView(mvpView);
        mCompositeDisposable = new CompositeDisposable();
        pagerSubject = PublishSubject.create();

        mCompositeDisposable.add(
                pagerSubject.switchMap(pager -> sendRequestToApiObservable(pager)
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
                        .subscribe(bottomReached -> {
                            getMvpView().showLoader(true);
                            this.loadCharacters();
                        }, throwable -> {
                            getMvpView().showLoader(false);
                            Timber.e(throwable);
                        }));
    }


    /**
     * Management search events how component Rx.
     *
     * @param observable : Observable from SearchView component.
     */
    void rxSearchBoxEvent(Observable<CharSequence> observable) {
        checkViewAttached();
        mCompositeDisposable.add(
                observable
                        .filter(ViewUtil::isTextValid)
                        .throttleLast(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .map(charSequence -> {
                            getMvpView().showLoader(true);
                            return this.pagerModel = new Pager(charSequence.toString());
                        })
                        .switchMap(pager -> sendRequestToApiObservable(pager)
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
     * Load the characters from the API.
     */
    void loadCharacters() {
        checkViewAttached();
        pagerSubject.onNext(pagerModel);
    }

    /**
     * Send request to API. Validate if query is empty or not.
     * If query is empty, the request all items of the first page.
     */
    private Observable<Characters> sendRequestToApiObservable(Pager pagerModel) {
        return mDataManager.getCharacters(pagerModel)
                .filter(NetWorkUtils::isDataResponseValid);
    }
}
