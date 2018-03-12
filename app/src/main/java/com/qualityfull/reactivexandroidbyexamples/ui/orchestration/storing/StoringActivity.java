package com.qualityfull.reactivexandroidbyexamples.ui.orchestration.storing;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qualityfull.reactivexandroidbyexamples.R;
import com.qualityfull.reactivexandroidbyexamples.data.local.entities.SeriesDetail;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BaseActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoringActivity extends BaseActivity implements StoringMvpView {

    @Inject
    StoringPresenter storingPresenter;

    @BindView(R.id.full_name_series)
    EditText editTextFullSeriesName;

    @BindView(R.id.thumbnail_imageView)
    ImageView thumbnailImageView;

    @BindView(R.id.title_textView)
    TextView titleTextView;

    @BindView(R.id.overview_textView)
    TextView overviewTextView;

    @BindView(R.id.number_rating_textView)
    TextView numberRatingTextView;

    @BindView(R.id.name_status_textView)
    TextView nameStatusTextView;

    @BindView(R.id.character_list_textView)
    TextView characterListTextView;

    @BindView(R.id.episode_list_textView)
    TextView episodeListTextView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.layout_general_series_detail)
    RelativeLayout layoutGeneralSeriesDetail;

    @BindView(R.id.label_errors)
    TextView textLabelErrors;

    @BindView(R.id.show_detail)
    Button showButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_storing);
        ButterKnife.bind(this);

        storingPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        storingPresenter.detachView();
    }

    @OnClick(R.id.show_detail)
    public void showDetail() {
        storingPresenter.showDetailSeries(editTextFullSeriesName.getText().toString());
    }

    @Override
    public void showSeriesDetail(SeriesDetail seriesDetail) {
        titleTextView.setText(seriesDetail.getName());
        overviewTextView.setText(seriesDetail.getOverview());
        numberRatingTextView.setText(String.valueOf(seriesDetail.getSiteRating()));
        nameStatusTextView.setText(seriesDetail.getStatus());
        characterListTextView.setText(seriesDetail.getCharacters());
        episodeListTextView.setText(seriesDetail.getEpisodes());
    }

    @Override
    public void showLoader(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showLayoutDetail(boolean show) {
        layoutGeneralSeriesDetail.setVisibility(show? View.VISIBLE : View.INVISIBLE);
        textLabelErrors.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showThumbnail(String url) {
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.item_placeholder)
                .resize(100, 150)
                .into(thumbnailImageView);
    }

    @Override
    public void showError(String descriptionError) {
        textLabelErrors.setVisibility(View.VISIBLE);
        textLabelErrors.setText(descriptionError);
    }

    @Override
    public void enableShowButton(boolean enable) {
        showButton.setEnabled(enable);
    }
}
