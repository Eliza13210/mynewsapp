package com.oc.liza.mynewsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MediaImage {
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        if (!url.startsWith("https://static01.nyt.com/")) {
            String new_url = "https://static01.nyt.com/" + url;
            url = new_url;
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
