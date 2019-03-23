package com.oc.liza.mynewsapp.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.network.UrlManager;

import java.util.Objects;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {

    protected UrlManager manager;

    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.query)
    EditText search_query;
    @BindView(R.id.begin_date)
    EditText search_begin_date;
    @BindView(R.id.end_date)
    EditText search_end_date;
    @BindView(R.id.cbHealth)
    CheckBox cbHealth;
    @BindView(R.id.cbMovies)
    CheckBox cbMovies;
    @BindView(R.id.cbScience)
    CheckBox cbScience;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_search;
    }

    private void initLayout() {
        manager = new UrlManager(this);

        //Open the keyboard automatically
        search_query.setSelection(0);
        Objects.requireNonNull(this.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager.getUserInput(search_query, search_begin_date, search_end_date, cbHealth, cbMovies, cbScience);

                // Start new activity to show results if the conditions are met
                if (manager.checkConditions()) {

                    //Let the manager take care of creating the url with the user input
                    manager.createSearchUrl();
                    manager.saveUrl("SEARCH_KEY");
                    startActivity(new Intent(SearchActivity.this, SearchResultActivity.class));
                } else {
                    manager.clearInput();
                    Toast.makeText(getApplicationContext(), "Sélectionnez au moins une catégorie et un mot clé", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
