package com.oc.liza.mynewsapp.models;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NewsTest {

    private News news = new News();
    private NewsImage newsImage = new NewsImage();

    @Test
    public void getUrl_IfStartsWithHttps_ReplaceWithHttp() {
        news.setUrl("https://www.google.com");
        String newUrl = news.getUrl();
        assertEquals("http://www.google.com", newUrl);

        news.setUrl("http://www.google.com");
        newUrl = news.getUrl();
        assertEquals("http://www.google.com", newUrl);

    }

    @Test
    public void getImageUrl_MultimediaListContainsObject_FetchUrlFromMultimediaList() {
        //try with multimedia list
        ArrayList<NewsImage> multimedia = new ArrayList<>();

        newsImage.setUrl("https://static01.nyt.com/testImage.jpg");
        multimedia.add(newsImage);
        news.setMultimedia(multimedia);

        String multimediaUrl = news.getImageUrl();
        assertEquals("https://static01.nyt.com/testImage.jpg", multimediaUrl);
    }

    @Test
    public void getImageUrl_MultimediaListIsEmpty_FetchUrlFromMediaList() {

        ArrayList<MediaImage> metadata = new ArrayList<>();
        ArrayList<NewsImage> media = new ArrayList<>();
        MediaImage mediaImage = new MediaImage();

        mediaImage.setUrl("http://www.google.com");
        metadata.add(mediaImage);
        newsImage.setMetadata(metadata);
        media.add(newsImage);
        news.setMedia(media);

        String mediaUrl = news.getImageUrl();
        assertEquals("http://www.google.com", mediaUrl);
    }

    @Test
    public void sectionAndSubsectionString_ObjectWithSubsection_ReturnStringWithSectionAndSubsection() {
        news.setSection("Politics");
        news.setSubsection("California");

        String resultString = news.sectionAndSubsectionString();
        assertEquals("Politics > California", resultString);
    }

    @Test
    public void sectionAndSubsectionString_ObjectWithoutSubsection_ReturnStringWithSection() {
        news.setSection("Politics");

        String resultString = news.sectionAndSubsectionString();
        assertEquals("Politics", resultString);
    }

}