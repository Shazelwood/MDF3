package com.hazelwood.widgetlistlab;

import java.io.Serializable;

/**
 * Created by Hazelwood on 10/16/14.
 */
public class NewsArticle implements Serializable {

    private static final long serialVersionUID = 517116325584636891L;

    private String mTitle;
    private String mAuthor;
    private String mDate;

    public NewsArticle(String _title, String _author, String _date) {
        mTitle = _title;
        mAuthor = _author;
        mDate = _date;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }
}
