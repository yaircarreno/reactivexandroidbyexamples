package com.qualityfull.reactivexandroidbyexamples.ui.searchbox;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.qualityfull.reactivexandroidbyexamples.R;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Result;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BaseActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.infiniteScrolling.PaginationAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchBoxActivity extends BaseActivity implements SearchBoxMvpView {

    @Inject
    SearchBoxPresenter mSearchBoxPresenter;

    @Inject
    PaginationAdapter mAdapter;

    @BindView(R.id.pagination_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.search_box)
    SearchView mSearchView;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_search_box);
        ButterKnife.bind(this);

        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // LinearLayoutManager for the RecycleView
        mLinearLayoutManager = new LinearLayoutManager(this);

        // Use a linear layout manager
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Specify an adapter
        mRecyclerView.setAdapter(mAdapter);

        mSearchBoxPresenter.attachView(this);
        // Call the Presenter and its method
        mSearchBoxPresenter.loadCharacters();

        // Create the Observable for scroll events
        mSearchBoxPresenter.rxScrollEvents(RxRecyclerView.scrollEvents(mRecyclerView));

        // Create the Observable for search view box
        mSearchBoxPresenter.rxSearchBoxEvent(RxSearchView.queryTextChanges(mSearchView));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchBoxPresenter.detachView();
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
