package com.qualityfull.reactivexandroidbyexamples.ui.pagination.infiniteScrolling;

import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Result;
import com.qualityfull.reactivexandroidbyexamples.ui.base.MvpView;

import java.util.List;

interface PaginationMvpView extends MvpView {

    void sendRequestToApi();

    void loadCharacters(List<Result> results);

    boolean totalItemsShowed();

    void showLoader(boolean show);
}
