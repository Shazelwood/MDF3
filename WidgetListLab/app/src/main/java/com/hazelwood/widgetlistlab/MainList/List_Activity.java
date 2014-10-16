package com.hazelwood.widgetlistlab.MainList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hazelwood.widgetlistlab.Form_Activity;
import com.hazelwood.widgetlistlab.NewsArticle;
import com.hazelwood.widgetlistlab.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Hazelwood on 10/16/14.
 */
public class List_Activity extends Activity implements List_Fragment.ListFragmentListener {
    FragmentManager fragmentManager;
    ArrayList<NewsArticle> articles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        articles = new ArrayList<NewsArticle>();

        ArrayList<NewsArticle> oldArticles = new ArrayList<NewsArticle>();

        try {
            File extFolder = getExternalFilesDir(null);
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
            articles = oldArticles;
        }

        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.list_container, List_Fragment.newInstance(articles), List_Fragment.TAG).commit();


    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<NewsArticle> oldArticles = new ArrayList<NewsArticle>();

        try {
            File extFolder = getExternalFilesDir(null);
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
            articles = oldArticles;
        }

        fragmentManager.beginTransaction().replace(R.id.list_container, List_Fragment.newInstance(articles), List_Fragment.TAG).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, Form_Activity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //    public void load(ArrayList<NewsArticle> arrayList){
//        arrayList = new ArrayList<NewsArticle>();
//
//        try {
//            File extFolder = this.getExternalFilesDir(null);
//            File file = new File(extFolder, "ListArticles.dat");
//
//            if (file.exists()) {
//                FileInputStream fin = new FileInputStream(file);
//                ObjectInputStream oin = new ObjectInputStream(fin);
//                arrayList = (ArrayList<NewsArticle>) oin.readObject();
//                oin.close();
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
}
