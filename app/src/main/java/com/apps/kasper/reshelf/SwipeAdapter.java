package com.apps.kasper.reshelf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SwipeAdapter extends FragmentStatePagerAdapter{
    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        String[] welcomeScreenBig = {"Prepare your books.", "Take a photo.", "Write a title.", "Simply resell your books."};
        Fragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("count", welcomeScreenBig[i]);
        bundle.putInt("position", i);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
