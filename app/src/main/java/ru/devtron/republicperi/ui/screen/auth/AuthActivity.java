package ru.devtron.republicperi.ui.screen.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.ui.screen.registr.RegistrActivity;
import ru.devtron.republicperi.ui.screen.repair.RepairActivity;

public class AuthActivity extends AppCompatActivity {
    Toolbar mToolbar;
    Button mBtnForRegistAtivity;
    private Button mBtnForRepairAtivity;

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
            }
        }
    };
}
