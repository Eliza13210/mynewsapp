package com.oc.liza.mynewsapp.controller.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.models.News;
import com.oc.liza.mynewsapp.models.NewsObject;
import com.oc.liza.mynewsapp.utils.NewsStream;
import com.oc.liza.mynewsapp.views.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchResultFragment extends Fragment {
    @BindView(R.id.fragment_view)
    RecyclerView recyclerView; // 1 - Declare RecyclerView

    private Disposable disposable;
    // 2 - Declare list of news (News) & Adapter
    private List<News> newsList;
    private NewsAdapter adapter;
    private String url = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_result_fragment, container, false);
        ButterKnife.bind(this, view);


        SharedPreferences sharedPref=getActivity().getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        url = sharedPref.getString("SEARCH_URL", null);
        this.configureRecyclerView();
        this.executeHttpRequestWithRetrofit();

        return view;
    }

    //  Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // 3.1 - Reset list
        this.newsList = new ArrayList<>();
        // 3.2 - Create adapter passing the list of news
        this.adapter = new NewsAdapter(this.newsList);
        // 3.3 - Attach the adapter to the recycler view to populate items
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    // 1 - Execute our Stream
    private void executeHttpRequestWithRetrofit() {


        //- Execute the stream subscribing to Observable defined inside NewsStream
        this.disposable = NewsStream.streamFetchNewslist(url).subscribeWith(new DisposableObserver<NewsObject>() {
            @Override
            public void onNext(NewsObject news) {
                // - Update UI with list of news
                updateUI(news);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Tag_error", "url " + url + " " + e);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void updateUI(NewsObject news) {
        newsList.addAll(news.getResponse().getDocs());
        adapter.notifyDataSetChanged();

    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

}
