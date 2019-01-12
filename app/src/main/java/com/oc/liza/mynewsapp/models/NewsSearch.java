package com.oc.liza.mynewsapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

//Object from Article search API
public class NewsSearch implements NewsItem {

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

    //Section name can be either of these three

    @SerializedName("section_name")
    @Expose
    private String subsection;

    @SerializedName("news_desk")
    @Expose
    private String section;

    @SerializedName("headline")
    @Expose
    private NewsSearch headline;

    @SerializedName("main")
    @Expose
    private String main;


    @Override
    public String getTitle() {
        title = title.replaceAll("<p>", "").replaceAll("</p>", "");
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getPublished_date() {
        try {
            return DateFormat.getDateInstance(DateFormat.SHORT).format(published_date);
        } catch (Exception e) {
            return "Pas de date";
        }
    }

    @Override
    public String getImageUrl() {
        try {
            return multimedia.get(0).getUrl();
        } catch (Exception e) {
            return "https://upload.wikimedia.org/wikipedia/commons/4/40/New_York_Times_logo_variation.jpg";
        }
    }

    @Override
    public String getUrl() {
        if (url.startsWith("https")) {
            //replace https with http in url
            url = url.substring(5);
            url = "http" + url;
        }
        return url;
    }

    @Override
    public String sectionAndSubsectionString() {
        String str = "";
        if (section != null && subsection != null) {
            str += section + " > " + subsection;
        } else if (section != null && subsection == null) {
            str += section;
        } else if (headline.getMain() != null) {
            str += headline.getMain();
        }
        return str;
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

    public String getMain() {
        return main;
    }

    public void setPublished_date(Date published_date) {
        this.published_date = published_date;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setHeadline(NewsSearch headline) {
        this.headline = headline;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }
}
