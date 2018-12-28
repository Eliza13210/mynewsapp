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
import com.oc.liza.mynewsapp.controller.activities.MainActivity;
import com.oc.liza.mynewsapp.models.News;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsViewHolder extends RecyclerView.ViewHolder {
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

    public void updateWithNewsItem(final News newsItem, final Context context) {
        this.title.setText(newsItem.getTitle());
        this.date.setText(newsItem.getPublished_date());
        this.section.setText(newsItem.sectionAndSubsectionString());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref=context.getSharedPreferences("MYNEWS_KEY", context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPref.edit();
                editor.putString("WEBVIEW_URL",newsItem.getUrl());
                editor.apply();

                Intent startWebview=new Intent(context, ArticleWebviewActivity.class);
                context.startActivity(startWebview);
            }
        });
        try {
            String url = newsItem.getImageUrl();
            Glide.with(context)
                    .load(url)
                    .into(thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exception", "error " + e);
        }
    }


}
