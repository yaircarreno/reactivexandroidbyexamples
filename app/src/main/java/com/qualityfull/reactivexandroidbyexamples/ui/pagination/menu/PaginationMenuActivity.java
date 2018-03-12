package com.qualityfull.reactivexandroidbyexamples.ui.pagination.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qualityfull.reactivexandroidbyexamples.R;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BaseActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.infiniteScrolling.PaginationActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.loadMore.PaginationLoadMoreActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.withPublishSubject.PaginationPublishActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.withSwitchMap.PaginationSwitchMapActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaginationMenuActivity extends BaseActivity implements PaginationMenuMvpView {

    @Inject
    PaginationMenuPresenter mPaginationMenuPresenter;

    @BindView(R.id.pagination_menu_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    PaginationMenuAdapter mPaginationMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_pagination_menu);
        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mPaginationMenuAdapter);

        // Attach the View to presenter
        mPaginationMenuPresenter.attachView(this);
        mPaginationMenuPresenter.loadOptions();

        mPaginationMenuPresenter.setOnClickObservable(mPaginationMenuAdapter.getViewClickedObservable());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPaginationMenuPresenter.detachView();
    }

    @Override
    public void loadOptions(String[] options) {
        mPaginationMenuAdapter.setOptions(options);
        mPaginationMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void showScreen(Integer option) {
        switch (option) {
            case 0:
                startActivity(new Intent(PaginationMenuActivity.this, PaginationActivity.class));
                break;
            case 1:
                startActivity(new Intent(PaginationMenuActivity.this, PaginationSwitchMapActivity.class));
                break;
            case 2:
                startActivity(new Intent(PaginationMenuActivity.this, PaginationPublishActivity.class));
                break;

            case 3:
                startActivity(new Intent(PaginationMenuActivity.this, PaginationLoadMoreActivity.class));
                break;
            default:
                startActivity(new Intent(PaginationMenuActivity.this, PaginationActivity.class));
        }
    }
}
