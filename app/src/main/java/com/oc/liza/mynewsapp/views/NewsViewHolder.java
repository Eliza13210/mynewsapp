package com.oc.liza.mynewsapp.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.controller.activities.ArticleWebviewActivity;
import com.oc.liza.mynewsapp.models.NewsItem;

import butterknife.BindView;
import butterknife.ButterKnife;


class NewsViewHolder extends RecyclerView.ViewHolder {

    private SharedPreferences sharedPref;

    @BindView(R.id.fragment_item_title)
    TextView title;
    @BindView(R.id.fragment_date)
    TextView date;
    @BindView(R.id.fragment_section)
    TextView section;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;


    NewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void updateWithNewsItem(final NewsItem newsItem, final Context context) {
        //Update view with title, date and section
        this.title.setText(newsItem.getTitle());
        this.date.setText(newsItem.getPublished_date());
        this.section.setText(newsItem.sectionAndSubsectionString());

        //Load photo and make article clickable
        loadPhoto(newsItem, context);
        startWebview(newsItem, context);

        //Check if the user has already clicked on the article, then change background color
        sharedPref = context.getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        if (sharedPref.getBoolean(newsItem.getTitle(), false)) {
            itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPressed));
        }
    }

    private void startWebview(final NewsItem newsItem, final Context context) {
        //when user click on view, open the article in a web view inside the app
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Change background color when user click article
                itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPressed));

                //store the articles web url in shared preferences
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("WEBVIEW_URL", newsItem.getUrl());
                editor.putBoolean(newsItem.getTitle(), true);
                editor.apply();

                //Start web view activity
                Intent startWebview = new Intent(context, ArticleWebviewActivity.class);
                context.startActivity(startWebview);
            }
        });
    }

    private void loadPhoto(final NewsItem newsItem, final Context context) {
        //Show photo in view if there is one
        try {
            String url = newsItem.getImageUrl();
            Glide.with(context)
                    .load(url)
                    .into(thumbnail);
        } catch (Exception e) {
            Glide.with(context)
                    .load(context.getString(R.string.ny_times_logo))
                    .into(thumbnail);
        }

    }
}
