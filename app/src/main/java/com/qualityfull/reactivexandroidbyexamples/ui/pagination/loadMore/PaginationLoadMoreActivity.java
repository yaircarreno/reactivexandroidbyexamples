package com.qualityfull.reactivexandroidbyexamples.ui.pagination.loadMore;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding2.view.RxView;
import com.qualityfull.reactivexandroidbyexamples.R;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Result;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BaseActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.infiniteScrolling.PaginationAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaginationLoadMoreActivity extends BaseActivity implements PaginationLoadMoreMvpView {

    @Inject
    PaginationLoadMorePresenter mPaginationLoadMorePresenter;

    @Inject
    PaginationAdapter mAdapter;

    @BindView(R.id.pagination_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.button_load_more)
    Button mButtonLoadMore;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_pagination_load_more);
        ButterKnife.bind(this);

        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // LinearLayoutManager for the RecycleView
        mLinearLayoutManager = new LinearLayoutManager(this);

        // Use a linear layout manager
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Specify an adapter
        mRecyclerView.setAdapter(mAdapter);

        mPaginationLoadMorePresenter.attachView(this);

        // Create the Observable for scroll events
        mPaginationLoadMorePresenter.rxScrollEvents(RxRecyclerView.scrollEvents(mRecyclerView));

        // Call the Presenter and its method
        mPaginationLoadMorePresenter.loadCharacters();

        // Create the Observable for multiple clicks control
        mPaginationLoadMorePresenter.rxButtonEvents(RxView.clicks(mButtonLoadMore));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPaginationLoadMorePresenter.detachView();
    }

    @Override
    public void loadCharacters(List<Result> results) {
        mAdapter.setOptions(results);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean totalItemsShowed() {
        return mLinearLayoutManager.getChildCount() + mLinearLayoutManager.findFirstVisibleItemPosition()  >= mAdapter.getItemCount();
    }

    @Override
    public void showLoader(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showButtonLoadMore(boolean show) {
        mButtonLoadMore.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
