package com.oc.liza.mynewsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsObject {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("results")
    @Expose
    private ArrayList<News> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<News> getResults() {
        return results;
    }

    public void setResults(ArrayList<News> results) {
        this.results = results;
    }
}
