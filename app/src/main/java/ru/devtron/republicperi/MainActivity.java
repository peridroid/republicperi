package ru.devtron.republicperi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
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
                Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                break;
        }

        if (item.getGroupId() != R.id.additional_group)
            item.setChecked(true);
        return true;
    }
}
