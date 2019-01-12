package com.oc.liza.mynewsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class UrlManager {
    private Context context;
    private String url = "";
    private String query;
    private String beginDate = "";
    private String endDate = "";
    private String checkboxQuery;

    public UrlManager(Context context) {
        this.context = context;
    }

    /**
     * This function gets all the user input
     *
     * @param search_query      the edit text view with the search query
     * @param search_begin_date the edit text view with begin date
     * @param search_end_date   the edit text view with the end date
     * @param cbHealth          the checkbox with category health
     * @param cbMovies          the checkbox with category movies
     * @param cbScience         the checkbox with category science
     */
    public void getUserInput(EditText search_query, EditText search_begin_date, EditText search_end_date, CheckBox cbHealth, CheckBox cbMovies, CheckBox cbScience) {

        query = search_query.getText().toString();

        if (search_begin_date != null && search_end_date != null) {
            beginDate = search_begin_date.getText().toString();
            beginDate = beginDate.replace("/", "");
            endDate = search_end_date.getText().toString();
            endDate = endDate.replace("/", "");
        }
        List<CheckBox> checkBoxList = new ArrayList<>();
        checkBoxList.add(cbHealth);
        checkBoxList.add(cbMovies);
        checkBoxList.add(cbScience);
        for (CheckBox box : checkBoxList) {
            if (box.isChecked()) {
                checkboxQuery = "";
                checkboxQuery += box.getText() + "%20";
            }
        }
    }

    /**
     * This method will create the url to use for the request with the user input
     */
    public void createSearchUrl() {

        url = "http://api.nytimes.com/svc/search/v2/articlesearch.json?&"
                + "api-key=799e9f0e6e264b3a8e21b57f3f05dfd0&fq=news_desk:("
                + checkboxQuery
                + ")&q=";
        if (!query.isEmpty())
            url += query;
        if (!beginDate.isEmpty()) {
            url += "&begin_date=" + beginDate;
        }
        if (!endDate.isEmpty()) {
            url += "&end_date=" + endDate;
        }
        url += "&sort=newest";
    }

    public String getUrl() {
        return url;
    }

    /**
     * Save the url in shared preferences
     *
     * @param sharedPrefKey the key you want to associate the url with in sharedpreferences
     */

    public void saveUrl(String sharedPrefKey) {
        SharedPreferences sharedPref = context.getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(sharedPrefKey, url);
        editor.apply();
        checkboxQuery = null;
        Log.e("search", url);
    }

    /**
     * Check that the user has chosen at least one checkbox category and one search query
     *
     * @return true if conditions are met
     */
    public boolean checkConditions() {
        return (checkboxQuery != null && !query.isEmpty());
    }
}
