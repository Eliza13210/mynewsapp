package com.oc.liza.mynewsapp.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.Z;
import static org.junit.Assert.*;

public class NewsSearchTest {

    private NewsSearch newsSearch = new NewsSearch();

    @Test
    public void getPublished_date_DateIsNull_ReturnNoDate() {
        newsSearch.setPublished_date(null);

        assertEquals("Pas de date", newsSearch.getPublished_date());
    }

    @Test

    public void getImageUrl() {
        ArrayList<NewsImage> list = new ArrayList<>();
        NewsImage image = new NewsImage();
        image.setUrl("cat.jpg");
        list.add(image);
        newsSearch.setMultimedia(list);

        assertEquals("https://static01.nyt.com/cat.jpg", newsSearch.getImageUrl());
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
    public void sectionAndSubsectionString_whenNoSubsection() {
        newsSearch.setSection("Fantastic animals");
        newsSearch.setSubsection("false");
        assertEquals("Fantastic animals", newsSearch.sectionAndSubsectionString());
    }

    @Test
    public void sectionAndSubsectionString() {
        newsSearch.setSection("Animals");
        newsSearch.setSubsection("Mammals");
        assertEquals("Animals > Mammals", newsSearch.sectionAndSubsectionString());
    }

    @Test
    public void sectionAndSubsectionString_whenNoSectionAndNoSubsection_shouldReturnStringWithHeadline() {
        NewsSearch headline = new NewsSearch();
        headline.setMain("Europe");
        newsSearch.setHeadline(headline);

        assertEquals("Europe", newsSearch.sectionAndSubsectionString());
    }

    @Test
    public void getTitle_whenHtmlTag_thenReplaceTags() {
        String str = "<p>Christmas is coming</p>";
        newsSearch.setTitle(str);
        String newStr = newsSearch.getTitle();

        assertEquals("Christmas is coming", newStr);

    }
}