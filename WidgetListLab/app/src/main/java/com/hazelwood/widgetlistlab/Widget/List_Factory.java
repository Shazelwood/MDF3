package com.hazelwood.widgetlistlab.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.hazelwood.widgetlistlab.Details.Detail_Activity;
import com.hazelwood.widgetlistlab.NewsArticle;
import com.hazelwood.widgetlistlab.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Hazelwood on 10/16/14.
 */
public class List_Factory implements RemoteViewsService.RemoteViewsFactory {
    private static final int ID_CONSTANT = 0x0101010;
    private ArrayList<NewsArticle> arrayList;
    private Context mContext;
    private int mAppWidgetId;


    public List_Factory(Context context) {
        mContext = context;
        arrayList = new ArrayList<NewsArticle>();
    }

    @Override
    public void onCreate() {

        ArrayList<NewsArticle> oldArticles = new ArrayList<NewsArticle>();

        try {
            File extFolder = mContext.getExternalFilesDir(null);
            File file = new File(extFolder, "ListArticles.dat");

            if (file.exists()) {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream oin = new ObjectInputStream(fin);
                oldArticles = (ArrayList<NewsArticle>) oin.readObject();
                oin.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if (oldArticles != null){
            arrayList = oldArticles;
        }
    }

    @Override
    public void onDataSetChanged() {
        ArrayList<NewsArticle> oldArticles = new ArrayList<NewsArticle>();

        try {
            File extFolder = mContext.getExternalFilesDir(null);
            File file = new File(extFolder, "ListArticles.dat");

            if (file.exists()) {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream oin = new ObjectInputStream(fin);
                oldArticles = (ArrayList<NewsArticle>) oin.readObject();
                oin.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if (oldArticles != null){
            arrayList = oldArticles;
        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        NewsArticle article = arrayList.get(i);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.detail_items);
        rv.setTextViewText(R.id.custom_item, article.getTitle());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(Detail_Activity.EXTRA_ITEM, article);
        rv.setOnClickFillInIntent(R.id.custom_items_root, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

//... include adapter-like methods here. See the StackView Widget sample.

}