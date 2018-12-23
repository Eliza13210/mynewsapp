package com.oc.liza.mynewsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

class News {

    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("subsection")
    @Expose
    private String subsection;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("published_date")
    @Expose
    private Date published_date;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("multimedia")
    @Expose
    private ArrayList<NewsImage> multimedia;

    @SerializedName("media")
    @Expose
    private ArrayList<NewsImage> media;


    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublished_date() {
        return DateFormat.getDateInstance(DateFormat.SHORT).format(published_date);
    }

    public void setPublished_date(Date published_date) {
        this.published_date = published_date;
    }

    public String getUrl() {
        if (url.startsWith("https")) {
            //replace https with http in url
            url = url.substring(5);
            url = "http" + url;
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMultimedia(ArrayList<NewsImage> multimedia) {
        this.multimedia = multimedia;
    }

    public ArrayList<NewsImage> getMultimedia() {
        return multimedia;
    }

    public ArrayList<NewsImage> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<NewsImage> media) {
        this.media = media;
    }

    public String getImageUrl() {
        try {
            return multimedia.get(0).getUrl();
        } catch (Exception e) {
            return media.get(0).getMetadata().get(0).getUrl();
        }
    }

    public String toString() {
        String str = "";
        if (subsection != null && subsection.length() > 1) {
            str += section + " > " + subsection;
        } else {
            str += section;
        }
        return str;
    }
}
