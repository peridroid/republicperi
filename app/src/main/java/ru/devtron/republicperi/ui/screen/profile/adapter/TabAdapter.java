package ru.devtron.republicperi.ui.screen.profile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import ru.devtron.republicperi.ui.screen.profile_edit.ProfileEditFragment;
import ru.devtron.republicperi.ui.screen.service_creator.ServiceCreatorFragment;

public class TabAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> mFragmentArrayList;

    public TabAdapter(FragmentManager fm) {
        super(fm);
        initTabs();
    }

    private void initTabs() {
        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(new ServiceCreatorFragment());
        mFragmentArrayList.add(new ProfileEditFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentArrayList.size();
    }

}
