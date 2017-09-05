package ru.devtron.republicperi.ui.base;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.Toast;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.ui.screen.main.MainFragment;
import ru.devtron.republicperi.ui.transition.DetailsTransition;

public abstract class BaseActivity extends AppCompatActivity {
    DetailsTransition mDetailsTransition;

    protected ProgressDialog mProgressDialog;
    private boolean isDialogShowing;

    public void setFragment(Fragment fragment, ImageView sharedImageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mDetailsTransition == null) {
                mDetailsTransition = new DetailsTransition();
            }
            fragment.setSharedElementEnterTransition(mDetailsTransition);
            fragment.setExitTransition(new Fade());
            fragment.setSharedElementReturnTransition(mDetailsTransition);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addSharedElement(sharedImageView, ViewCompat.getTransitionName(sharedImageView));
        fragmentTransaction.replace(R.id.root_frame, fragment, fragment.getClass().getSimpleName());
        boolean isRootScreen = MainFragment.class.getSimpleName().equals(fragment.getClass().getSimpleName());
        if (!isRootScreen) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.CustomDialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.progress_splash);
    }

    public void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isDialogShowing) showLoading();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            isDialogShowing = true;
            mProgressDialog.dismiss();
        }
    }
}
