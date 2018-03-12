package com.qualityfull.reactivexandroidbyexamples.ui.validationforms;

import com.qualityfull.reactivexandroidbyexamples.ui.base.BasePresenter;
import com.qualityfull.reactivexandroidbyexamples.util.Mask;
import com.qualityfull.reactivexandroidbyexamples.util.ViewUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ValidationFormsPresenter extends BasePresenter<ValidationFormsMvpView> {

    private static final int MAX_SIZE = 10;
    private static final int MIN_SIZE = 2;
    private static final String MASK_DATE = "##/##/####";
    private static final String MASK_PHONE = "(###)###-####";

    private CompositeDisposable mCompositeDisposable;

    @Inject
    ValidationFormsPresenter() {
    }

    @Override
    public void attachView(ValidationFormsMvpView mvpView) {
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
     * Rx validations over form and input data.
     **/
    void validateFormFields(Observable<CharSequence> fullNameObs,
                            Observable<CharSequence> emailObs,
                            Observable<CharSequence> passwordObs,
                            Observable<CharSequence> dateBirthObs,
                            Observable<CharSequence> phoneObs) {
        checkViewAttached();

        //Validate first name field: Not empty and between minimum and maximum value.
        Observable<Boolean> obs1 = fullNameObs.map(charSequence -> ViewUtil.isFieldValid(charSequence, MIN_SIZE, MAX_SIZE));

        //Validate email field: Complies with the email regex
        Observable<Boolean> obs2 = emailObs.map(ViewUtil::isEmailValid);

        //Validate password field: Complies with the password regex
        Observable<Boolean> obs3 = passwordObs.map(ViewUtil::isPasswordValid);

        //Validate date of birth field: Complies with the format dd/mm/yyyy
        Observable<Boolean> obs4 = dateBirthObs
                .flatMap(charSequence -> Mask.validateMask(charSequence.toString(), MASK_DATE)
                        .subscribeOn(Schedulers.computation()))
                .distinctUntilChanged();

        //Observer to enable/disable button
        mCompositeDisposable.add(Observable.combineLatest(
                obs1, obs2, obs3, obs4,
                (fullName, email, pass, db) -> fullName && email && pass && db)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(valid -> getMvpView().setEnabledButton(valid), Timber::e));

        //Register the observables to manage the error messages over each fields.
        this.manageMessagesError(fullNameObs, emailObs, passwordObs, dateBirthObs, phoneObs);
    }

    /**
     * Apply mask over TextView.
     */
    void applyMaskTextView(Observable<CharSequence> dateBirthObs, Observable<CharSequence> phoneObs) {

        mCompositeDisposable.add(dateBirthObs
                .filter(ViewUtil::isTextValid)
                .flatMap(charSequence -> Mask.applyMask(charSequence.toString(), MASK_DATE)
                        .subscribeOn(Schedulers.computation()))
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(masked -> getMvpView().setMaskDate(masked), Timber::e));

        mCompositeDisposable.add(phoneObs
                .filter(ViewUtil::isTextValid)
                .flatMap(charSequence -> Mask.applyMask(charSequence.toString(), MASK_PHONE)
                        .subscribeOn(Schedulers.computation()))
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(masked -> getMvpView().setMaskPhone(masked), Timber::e));
    }


    /**
     * Manages error messages over each field.
     */
    private void manageMessagesError(Observable<CharSequence> fullNameObs,
                                     Observable<CharSequence> emailObs,
                                     Observable<CharSequence> passwordObs,
                                     Observable<CharSequence> dateBirthObs,
                                     Observable<CharSequence> phoneObs) {

        //Observable for message full-name label.
        //In this case, it's necessary to use map instead filter.
        mCompositeDisposable.add(fullNameObs
                .map(charSequence -> ViewUtil.isFieldValidOrEmpty(charSequence, MIN_SIZE, MAX_SIZE))
                .subscribe(valid -> getMvpView().hideErrorEnabledFullName(valid, MIN_SIZE, MAX_SIZE), Timber::e));

        //Observable for message email label
        //In this case, it's necessary to use map instead filter.
        mCompositeDisposable.add(emailObs
                .map(ViewUtil::isEmailFieldValidOrEmpty)
                .subscribe(valid -> getMvpView().hideErrorEnabledEmail(valid), Timber::e));

        //Observable for message password label
        //In this case, it's necessary to use map instead filter.
        mCompositeDisposable.add(passwordObs
                .map(ViewUtil::isPasswordFieldValidOrEmpty)
                .subscribe(valid -> getMvpView().hideErrorEnabledPassword(valid), Timber::e));

        //Observable for message date of birth label
        mCompositeDisposable.add(dateBirthObs
                .filter(ViewUtil::isTextValid)
                .flatMap(charSequence -> Mask.validateMask(charSequence.toString(), MASK_DATE)
                        .subscribeOn(Schedulers.computation()))
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(valid -> getMvpView().hideErrorEnabledDateBirth(valid), Timber::e));

        //Observable for message phone label
        mCompositeDisposable.add(phoneObs
                .filter(ViewUtil::isTextValid)
                .flatMap(charSequence -> Mask.validateMask(charSequence.toString(), MASK_PHONE)
                        .subscribeOn(Schedulers.computation()))
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(valid -> getMvpView().hideErrorEnabledPhone(valid), Timber::e));
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
                        .subscribe(s -> getMvpView().register(), Timber::e));
    }
}
