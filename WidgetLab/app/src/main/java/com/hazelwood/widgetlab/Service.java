package com.hazelwood.widgetlab;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Hazelwood on 10/14/14.
 */
public class Service extends IntentService {
    public static final String WIDGET_ID = "EXTRA_WIDGET_ID";
    public Service() {
        super("com.HAZELWOOD.widgetlab.service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedState = prefs.getString("PREF_LOCATION_LIST","Not Assigned");

        DateFormat outFormat = new SimpleDateFormat("HH:mm:ss");
        outFormat.setTimeZone(TimeZone.getTimeZone("EST"));

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        ArrayList<Weather> hamptonWEATHER = new ArrayList<Weather>();
        hamptonWEATHER.add(new Weather("Good", "", 89, 84, 83, 0, 0));

        ArrayList<Weather> baltimoreWEATHER = new ArrayList<Weather>();
        baltimoreWEATHER.add(new Weather("Great", "", 89, 84, 83, 0, 0));

        ArrayList<Weather> washingtonWEATHER = new ArrayList<Weather>();
        washingtonWEATHER.add(new Weather("Excellent", "", 89, 84, 83, 0, 0));

        if (selectedState.equals("1")){
            int id = intent.getIntExtra(WIDGET_ID, -1);
            long time = System.currentTimeMillis();
            Date d = new Date(time);
            String result = outFormat.format(d);

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_layout);
            views.setTextViewText(R.id.dataTV, hamptonWEATHER.get(0).getConditions());
            views.setTextViewText(R.id.tempTV, String.valueOf(hamptonWEATHER.get(0).getTemperatureNOW()));
            views.setTextViewText(R.id.timeTV, result);

            appWidgetManager.updateAppWidget(id, views);

        } else if (selectedState.equals("2")){
            int id = intent.getIntExtra(WIDGET_ID, -1);
            long time = System.currentTimeMillis();
            Date d = new Date(time);
            String result = outFormat.format(d);

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_layout);
            views.setTextViewText(R.id.dataTV, washingtonWEATHER.get(0).getConditions());
            views.setTextViewText(R.id.tempTV, String.valueOf(washingtonWEATHER.get(0).getTemperatureNOW()));
            views.setTextViewText(R.id.timeTV, result);

            appWidgetManager.updateAppWidget(id, views);

        } else if (selectedState.equals("3")){
            int id = intent.getIntExtra(WIDGET_ID, -1);
            long time = System.currentTimeMillis();
            Date d = new Date(time);
            String result = outFormat.format(d);

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_layout);
            views.setTextViewText(R.id.dataTV, baltimoreWEATHER.get(0).getConditions());
            views.setTextViewText(R.id.tempTV, String.valueOf(baltimoreWEATHER.get(0).getTemperatureNOW()));
            views.setTextViewText(R.id.timeTV, result);

            appWidgetManager.updateAppWidget(id, views);

        }


    }
}
