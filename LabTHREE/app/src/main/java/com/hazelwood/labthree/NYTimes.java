package com.hazelwood.labthree;

import java.io.Serializable;

/**
 * Created by Hazelwood on 8/27/14.
 */
public class NYTimes implements Serializable {

    public static final long serialVersionUID = 1234567890L;
    private String title, author, description, webURL, id;

    public NYTimes(String _title, String _author, String _description, String _URL, String _id){
        title = _title;
        author = _author;
        description = _description;
        webURL = _URL;
        id = _id;
    }

    public NYTimes(){

    }

    public void setNewsURL(String newsURL) {
        this.webURL = newsURL;
    }

    public String getNewsURL() {
        return webURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
