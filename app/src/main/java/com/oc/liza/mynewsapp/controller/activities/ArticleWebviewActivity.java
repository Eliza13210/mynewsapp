package com.oc.liza.mynewsapp.controller.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oc.liza.mynewsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class ArticleWebviewActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.webView)
    WebView webView;

    private String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        webView = new WebView(this);
        setContentView(webView);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        SharedPreferences sharedPref=this.getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        url=sharedPref.getString("WEBVIEW_URL", null);
        webView.loadUrl(url);
    }



}
