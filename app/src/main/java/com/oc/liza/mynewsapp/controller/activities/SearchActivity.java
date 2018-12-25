package com.oc.liza.mynewsapp.controller.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.oc.liza.mynewsapp.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    public String query;
    public String beginDate;
    public String endDate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.query)
    EditText search_query;
    @BindView(R.id.begin_date)
    EditText search_begin_date;
    @BindView(R.id.end_date)
    EditText search_end_date;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        search_query.setSelection(0);
        Objects.requireNonNull(this.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void getSearchUrl() {

        url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?&amp;"
                + "api-key=799e9f0e6e264b3a8e21b57f3f05dfd0&amp;q="
                + query;
        if (!beginDate.isEmpty()) {
            url += "&amp;begin_date=" + beginDate;
        }
        if (!endDate.isEmpty()) {
            url += "&amp;end_date=" + endDate;
        }

    }
}
