package com.oc.liza.mynewsapp.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class MediaImageTest {


    @Test
    public void getUrlFromMediaImage() {
        MediaImage m = new MediaImage();
        String testUrl = "http://www.google.com";
        m.setUrl(testUrl);
        String url = m.getUrl();
        assertNotNull(m);
        assertEquals(url, testUrl);
    }

}