package com.oc.liza.mynewsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsSearch {
    @SerializedName("web_url")
    @Expose
    private String url;


    @SerializedName("snippet")
    @Expose
    private String title;

    @SerializedName("multimedia")
    @Expose
    private ArrayList<NewsImage> multimedia;

    @SerializedName("pub_date")
    @Expose
    private Date published_date;


    @SerializedName("section_name")
    @Expose
    private String subsection;

    @SerializedName("news_desk")
    @Expose
    private String section;

    public String getSnippet() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getSection() {
        String str = "";
        if (subsection != null && subsection.length() > 1) {
            str += section + " > " + subsection;
        } else {
            str += section;
        }
        return str;
    }

    public void setSection(String section) {
        this.section = section;
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

    public ArrayList<NewsImage> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(ArrayList<NewsImage> multimedia) {
        this.multimedia = multimedia;
    }
}
