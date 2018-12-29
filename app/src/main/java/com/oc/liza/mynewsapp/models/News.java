package com.oc.liza.mynewsapp.models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class News {
//For top stories and most popular

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

    //For article search
    @SerializedName("web_url")
    @Expose
    private String web_url;


    @SerializedName("snippet")
    @Expose
    private String snippet;


    @SerializedName("pub_date")
    @Expose
    private Date pub_date;


    @SerializedName("section_name")
    @Expose
    private String subsection_search;

    @SerializedName("news_desk")
    @Expose
    private String section_search;

    @SerializedName("headline")
    @Expose
    private News headline;

    @SerializedName("main")
    @Expose
    private String main;


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
        if (snippet == null) {
            return title;
        } else {
            return snippet;
        }

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublished_date() {

        try {
            if (pub_date == null) {
                return DateFormat.getDateInstance(DateFormat.SHORT).format(published_date);
            } else {
                return DateFormat.getDateInstance(DateFormat.SHORT).format(pub_date);
            }
        } catch (Exception e) {
            Log.e("Date", "No date" + e);
        }
        return "Pas de date";
    }

    public void setPublished_date(Date published_date) {

        this.published_date = published_date;
    }

    public String getUrl() {
        if (web_url == null) {
            if (url.startsWith("https")) {
                //replace https with http in url
                url = url.substring(5);
                url = "http" + url;
            }
            return url;
        } else {
            return web_url;
        }


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

    public String sectionAndSubsectionString() {
        String str = "";
        if (section_search == null && subsection_search == null) {
            if (subsection != null && subsection.length() > 1) {
                str += section + " > " + subsection;
            } else {
                str += section;
            }

        } else {
            if (section_search != null && subsection_search != null) {
                str += section_search + " > " + subsection_search;
            } else if (section_search != null && subsection_search==null) {
                str += section_search;
            } else {
                str +=  headline.getMain();
            }

        }
        return str;
    }

    public String getMain() {
        return main;
    }
}
