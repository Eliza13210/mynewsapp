package com.oc.liza.mynewsapp.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class NewsObjectTest {
    private ArrayList<NewsStories> results;
    private NewsObject news;
    private ArrayList<NewsSearch> docs;
    private NewsObject response;

    @Test
    public void getList_whenResultsIsNull_thenReturnGetResponseGetDocs() {
        news = new NewsObject();
        response = new NewsObject();
        news.setResponse(response);
        docs = new ArrayList<>();
        NewsSearch search = new NewsSearch();
        docs.add(search);
        response.setDocs(docs);

        assertSame(news.getList(), news.getResponse().getDocs());
    }

    @Test
    public void getList_whenResultsIsNotNull_thenReturnResults() {
        news = new NewsObject();

        response = new NewsObject();
        news.setResponse(response);
        docs = new ArrayList<>();
        NewsSearch search = new NewsSearch();
        docs.add(search);
        response.setDocs(docs);

        results = new ArrayList<>();
        results.add(new NewsStories());
        news.setResults(results);

        assertSame(news.getList(), results);
    }

    @Test
    public void checkIfResult() {
        news=new NewsObject();
        results = new ArrayList<>();
        results.add(new NewsStories());
        news.setResults(results);
        news.setNum_results(2);
        
        assertEquals(2, news.checkIfResult());
    }
}