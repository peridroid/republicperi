package ru.devtron.republicperi.screen.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.screen.profile.adapter.TabAdapter;

public class ProfileActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TabLayout mTabLayout;
    Toolbar mToolbar;

    private int[] icons = {
            R.drawable.ic_add_service,
            R.drawable.ic_account_edit
    };

    private String[] titles = {
            "Добавить услугу",
            "Редактировать профиль"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tab_layout);
        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mViewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            if (mTabLayout.getTabAt(i) != null) {
                //noinspection ConstantConditions
                mTabLayout.getTabAt(i).setIcon(icons[i]);
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mToolbar.setTitle(titles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
