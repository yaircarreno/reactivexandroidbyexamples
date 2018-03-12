package com.qualityfull.reactivexandroidbyexamples.ui.pagination.loadMore;

import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Result;
import com.qualityfull.reactivexandroidbyexamples.ui.base.MvpView;
import java.util.List;

public interface PaginationLoadMoreMvpView extends MvpView {

    void loadCharacters(List<Result> results);

    boolean totalItemsShowed();

    void showLoader(boolean show);

    void showButtonLoadMore(boolean show);
}
