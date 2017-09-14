package ru.devtron.republicperi.ui.screen.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.KeyValueStorage;
import ru.devtron.republicperi.ui.base.BaseActivity;
import ru.devtron.republicperi.ui.screen.auth.AuthActivity;
import ru.devtron.republicperi.ui.screen.profile.ProfileActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    TextView mUserNameNav;
    TextView mUserEmailNav;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initNavigationView();
        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.root_frame, mainFragment);
            fragmentTransaction.commit();
        }
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    /**
     * Первоначальная настройка навигации
     */
    private void initNavigationView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        //получаем корневую вью в NavigationView
        View header = mNavigationView.getHeaderView(0);

        //слушатель на нажатие Navigation Header
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent intent;
                if (KeyValueStorage.getInstance().getKeyToken().equals("")) {
                    intent = new Intent(MainActivity.this, AuthActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                }
                startActivity(intent);
            }
        });
        mUserNameNav = findViewById(R.id.user_name_tv);
        mUserEmailNav = findViewById(R.id.user_email_tv);
        mNavigationView.setNavigationItemSelectedListener(this);

        // Кнопка гамбургер
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(MainActivity.this, "HomeScreen", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_places:
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
        }

        if (item.getGroupId() != R.id.additional_group)
            item.setChecked(true);
        return true;
    }
}
