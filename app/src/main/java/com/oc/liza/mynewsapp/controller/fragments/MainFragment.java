package com.oc.liza.mynewsapp.controller.fragments;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

public class MainFragment extends BaseFragment {

    @Override
    public void setUrl() {
        SharedPreferences pref = Objects.requireNonNull(getActivity()).getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        url = pref.getString("SEARCH_KEY", null);
    }
}