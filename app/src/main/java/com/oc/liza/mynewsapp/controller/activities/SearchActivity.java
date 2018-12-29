package com.oc.liza.mynewsapp.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    public String url;
    private SharedPreferences sharedPref;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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

                //Fetch user input and create url
                query = search_query.getText().toString();
                beginDate = search_begin_date.getText().toString();
                endDate = search_end_date.getText().toString();
                getSearchUrl();

                //Save url in shared preferences
                sharedPref = getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("SEARCH_URL", url);
                editor.apply();

                //Start new activity with the url
                Intent result = new Intent(SearchActivity.this, SearchResultActivity.class);
                startActivity(result);


            }
        });

    }

    //This method will create the url to use for the request with the user input

    public void getSearchUrl() {

        url = "http://api.nytimes.com/svc/search/v2/articlesearch.json?&"
                + "api-key=799e9f0e6e264b3a8e21b57f3f05dfd0&q="
                + query;
        if (!beginDate.isEmpty()) {
            url += "&begin_date=" + beginDate;
        }
        if (!endDate.isEmpty()) {
            url += "&end_date=" + endDate;
        }

    }
}
