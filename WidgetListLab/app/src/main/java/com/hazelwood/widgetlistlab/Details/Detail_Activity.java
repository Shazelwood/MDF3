package com.hazelwood.widgetlistlab.Details;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hazelwood.widgetlistlab.NewsArticle;
import com.hazelwood.widgetlistlab.R;
import com.hazelwood.widgetlistlab.Widget.WidgetProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Detail_Activity extends Activity {
    public static final String EXTRA_ITEM = "com.fullsail.android.DetailsActivity.EXTRA_ITEM";
    String check;
    public static final String TAG = "Detail_Activity_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        NewsArticle article = (NewsArticle) intent.getSerializableExtra(EXTRA_ITEM);

        TextView tvONE = (TextView) findViewById(R.id.titleTV);
        TextView tvTWO = (TextView) findViewById(R.id.authorTV);
        TextView tvTHREE = (TextView) findViewById(R.id.dateTV);

        tvONE.setText(article.getTitle());
        check = article.getTitle();
        tvTWO.setText(article.getAuthor());
        tvTHREE.setText(article.getDate());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_delete) {
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

            for (int i = 0; i < oldArticles.size(); i++){
                if (check.equals(oldArticles.get(i).getTitle())){
                    oldArticles.remove(i);
                }
            }

            ArrayList<NewsArticle> articles = new ArrayList<NewsArticle>();
            if (oldArticles != null){
                articles = oldArticles;
            }


            try {
                File extFolder = getExternalFilesDir(null);
                File extFile = new File(extFolder, "ListArticles.dat");
                FileOutputStream fos = new FileOutputStream(extFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(articles);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            AppWidgetManager mgr = AppWidgetManager.getInstance(Detail_Activity.this);

            int[] ids = mgr.getAppWidgetIds(new ComponentName(Detail_Activity.this, WidgetProvider.class));

            for(int num : ids) {
                mgr.notifyAppWidgetViewDataChanged(num,R.id.widget_listview);
            }

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}