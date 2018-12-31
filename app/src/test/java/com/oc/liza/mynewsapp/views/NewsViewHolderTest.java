package com.oc.liza.mynewsapp.views;

import android.content.Context;
import android.view.View;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.models.News;
import com.oc.liza.mynewsapp.models.NewsItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewsViewHolderTest {

    NewsViewHolder holder;
    News news;
    Context ctx;

    @Test
    public void updateWithNewsItem() {
        ctx = mock(Context.class);
        holder = mock(NewsViewHolder.class);
        holder.updateWithNewsItem(news, ctx);

    }
}