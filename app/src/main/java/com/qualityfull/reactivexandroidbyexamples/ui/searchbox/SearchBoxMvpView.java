package com.qualityfull.reactivexandroidbyexamples.ui.searchbox;

import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Result;
import com.qualityfull.reactivexandroidbyexamples.ui.base.MvpView;
import java.util.List;

public interface SearchBoxMvpView extends MvpView {

    void loadCharacters(List<Result> results);

    boolean totalItemsShowed();

    void showLoader(boolean show);
}
