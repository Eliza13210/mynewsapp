package com.oc.liza.mynewsapp.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oc.liza.mynewsapp.controller.fragments.MainFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        // getItem is called to instantiate the fragment for the given page.
        return MainFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return 5;
    }

}
