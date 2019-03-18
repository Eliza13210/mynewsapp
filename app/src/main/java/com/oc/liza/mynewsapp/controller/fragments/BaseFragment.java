package com.oc.liza.mynewsapp.controller.fragments;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public abstract class BaseFragment extends Fragment {
    @BindView(R.id.fragment_view)
    RecyclerView recyclerView; // 1 - Declare RecyclerView

    protected Disposable disposable;
    // 2 - Declare list of news (NewsStories) & Adapter
    protected List<NewsItem> newsList;
    protected NewsAdapter adapter;
    protected String url;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
        setUrl();
        this.configureRecyclerView();
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    public abstract void setUrl();


    //  Configure RecyclerView, Adapter, LayoutManager & glue it together
    protected void configureRecyclerView() {
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
     * Define the url to start the stream and
     * and fetch the news
     */
    protected void executeHttpRequestWithRetrofit() {

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

    protected void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    protected void updateUIWithListOfNews(NewsObject news) {
        if (news.checkIfResult() == 0) {
            Snackbar.make(recyclerView, "No articles found", Snackbar.LENGTH_LONG).show();

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
