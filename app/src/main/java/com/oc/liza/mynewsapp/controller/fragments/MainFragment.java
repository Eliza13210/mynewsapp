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
import com.oc.liza.mynewsapp.utils.NewsStream;
import com.oc.liza.mynewsapp.views.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MainFragment extends Fragment {

    @BindView(R.id.fragment_view)
    RecyclerView recyclerView; // 1 - Declare RecyclerView

    private Disposable disposable;
    // 2 - Declare list of news (News) & Adapter
    private List<NewsItem> newsList;
    private NewsAdapter adapter;
    private int position;
    private String url;
    private SharedPreferences pref;

    /**
     * Create new instance is called from the Pager adapter
     * Put the position in the bundle so it can be called in OnCreate method
     *
     * @param position the position from the viewpager
     * @return the fragment
     */
    public static MainFragment newInstance(int position) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Check if the bundle contains the position in order to define the URL
     * otherwise set position to 100
     *
     * @param savedInstanceState
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position", 0);
        } else {
            position = 100;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
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

    /**
     * Define the url depending on the position
     * and fetch the stream
     */
    private void executeHttpRequestWithRetrofit() {
        pref = getActivity().getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);

        switch (position) {
            case 0:
                url = this.getResources().getString(R.string.top_stories_url);
                break;
            case 1:
                url = this.getResources().getString(R.string.most_popular_url);
                break;
            case 2:
                url = this.getResources().getString(R.string.science_url);
                break;
            case 3:
                url = this.getResources().getString(R.string.health_url);
                break;
            case 4:
                url = this.getResources().getString(R.string.movies_url);
                break;
            case 100:
                url = pref.getString("SEARCH_KEY", null);
                break;
        }

        //- Execute the stream subscribing to Observable defined inside NewsStream
        this.disposable = NewsStream.streamFetchNewslist(url).subscribeWith(new DisposableObserver<NewsObject>() {
            @Override
            public void onNext(NewsObject news) {
                // - Update UI with list of news
                updateUIWithListOfNews(news);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Error observer", "Error " + e);

            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUIWithListOfNews(NewsObject news) {
        if (news.checkIfResult() == 0) {
            Snackbar.make(recyclerView, "Pas de résultat", Snackbar.LENGTH_LONG).show();

        } else {
            newsList.addAll(news.getList());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }
}