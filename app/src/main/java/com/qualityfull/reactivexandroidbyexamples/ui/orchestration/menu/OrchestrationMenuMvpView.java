package com.qualityfull.reactivexandroidbyexamples.ui.orchestration.menu;

import com.qualityfull.reactivexandroidbyexamples.ui.base.MvpView;

public interface OrchestrationMenuMvpView extends MvpView {

    void loadOptions(String[] options);

    void showScreen(Integer option);
}
