package com.oc.liza.mynewsapp.controller.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.models.NewsItem;
import com.oc.liza.mynewsapp.models.NewsObject;
import com.oc.liza.mynewsapp.network.NewsStream;
import com.oc.liza.mynewsapp.views.NewsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MainFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
        setUrl();
        this.configureRecyclerView();
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    @Override
    public void setUrl() {
        SharedPreferences pref = Objects.requireNonNull(getActivity()).getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        url = pref.getString("SEARCH_KEY", null);
    }
}