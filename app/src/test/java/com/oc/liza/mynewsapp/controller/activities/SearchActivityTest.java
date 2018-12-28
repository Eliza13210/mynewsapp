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

        searchActivity.getSearchUrl();

        assertEquals("https://api.nytimes.com/svc/search/v2/articlesearch.json?&amp;" +
                "api-key=799e9f0e6e264b3a8e21b57f3f05dfd0&amp;q=christmas&amp;begin_date=20010101" +
                "&amp;end_date=20121212", searchActivity.url);

    }

    @Test
    public void getUrl_IfSearchQueryAndNoDates_ReturnUrl() {

        searchActivity.query = "kittens";
        searchActivity.beginDate = "";
        searchActivity.endDate = "";

        searchActivity.getSearchUrl();

        assertEquals("https://api.nytimes.com/svc/search/v2/articlesearch.json?&amp;" +
                "api-key=799e9f0e6e264b3a8e21b57f3f05dfd0&amp;q=kittens", searchActivity.url);

    }
}