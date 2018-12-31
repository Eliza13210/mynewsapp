package com.oc.liza.mynewsapp.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class MediaImageTest {


    @Test
    public void getUrlFromMediaImage() {
        MediaImage mediaImage = new MediaImage();
        String testUrl = "http://www.google.com";
        mediaImage.setUrl(testUrl);
        String url = mediaImage.getUrl();
        assertNotNull(mediaImage);
        assertEquals(url, testUrl);
    }

}