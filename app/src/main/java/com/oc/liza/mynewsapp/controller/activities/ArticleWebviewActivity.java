package com.oc.liza.mynewsapp.controller.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        configWebview();
        setContentView(webView);

    }

    private void configWebview() {

        //Create new webview to show the article and set the app as webclient
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        //Get the url saved in shared preferences
        sharedPref=this.getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        String url = sharedPref.getString("WEBVIEW_URL", null);
        webView.loadUrl(url);
    }


}
