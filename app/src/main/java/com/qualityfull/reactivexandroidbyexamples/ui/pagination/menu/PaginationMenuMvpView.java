package com.qualityfull.reactivexandroidbyexamples.ui.pagination.menu;

import com.qualityfull.reactivexandroidbyexamples.ui.base.MvpView;

public interface PaginationMenuMvpView extends MvpView {

    void loadOptions(String[] options);

    void showScreen(Integer option);
}
