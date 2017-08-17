package ru.devtron.republicperi.ui.screen.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.CommonRepository;
import ru.devtron.republicperi.data.KeyValueStorage;
import ru.devtron.republicperi.data.network.requests.LoginReq;
import ru.devtron.republicperi.data.network.response.AccessToken;
import ru.devtron.republicperi.ui.screen.registr.RegistrActivity;
import ru.devtron.republicperi.ui.screen.repair.RepairActivity;

public class AuthActivity extends AppCompatActivity {
    Toolbar mToolbar;
    Button mBtnForRegistAtivity;
    private Button mBtnForRepairAtivity;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mToolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mBtnForRegistAtivity = findViewById(R.id.auth_registration_btn);
        mBtnForRepairAtivity = findViewById(R.id.auth_forgot_btn);
        mLoginBtn = findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(mOnClickListener);

        mBtnForRegistAtivity.setOnClickListener(mOnClickListener);
        mBtnForRepairAtivity.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.auth_registration_btn:
                    intent.setClass(AuthActivity.this, RegistrActivity.class);
                    startActivity(intent);
                    break;
                case R.id.auth_forgot_btn:
                    intent.setClass(AuthActivity.this, RepairActivity.class);
                    startActivity(intent);
                    break;
                case R.id.login_btn:
                    final LoginReq blablabla = new LoginReq("loki@asgard.com", "blablabla");
                    Call<AccessToken> tokenCall = CommonRepository.getInstance()
                            .loginUser(blablabla);
                    tokenCall.enqueue(new Callback<AccessToken>() {
                        @Override
                        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                            if (response.isSuccessful()) {
                                KeyValueStorage.getInstance().saveToken(response.body().getToken());
                                KeyValueStorage.getInstance().saveLogin(blablabla);
                                Toast.makeText(AuthActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AuthActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
                            Toast.makeText(AuthActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        }
    };
}
