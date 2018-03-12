package com.qualityfull.reactivexandroidbyexamples.ui.pagination.withPublishSubject;

import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Result;
import com.qualityfull.reactivexandroidbyexamples.ui.base.MvpView;

import java.util.List;

public interface PaginationPublishMvpView extends MvpView {

    void loadCharacters(List<Result> results);

    boolean totalItemsShowed();

    void showLoader(boolean show);
}
