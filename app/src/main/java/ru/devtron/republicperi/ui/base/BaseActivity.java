package ru.devtron.republicperi.ui.base;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.widget.ImageView;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.ui.screen.main.MainFragment;
import ru.devtron.republicperi.ui.transition.DetailsTransition;

public class BaseActivity extends AppCompatActivity {
    DetailsTransition mDetailsTransition;

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
}
