package com.qualityfull.reactivexandroidbyexamples.ui.orchestration.multipleapi;

import com.qualityfull.reactivexandroidbyexamples.data.local.entities.SeriesDetail;
import com.qualityfull.reactivexandroidbyexamples.ui.base.MvpView;

public interface MultipleApiMvpView extends MvpView {

    void showSeriesDetail(SeriesDetail seriesDetail);

    void showLoader(boolean show);

    void showLayoutDetail(boolean show);

    void showThumbnail(String url);

    void showError(String descriptionError);

    void enableShowButton(boolean enable);
}
