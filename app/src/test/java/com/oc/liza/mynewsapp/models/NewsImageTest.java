package com.oc.liza.mynewsapp.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class NewsImageTest {

    @Test
    public void getUrl() {

        NewsImage image = new NewsImage();

        String url = "www.easter_egg.jpg";
        image.setUrl(url);
        url=image.getUrl();

        assertEquals("https://static01.nyt.com/www.easter_egg.jpg", url);
    }
}