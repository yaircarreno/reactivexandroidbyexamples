package com.qualityfull.reactivexandroidbyexamples.ui.home;

import com.qualityfull.reactivexandroidbyexamples.ui.base.MvpView;

public interface HomeMvpView extends MvpView {

    void loadOptions(String[] options);

    void showScreen(Integer option);
}
