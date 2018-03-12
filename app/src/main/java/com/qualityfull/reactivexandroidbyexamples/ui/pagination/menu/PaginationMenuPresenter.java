package com.qualityfull.reactivexandroidbyexamples.ui.pagination.menu;


import com.qualityfull.reactivexandroidbyexamples.core.Constants;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PaginationMenuPresenter extends BasePresenter<PaginationMenuMvpView> {

    private CompositeDisposable mCompositeDisposable;

    @Inject
    PaginationMenuPresenter() {
    }

    @Override
    public void attachView(PaginationMenuMvpView mvpView) {
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

    void loadOptions() {
        getMvpView().loadOptions(Constants.PAGINATION_OPTIONS);
    }

    void setOnClickObservable(Observable<Integer> observable) {
        mCompositeDisposable.add(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(position -> getMvpView().showScreen(position), Timber::e));
    }
}
