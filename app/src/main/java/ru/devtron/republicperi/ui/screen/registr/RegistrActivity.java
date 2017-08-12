package ru.devtron.republicperi.ui.screen.registr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.ui.screen.repair.RepairActivity;

/**
 * Created by flux on 12.08.2017.
 */

public class RegistrActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Button mButtonForAuthActivity;
    private Button mButtonForRepairActivity;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.registr_auth_btn:
                    RegistrActivity.this.finish();
                    break;
                case R.id.registr_forgot_password_btn:
                    intent.setClass(RegistrActivity.this, RepairActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mButtonForAuthActivity = findViewById(R.id.registr_auth_btn);
        mButtonForRepairActivity = findViewById(R.id.registr_forgot_password_btn);

        mButtonForAuthActivity.setOnClickListener(mOnClickListener);
        mButtonForRepairActivity.setOnClickListener(mOnClickListener);
    }
}
