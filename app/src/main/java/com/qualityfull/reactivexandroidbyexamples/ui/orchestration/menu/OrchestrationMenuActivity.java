package com.qualityfull.reactivexandroidbyexamples.ui.orchestration.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.qualityfull.reactivexandroidbyexamples.R;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BaseActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.orchestration.auth.AuthActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.orchestration.storing.StoringActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.orchestration.multipleapi.MultipleApiActivity;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrchestrationMenuActivity extends BaseActivity implements OrchestrationMenuMvpView {

    @Inject
    OrchestrationMenuPresenter orchestrationMenuPresenter;

    @BindView(R.id.orchestration_menu_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    OrchestrationMenuAdapter orchestrationMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_orchestration_menu);
        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(orchestrationMenuAdapter);

        // Attach the View to presenter
        orchestrationMenuPresenter.attachView(this);
        orchestrationMenuPresenter.loadOptions();

        orchestrationMenuPresenter.setOnClickObservable(orchestrationMenuAdapter.getViewClickedObservable());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orchestrationMenuPresenter.detachView();
    }

    @Override
    public void loadOptions(String[] options) {
        orchestrationMenuAdapter.setOptions(options);
        orchestrationMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void showScreen(Integer option) {
        switch (option) {
            case 0:
                startActivity(new Intent(OrchestrationMenuActivity.this, AuthActivity.class));
                break;
            case 1:
                startActivity(new Intent(OrchestrationMenuActivity.this, MultipleApiActivity.class));
                break;
            case 2:
                startActivity(new Intent(OrchestrationMenuActivity.this, StoringActivity.class));
                break;
            default:
                startActivity(new Intent(OrchestrationMenuActivity.this, AuthActivity.class));
        }
    }
}
