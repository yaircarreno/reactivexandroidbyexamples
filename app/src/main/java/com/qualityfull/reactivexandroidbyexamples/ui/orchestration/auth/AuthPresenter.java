package com.qualityfull.reactivexandroidbyexamples.ui.orchestration.auth;

import com.qualityfull.reactivexandroidbyexamples.data.RxOperations;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AuthPresenter extends BasePresenter<AuthMvpView> {

    private final RxOperations rxOperations;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    AuthPresenter(RxOperations rxOperations) {
        this.rxOperations = rxOperations;
    }

    @Override
    public void attachView(AuthMvpView mvpView) {
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

    void showSeries(String name) {
        checkViewAttached();
        mCompositeDisposable.add(
                rxOperations.getTokenStored()
                        .flatMap(token -> rxOperations.getSeries(name, token))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> {
                            Timber.i("List items received " + list.size());
                            getMvpView().showResults(list.size());
                        }, throwable -> getMvpView().showError(rxOperations.getServiceError(throwable))));
    }

    void clearTokenStored() {
        rxOperations.clearTokenStored();
    }
}
