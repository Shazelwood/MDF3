package com.hazelwood.widgetlistlab.MainList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hazelwood.widgetlistlab.NewsArticle;
import com.hazelwood.widgetlistlab.R;

import java.util.ArrayList;

/**
 * Created by Hazelwood on 8/27/14.
 */
public class Article_Adapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<NewsArticle> mObjects;

    private static final long ID_CONSTANT = 123456789;

    public Article_Adapter(Context c, ArrayList<NewsArticle> objects){
        mContext = c;
        mObjects = objects;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public NewsArticle getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.customlist, parent, false);
        }

        NewsArticle item = getItem(position);
        TextView textView1 = (TextView)convertView.findViewById(R.id.newsTitle);
        TextView textView2 = (TextView)convertView.findViewById(R.id.newsDescription);

        textView1.setText(item.getTitle());
        textView2.setText(item.getDate());


        return convertView;
    }

}
