package com.qualityfull.reactivexandroidbyexamples.ui.validationforms;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.qualityfull.reactivexandroidbyexamples.R;
import com.qualityfull.reactivexandroidbyexamples.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ValidationFormsActivity extends BaseActivity implements ValidationFormsMvpView {

    @Inject
    ValidationFormsPresenter mValidationFormsPresenter;

    @BindView(R.id.full_name_text_input_layout)
    TextInputLayout textInputLayoutFullName;

    @BindView(R.id.email_text_input_layout)
    TextInputLayout textInputLayoutEmail;

    @BindView(R.id.password_text_input_layout)
    TextInputLayout textInputLayoutPassword;

    @BindView(R.id.date_birth_text_input_layout)
    TextInputLayout textInputLayoutDateBirth;

    @BindView(R.id.phone_text_input_layout)
    TextInputLayout textInputLayoutPhone;

    @BindView(R.id.full_name)
    EditText editTextFullName;

    @BindView(R.id.email)
    EditText editTextEmail;

    @BindView(R.id.password)
    EditText editTextPassword;

    @BindView(R.id.date_birth)
    EditText editTextDateBirth;

    @BindView(R.id.phone)
    EditText editTextPhone;

    @BindView(R.id.button_register)
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_validation_forms);
        ButterKnife.bind(this);

        mValidationFormsPresenter.attachView(this);

        //Register validation of Fields.
        mValidationFormsPresenter.validateFormFields(RxTextView.textChanges(editTextFullName),
                RxTextView.textChanges(editTextEmail), RxTextView.textChanges(editTextPassword),
                RxTextView.textChanges(editTextDateBirth), RxTextView.textChanges(editTextPhone));

        //Register apply mask to fields
        mValidationFormsPresenter.applyMaskTextView(RxTextView.textChanges(editTextDateBirth),
                RxTextView.textChanges(editTextPhone));

        //Create the Observable for multiple clicks control
        mValidationFormsPresenter.rxButtonEvents(RxView.clicks(buttonRegister));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mValidationFormsPresenter.detachView();
    }

    @Override
    public void setEnabledButton(boolean enabled) {
        buttonRegister.setEnabled(enabled);
    }

    @Override
    public void hideErrorEnabledFullName(boolean hide, int minSize, int maxSize) {
        textInputLayoutFullName.setError(getString(R.string.field_name_required, minSize, maxSize));
        textInputLayoutFullName.setErrorEnabled(!hide);
    }

    @Override
    public void hideErrorEnabledEmail(boolean hide) {
        textInputLayoutEmail.setError(getString(R.string.email_required));
        textInputLayoutEmail.setErrorEnabled(!hide);
    }

    @Override
    public void hideErrorEnabledPassword(boolean hide) {
        textInputLayoutPassword.setError(getString(R.string.password_required));
        textInputLayoutPassword.setErrorEnabled(!hide);
    }

    @Override
    public void setMaskDate(String masked) {
        editTextDateBirth.setText(masked);
        editTextDateBirth.setSelection(masked.length());
    }

    @Override
    public void setMaskPhone(String masked) {
        editTextPhone.setText(masked);
        editTextPhone.setSelection(masked.length());
    }

    @Override
    public void hideErrorEnabledDateBirth(boolean hide) {
        textInputLayoutDateBirth.setError(getString(R.string.date_birth_required));
        textInputLayoutDateBirth.setErrorEnabled(!hide);
    }

    @Override
    public void hideErrorEnabledPhone(boolean hide) {
        textInputLayoutPhone.setError(getString(R.string.phone_required));
        textInputLayoutPhone.setErrorEnabled(!hide);
    }

    @Override
    public void register() {
        Timber.i("User named %s with email %s and date of birth %s.",
                editTextFullName.getText().toString(),
                editTextEmail.getText().toString(),
                editTextDateBirth.getText().toString());
    }
}
