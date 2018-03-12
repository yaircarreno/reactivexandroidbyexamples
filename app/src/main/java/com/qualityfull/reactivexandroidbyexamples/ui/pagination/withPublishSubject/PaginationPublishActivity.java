package com.qualityfull.reactivexandroidbyexamples.ui.pagination.withPublishSubject;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.qualityfull.reactivexandroidbyexamples.R;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Result;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BaseActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.infiniteScrolling.PaginationAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaginationPublishActivity extends BaseActivity implements PaginationPublishMvpView {

    @Inject
    PaginationPublishPresenter mPaginationPublishPresenter;

    @Inject
    PaginationAdapter mAdapter;

    @BindView(R.id.pagination_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_pagination_publish);
        ButterKnife.bind(this);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // LinearLayoutManager for the RecycleView
        mLinearLayoutManager = new LinearLayoutManager(this);

        // Use a linear layout manager
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Specify an adapter
        mRecyclerView.setAdapter(mAdapter);

        mPaginationPublishPresenter.attachView(this);

        // Create the Observable for scroll events
        mPaginationPublishPresenter.rxScrollEvents(RxRecyclerView.scrollEvents(mRecyclerView));

        // Call the Presenter and its method
        mPaginationPublishPresenter.loadCharacters();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPaginationPublishPresenter.detachView();
    }

    @Override
    public void loadCharacters(List<Result> results) {
        mAdapter.setOptions(results);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean totalItemsShowed() {
        return mLinearLayoutManager.getChildCount() + mLinearLayoutManager.findFirstVisibleItemPosition() >= mAdapter.getItemCount();
    }

    @Override
    public void showLoader(boolean show) {
        mProgressBar.setVisibility(show && !mProgressBar.isShown() ? View.VISIBLE : View.GONE);
    }
}
