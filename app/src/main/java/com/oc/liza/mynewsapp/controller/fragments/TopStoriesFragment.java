package com.oc.liza.mynewsapp.controller.fragments;

import com.oc.liza.mynewsapp.R;

public class TopStoriesFragment extends BaseFragment {


    @Override
    public void setUrl() {

        url = this.getResources().getString(R.string.top_stories_url);
    }
}
