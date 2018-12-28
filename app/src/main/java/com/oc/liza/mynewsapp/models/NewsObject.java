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

    @SerializedName("response")
    @Expose
    private NewsObject response;

    @SerializedName("docs")
    @Expose
    private ArrayList<News> docs;

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

    public NewsObject getResponse() {
        return response;
    }

    public ArrayList<News> getDocs() {
        return docs;
    }

    public void setDocs(ArrayList<News> docs) {
        this.docs = docs;
    }
}
