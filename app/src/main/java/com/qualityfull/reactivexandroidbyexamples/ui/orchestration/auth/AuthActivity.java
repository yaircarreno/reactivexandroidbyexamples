package com.qualityfull.reactivexandroidbyexamples.ui.orchestration.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.qualityfull.reactivexandroidbyexamples.R;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BaseActivity;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class AuthActivity extends BaseActivity implements AuthMvpView {

    @Inject
    AuthPresenter authPresenter;

    @BindView(R.id.full_name_series)
    EditText editTextFullSeriesName;

    @BindView(R.id.label_results)
    TextView textLabelResults;

    @BindView(R.id.label_errors)
    TextView textLabelErrors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        authPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        authPresenter.detachView();
    }

    @OnClick(R.id.button_get_token)
    public void getToken() {
        Timber.i("Search series");
        authPresenter.showSeries(editTextFullSeriesName.getText().toString());
    }

    @OnClick(R.id.button_set_token)
    public void setToken() {
        Timber.i("Clear token");
        authPresenter.clearTokenStored();
    }

    @Override
    public void showResults(int size) {
        textLabelResults.setVisibility(View.VISIBLE);
        textLabelErrors.setVisibility(View.INVISIBLE);
        textLabelResults.setText(getString(R.string.text_results, size));
    }

    @Override
    public void showError(String descriptionError) {
        textLabelErrors.setVisibility(View.VISIBLE);
        textLabelResults.setVisibility(View.INVISIBLE);
        textLabelErrors.setText(descriptionError);
    }
}
