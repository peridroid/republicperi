package ru.devtron.republicperi.ui.screen.profile;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.ui.screen.profile.adapter.TabAdapter;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    CollapsingToolbarLayout mCollapsingToolbarLayout;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    Toolbar mToolbar;
    AppBarLayout mAppbar;
    TabAdapter adapter;

    private int[] icons = {
            R.drawable.ic_add_service,
            R.drawable.ic_account_edit
    };

    private String[] titles = {
            "Добавить услугу",
            "Редактировать профиль"
    };
    private int selectedTabIconColor;
    private int unSelectedTabIconColor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tab_layout);
        mToolbar = findViewById(R.id.toolbar);
        mAppbar = findViewById(R.id.app_bar);

        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_profile);

        selectedTabIconColor = ContextCompat.getColor(ProfileActivity.this, R.color.white);
        unSelectedTabIconColor = ContextCompat.getColor(ProfileActivity.this, R.color.unselected_tab_icon_color);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initTabs();
    }

    private void initTabs() {
        adapter = new TabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            if (mTabLayout.getTabAt(i) != null) {
                //noinspection ConstantConditions
                mTabLayout.getTabAt(i).setIcon(icons[i]);
            }
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeColorTabIcon(selectedTabIconColor, tab.getIcon());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeColorTabIcon(unSelectedTabIconColor, tab.getIcon());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mCollapsingToolbarLayout.getLayoutParams();
        final int startHeight = params.height;
        final AccelerateInterpolator interpolator = new AccelerateInterpolator(1f);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffs, int positionOffsetPixels) {
                float positionOffset = 1 - positionOffs;

                Log.i(TAG, "onPageScrolled: position + " + position + " positionOffset " + positionOffset + " positionOffsetPixels " + positionOffsetPixels);


                if ((position == adapter.getCount() - 1 && positionOffset == 1) ||
                        position < adapter.getCount() - 2)
                    return;

                mCollapsingToolbarLayout.setAlpha(interpolator.getInterpolation(positionOffset));
                int newHeight = (int) (startHeight * positionOffset);
                params.height = newHeight > startHeight ? startHeight : newHeight;
                mCollapsingToolbarLayout.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                mToolbar.setTitle(titles[position]);
                if (position == mViewPager.getAdapter().getCount() - 1) {
                    mAppbar.setExpanded(false, true);
                } else {
                    mAppbar.setExpanded(true, true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private void changeColorTabIcon(int color, Drawable tabIcon) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(tabIcon, color);
        } else {
            tabIcon.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }
}
