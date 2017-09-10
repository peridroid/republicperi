package ru.devtron.republicperi.ui.screen.repair;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnTextChanged;
import ru.devtron.republicperi.R;
import ru.devtron.republicperi.utils.validator.EmailValidator;
import ru.devtron.republicperi.utils.validator.EmptyValidator;
import ru.devtron.republicperi.utils.validator.Validator;
import ru.devtron.republicperi.utils.validator.ValidatorsComposer;

/**
 * Created by flux on 12.08.2017.
 */

public class RepairActivity extends AppCompatActivity {
    private static final String TAG = "MR.ONETWO";
    private Toolbar mToolbar;
    @BindView(R.id.email_til)
    TextInputLayout mEmailTil;
    @BindView(R.id.repair_email_et)
    EditText mRepairEmailEt;
    @BindView(R.id.login_btn)
    Button mLoginBtn;

    final ValidatorsComposer<String> emptinessValidatorComposer = new ValidatorsComposer<>(new EmptyValidator());
    final ValidatorsComposer<String> emailValidatorComposer = new ValidatorsComposer<>(new EmptyValidator(), new EmailValidator());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @OnTextChanged(value = { R.id.repair_email_et },
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged() {
        mLoginBtn.setEnabled(false);

        Log.i(TAG, "FUCK");


        mEmailTil.setErrorEnabled(false);
        if(!emailValidatorComposer.isValid(mRepairEmailEt.getText().toString())){
            showFieldError(mEmailTil, emailValidatorComposer.getDescription());
            return;
        }

        mLoginBtn.setEnabled(true);
    }

    void showFieldError(TextInputLayout til, String error) {
        til.setErrorEnabled(true);
        til.setError(error);
        mLoginBtn.setEnabled(false);
    }


}
