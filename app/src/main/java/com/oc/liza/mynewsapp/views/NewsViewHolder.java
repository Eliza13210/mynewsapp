package com.oc.liza.mynewsapp.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.controller.activities.ArticleWebviewActivity;
import com.oc.liza.mynewsapp.models.News;
import com.oc.liza.mynewsapp.models.NewsItem;

import butterknife.BindView;
import butterknife.ButterKnife;


class NewsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_item_title)
    TextView title;
    @BindView(R.id.fragment_date)
    TextView date;
    @BindView(R.id.fragment_section)
    TextView section;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;


    public NewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void updateWithNewsItem(final NewsItem newsItem, final Context context) {
        //Update view with title, date and section
        this.title.setText(newsItem.getTitle());
        this.date.setText(newsItem.getPublished_date());
        this.section.setText(newsItem.sectionAndSubsectionString());
        //Show photo in view if there is one
        try {
            String url = newsItem.getImageUrl();
            Glide.with(context)
                    .load(url)
                    .into(thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exception", "error " + e);
        }

        //when user click on view, open the article in a webview inside the app
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //store the articles web url in shared preferences
                SharedPreferences sharedPref = context.getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("WEBVIEW_URL", newsItem.getUrl());
                editor.apply();

                //Start webview activity
                Intent startWebview = new Intent(context, ArticleWebviewActivity.class);
                context.startActivity(startWebview);
            }
        });

    }
}
