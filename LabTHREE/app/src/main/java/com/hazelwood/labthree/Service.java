package com.hazelwood.labthree;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Hazelwood on 10/4/14.
 */
public class Service extends IntentService {
    public static final String ACTION_SAVE =  "HAZELWOOD_ACTION_SAVE";
    public static final String EXTRA_SAVE =  "HAZELWOOD_EXTRA_SAVE";

    public int NOTIFY_ID = 0x00100403;
    String _title, _date, _byLine, _description, _imageURL, _webURL, _id;
    String jsonString;
    ArrayList<NYTimes> newsArray;



    public Service() {
        super("HAZELWOOD_SERVICE");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            //URL connection check
            String urlString = "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/sports/1.json?offset=20&api-key=96b25181c8c0c478f8a1a5e5c71226a8%3A1%3A68792990";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();
            jsonString = IOUtils.toString(is);
            is.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //JSON
            JSONObject outerObject = new JSONObject(jsonString);
            JSONArray items = outerObject.getJSONArray("results");
            newsArray = new ArrayList<NYTimes>();

            for (int i = 0; i < items.length(); i++) {
                JSONObject news = items.getJSONObject(i);
                if (news.has("title")) {
                    _title = news.getString("title");
                }
                if (news.has("id")) {
                    _id = news.getString("id");
                }
                if (news.has("byline")) {
                    _byLine = news.getString("byline");
                }
                if (news.has("published_date")) {
                    _date = news.getString("published_date");
                }
                if (news.has("abstract")) {
                    _description = news.getString("abstract");
                }
                if (news.has("url")) {
                    _webURL = news.getString("url");
                }
                newsArray.add(new NYTimes(_title,_byLine,_description, _webURL, _id));
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        int max = 10;
        int min = 1;

        Random random = new Random();
        int randomArticle = random.nextInt(10);

        NotificationManager notificationManager = (NotificationManager) Service.this.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Service.this);
        String articleTitle = newsArray.get(randomArticle).getTitle();
        String articleSummary = newsArray.get(randomArticle).getDescription();
        String articleURL = newsArray.get(randomArticle).getNewsURL();

        builder.setSmallIcon(android.R.drawable.ic_popup_reminder);
        builder.setContentTitle(articleTitle);

        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.setBigContentTitle(articleTitle);
        style.bigText(articleSummary);
        builder.setStyle(style);

        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(articleURL));
        PendingIntent pendingIntent1 = PendingIntent.getActivity(Service.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent1);

        intent = new Intent(ACTION_SAVE);
        intent.putExtra(EXTRA_SAVE, newsArray.get(randomArticle));
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(Service.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(android.R.drawable.ic_menu_save, "SAVE", pendingIntent2);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(NOTIFY_ID, notification);
    }
}
