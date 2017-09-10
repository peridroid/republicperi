package ru.devtron.republicperi.ui.screen.registr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.CommonRepository;
import ru.devtron.republicperi.data.network.requests.SignUpReq;
import ru.devtron.republicperi.data.network.response.AccessToken;
import ru.devtron.republicperi.ui.screen.auth.AuthActivity;
import ru.devtron.republicperi.ui.screen.repair.RepairActivity;
import ru.devtron.republicperi.utils.validator.EmailValidator;
import ru.devtron.republicperi.utils.validator.EmptyValidator;
import ru.devtron.republicperi.utils.validator.PhoneValidator;
import ru.devtron.republicperi.utils.validator.ValidatorsComposer;

public class RegistrActivity extends AppCompatActivity {
    private static final String TAG = "RegistrActivity";
    @BindView(R.id.registr_email_et)
    EditText mRegistrEmailEt;
    @BindView(R.id.registr_surname_et)
    EditText mRegistrSurnameEt;
    @BindView(R.id.registr_name_et)
    EditText mRegistrNameEt;
    @BindView(R.id.registr_phone_et)
    EditText mRegistrPhoneEt;
    @BindView(R.id.registr_password_et)
    EditText mRegistrPasswordEt;
    @BindView(R.id.registr_confirm_password_et)
    EditText mRegistrConfirmPasswordEt;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.email_til)
    TextInputLayout mEmailTil;
    @BindView(R.id.surname_til)
    TextInputLayout mSurnameTil;
    @BindView(R.id.name_til)
    TextInputLayout mNameTil;
    @BindView(R.id.phone_til)
    TextInputLayout mPhoneTil;
    @BindView(R.id.password_til)
    TextInputLayout mPasswordTil;
    @BindView(R.id.confirm_password_til)
    TextInputLayout mConfirmPasswordTil;
    @BindView(R.id.auth_btn)
    Button mRegistrAuthBtn;
    @BindView(R.id.registr_forgot_password_btn)
    Button mRegistrForgotPasswordBtn;

    final ValidatorsComposer<String> emptinessValidatorComposer = new ValidatorsComposer<>(new EmptyValidator());
    final ValidatorsComposer<String> emailEmptyValidatorComposer = new ValidatorsComposer<>(new EmptyValidator(), new EmailValidator());
    final ValidatorsComposer<String> phoneEmptyValidatorComposer = new ValidatorsComposer<>(new EmptyValidator(), new PhoneValidator());

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.auth_btn:
                    intent.setClass(RegistrActivity.this, AuthActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.registr_forgot_password_btn:
                    intent.setClass(RegistrActivity.this, RepairActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @OnClick(R.id.login_btn)
    public void signUpUser() {
        String email = mRegistrEmailEt.getText().toString();
        String surname = mRegistrSurnameEt.getText().toString();
        String name = mRegistrNameEt.getText().toString();
        String password = mRegistrPasswordEt.getText().toString();
        String phone = mRegistrPhoneEt.getText().toString();
        SignUpReq signUpReq = new SignUpReq(email, name, surname, phone, password);
        CommonRepository.getInstance().signUpUser(signUpReq).enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistrActivity.this, "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        Toast.makeText(RegistrActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(RegistrActivity.this, "Произошла ошибка при регистрации", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnTextChanged(value = {R.id.registr_email_et, R.id.registr_surname_et, R.id.registr_name_et,
            R.id.registr_phone_et, R.id.registr_password_et, R.id.registr_confirm_password_et },
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged() {
        mLoginBtn.setEnabled(false);

        mEmailTil.setErrorEnabled(false);
        if (!emailEmptyValidatorComposer.isValid(mRegistrEmailEt.getText().toString())) {
            showFieldError(mEmailTil, emailEmptyValidatorComposer.getDescription());
            return;
        }

        mSurnameTil.setErrorEnabled(false);
        if (!emptinessValidatorComposer.isValid(mRegistrSurnameEt.getText().toString())){
            showFieldError(mSurnameTil, emptinessValidatorComposer.getDescription());
            return;
        }

        mNameTil.setErrorEnabled(false);
        if (!emptinessValidatorComposer.isValid(mRegistrNameEt.getText().toString())){
            showFieldError(mNameTil, emptinessValidatorComposer.getDescription());
            return;
        }

        mPhoneTil.setErrorEnabled(false);
        if (!phoneEmptyValidatorComposer.isValid(mRegistrPhoneEt.getText().toString())) {
            showFieldError(mPhoneTil, phoneEmptyValidatorComposer.getDescription());
            return;
        }

        mConfirmPasswordTil.setErrorEnabled(false);
        if(!mRegistrConfirmPasswordEt.getText().toString()
                .equals(mRegistrPasswordEt.getText().toString())){
            showFieldError(mConfirmPasswordTil, "Пароли не совпадают");
            return;
        }

        mLoginBtn.setEnabled(true);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Button buttonForRepairActivity = findViewById(R.id.registr_forgot_password_btn);

        mRegistrAuthBtn.setOnClickListener(mOnClickListener);
        buttonForRepairActivity.setOnClickListener(mOnClickListener);
    }

    void showFieldError(TextInputLayout til, String error) {
        til.setErrorEnabled(true);
        til.setError(error);
        mLoginBtn.setEnabled(false);
    }
}
