package com.oc.liza.mynewsapp.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final static int FRAGMENT_COUNT = 5;
    private List<Fragment> fragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        // getItem is called to instantiate the fragment for the given page.
        return this.fragmentList.get(i);
    }

    @Override
    public int getCount() {

        //variable static
        return FRAGMENT_COUNT;
    }

}
