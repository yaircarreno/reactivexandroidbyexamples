package com.qualityfull.reactivexandroidbyexamples.ui.home;

import com.qualityfull.reactivexandroidbyexamples.core.Constants;
import com.qualityfull.reactivexandroidbyexamples.injection.scope.ConfigPersistent;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@ConfigPersistent
public class HomePresenter extends BasePresenter<HomeMvpView> {

    private CompositeDisposable mCompositeDisposable;

    @Inject
    HomePresenter() {
    }

    @Override
    public void attachView(HomeMvpView mvpView) {
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
        getMvpView().loadOptions(Constants.HOME_OPTIONS);
    }

    void setOnClickObservable(Observable<Integer> observable) {

        mCompositeDisposable.add(observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(position -> getMvpView().showScreen(position), Timber::e));
    }
}
