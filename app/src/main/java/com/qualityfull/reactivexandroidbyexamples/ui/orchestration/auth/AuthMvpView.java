package com.qualityfull.reactivexandroidbyexamples.ui.orchestration.auth;

import com.qualityfull.reactivexandroidbyexamples.ui.base.MvpView;

public interface AuthMvpView extends MvpView {

    void showResults(int size);

    void showError(String descriptionError);
}