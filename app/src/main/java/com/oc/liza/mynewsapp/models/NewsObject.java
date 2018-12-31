package com.oc.liza.mynewsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsObject {

    //If NewsObject is from Top stories or Most Popular Api
    @SerializedName("results")
    @Expose
    private ArrayList<News> results;

    //If NewsObject is from Article Search Api
    @SerializedName("response")
    @Expose
    private NewsObject response;

    @SerializedName("docs")
    @Expose
    private ArrayList<NewsSearch> docs;

    @SerializedName("meta")
    @Expose
    private NewsSearch meta;

    @SerializedName("hits")
    @Expose
    private int hits;

    public NewsObject() {
    }

    public ArrayList getList() {
        if (results == null) {
            return response.getDocs();
        } else {
            return results;
        }
    }

    public ArrayList<News> getResults() {
        return results;
    }

    public NewsObject getResponse() {
        return response;
    }

    public ArrayList<NewsSearch> getDocs() {
        return docs;
    }

    public void setResults(ArrayList<News> results) {
        this.results = results;
    }

    public void setResponse(NewsObject response) {
        this.response = response;
    }

    public void setDocs(ArrayList<NewsSearch> docs) {
        this.docs = docs;
    }

    public NewsSearch getMeta(){
        return meta;
    }

    public int getHits(){
        return hits;
    }

}
