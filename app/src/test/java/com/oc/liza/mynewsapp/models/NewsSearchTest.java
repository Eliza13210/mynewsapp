package com.oc.liza.mynewsapp.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class NewsSearchTest {

    NewsSearch newsSearch = new NewsSearch();

    @Test
    public void getPublished_date_DateIsNull_ReturnNoDate() {
        newsSearch.setPublished_date(null);

        assertEquals("Pas de date", newsSearch.getPublished_date());
    }

    @Test
    public void getUrl_UrlStartsWithHttps_ReturnUrlWithHttp() {
        newsSearch.setUrl("https://www.mynews.com");
        String newUrl = newsSearch.getUrl();
        assertEquals("http://www.mynews.com", newUrl);

        newsSearch.setUrl("http://www.mynews.com");
        newUrl = newsSearch.getUrl();
        assertEquals("http://www.mynews.com", newUrl);

    }

    @Test
    public void sectionAndSubsectionString() {
        newsSearch.setSection("Fantastic animals");
        assertEquals("Fantastic animals", newsSearch.sectionAndSubsectionString());
    }

    @Test
    public void getTitle_whenHtmlTag_thenReplaceTags() {
        String str = "<p>Christmas is coming</p>";
        newsSearch.setTitle(str);
        String newStr = newsSearch.getTitle();

        assertEquals("Christmas is coming", newStr);

    }
}