package com.qualityfull.reactivexandroidbyexamples.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.qualityfull.reactivexandroidbyexamples.R;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BaseActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.orchestration.menu.OrchestrationMenuActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.menu.PaginationMenuActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.searchbox.SearchBoxActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.validationforms.ValidationFormsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements HomeMvpView {

    @Inject
    HomePresenter mHomePresenter;

    @BindView(R.id.home_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Specify an adapter (see also next example)
        mRecyclerView.setAdapter(mAdapter);

        // Attach the View to presenter
        mHomePresenter.attachView(this);

        // Call the Presenter and its method
        mHomePresenter.loadOptions();

        mHomePresenter.setOnClickObservable(mAdapter.getViewClickedObservable());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomePresenter.detachView();
    }

    @Override
    public void loadOptions(String[] options) {
        mAdapter.setOptions(options);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showScreen(Integer option) {
        switch (option) {
            case 0:
                startActivity(new Intent(HomeActivity.this, PaginationMenuActivity.class));
                break;
            case 1:
                startActivity(new Intent(HomeActivity.this, SearchBoxActivity.class));
                break;
            case 2:
                startActivity(new Intent(HomeActivity.this, ValidationFormsActivity.class));
                break;
            case 3:
                startActivity(new Intent(HomeActivity.this, OrchestrationMenuActivity.class));
                break;
            default:
                startActivity(new Intent(HomeActivity.this, PaginationMenuActivity.class));
        }
    }
}
