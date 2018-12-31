package com.oc.liza.mynewsapp.controller.activities;

import org.junit.Test;

import static org.junit.Assert.*;

public class SearchActivityTest {

    private SearchActivity searchActivity = new SearchActivity();


    @Test
    public void getUrl_IfSearchQueryAndDates_ReturnUrl() {

        searchActivity.query = "christmas";
        searchActivity.beginDate = "20010101";
        searchActivity.endDate = "20121212";
        searchActivity.checkboxQuery="Science";

        searchActivity.createSearchUrl();

        assertEquals("http://api.nytimes.com/svc/search/v2/articlesearch.json?&" +
                "api-key=799e9f0e6e264b3a8e21b57f3f05dfd0&q=Sciencechristmas&begin_date=20010101" +
                "&end_date=20121212&sort=newest", searchActivity.url);

    }

    @Test
    public void getUrl_IfSearchQueryAndNoDates_ReturnUrl() {

        searchActivity.query = "kittens";
        searchActivity.beginDate = "";
        searchActivity.endDate = "";
        searchActivity.checkboxQuery="Movies";

        searchActivity.createSearchUrl();

        assertEquals("http://api.nytimes.com/svc/search/v2/articlesearch.json?&" +
                "api-key=799e9f0e6e264b3a8e21b57f3f05dfd0&q=Movieskittens&sort=newest", searchActivity.url);

    }
}