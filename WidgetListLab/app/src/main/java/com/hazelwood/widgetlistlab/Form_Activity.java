package com.hazelwood.widgetlistlab;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hazelwood.widgetlistlab.Widget.WidgetProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Hazelwood on 10/16/14.
 */
public class Form_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Button button = (Button)findViewById(R.id.saveBTN);

        final EditText etONE = (EditText)findViewById(R.id.authorET);
        final EditText etTWO = (EditText)findViewById(R.id.titleET);
        final EditText etTHREE = (EditText)findViewById(R.id.dateET);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                ArrayList<NewsArticle> articles = new ArrayList<NewsArticle>();
                if (oldArticles != null){
                    articles = oldArticles;
                }


                articles.add(new NewsArticle(
                        etONE.getText().toString(),
                        etTWO.getText().toString(),
                        etTHREE.getText().toString()
                ));

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

                AppWidgetManager mgr = AppWidgetManager.getInstance(Form_Activity.this);

                int[] ids = mgr.getAppWidgetIds(new ComponentName(Form_Activity.this, WidgetProvider.class));

                for(int id : ids) {
                    mgr.notifyAppWidgetViewDataChanged(id,R.id.widget_listview);
                }

                finish();
            }
        });

    }
}
