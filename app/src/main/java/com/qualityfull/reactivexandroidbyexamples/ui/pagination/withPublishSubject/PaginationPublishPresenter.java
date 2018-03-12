package com.qualityfull.reactivexandroidbyexamples.ui.pagination.withPublishSubject;

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
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

@ConfigPersistent
public class PaginationPublishPresenter extends BasePresenter<PaginationPublishMvpView> {

    private final DataManager mDataManager;
    private final Pager pagerModel;
    private CompositeDisposable mCompositeDisposable;
    private Subject<String> pagerSubject;

    @Inject
    PaginationPublishPresenter(DataManager dataManager, Pager pager) {
        mDataManager = dataManager;
        pagerModel = pager;
    }

    @Override
    public void attachView(PaginationPublishMvpView mvpView) {
        super.attachView(mvpView);

        // Initializes variables
        mCompositeDisposable = new CompositeDisposable();
        pagerSubject = PublishSubject.create();

        // PublishSubject using switchMap to invoke services to the API.
        mCompositeDisposable.add(
                pagerSubject.switchMap(s -> sendRequestToApiObservable(s)
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
     * Observable to load data from API.
     */
    private Observable<Characters> sendRequestToApiObservable(String page) {
        return mDataManager.getCharacters(Pager.LIMIT, page)
                .filter(NetWorkUtils::isDataResponseValid);
    }

    /**
     * Load the characters from the API.
     */
    void loadCharacters() {
        checkViewAttached();
        pagerSubject.onNext(this.pagerModel.getOffset());
    }
}
