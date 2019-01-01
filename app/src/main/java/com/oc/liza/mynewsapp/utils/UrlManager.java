package com.oc.liza.mynewsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class UrlManager {
    private Context context;
    private String url = "";
    private String query = "";
    private String beginDate;
    private String endDate;
    private List<CheckBox> checkBoxList;
    private String checkboxQuery = "";

    public UrlManager(Context context) {
        this.context = context;

    }

    //Get the user input; search query, checkbox selection, begin date and end date
    public void getUserInput(EditText search_query, EditText search_begin_date, EditText search_end_date, CheckBox cbHealth, CheckBox cbMovies, CheckBox cbScience) {

        query = search_query.getText().toString();
        beginDate = search_begin_date.getText().toString();
        endDate = search_end_date.getText().toString();

        checkBoxList = new ArrayList<>();
        checkBoxList.add(cbHealth);
        checkBoxList.add(cbMovies);
        checkBoxList.add(cbScience);
        for (CheckBox box : checkBoxList) {
            if (box.isChecked()) {
                checkboxQuery += box.getText() + "%20";
            }
        }

    }

    //This method will create the url to use for the request with the user input
    public void createSearchUrl() {

        this.url = "http://api.nytimes.com/svc/search/v2/articlesearch.json?&"
                + "api-key=799e9f0e6e264b3a8e21b57f3f05dfd0&q="
                + checkboxQuery;
        if (!query.isEmpty())
            this.url += query;
        if (!beginDate.isEmpty()) {
            this.url += "&begin_date=" + beginDate;
        }
        if (!endDate.isEmpty()) {
            this.url += "&end_date=" + endDate;
        }
        this.url += "&sort=newest";
    }

    public String getUrl() {
        return url;
    }

    private void saveUrl(String sharedPrefKey) {
        SharedPreferences sharedPref = context.getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("SEARCH_URL", url);
        editor.apply();


    }

    public boolean checkConditions() {
        return (checkboxQuery == null || query == null);
    }

}
