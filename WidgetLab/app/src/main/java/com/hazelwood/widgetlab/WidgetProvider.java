package com.hazelwood.widgetlab;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by Hazelwood on 10/14/14.
 */
public class WidgetProvider extends AppWidgetProvider {
    public static final String WIDGET_ID = "EXTRA_WIDGET_ID";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for(int i = 0; i < appWidgetIds.length; i++) {

            int widgetId = appWidgetIds[i];

            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            Intent configuration_intent = new Intent(context, Configuration_Activity.class);
            configuration_intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            PendingIntent configuration_pIntent = PendingIntent.getActivity(context, 0, configuration_intent, PendingIntent.FLAG_UPDATE_CURRENT);

            widgetView.setOnClickPendingIntent(R.id.configureBTN, configuration_pIntent);

            Intent details_intent = new Intent(context, Details_Activity.class);
            details_intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            PendingIntent details_pIntent = PendingIntent.getActivity(context, 0, details_intent, PendingIntent.FLAG_UPDATE_CURRENT);

            widgetView.setOnClickPendingIntent(R.id.iconBTN,details_pIntent);

            appWidgetManager.updateAppWidget(widgetId, widgetView);

            Intent startServiceIntent = new Intent(context, Service.class);
            startServiceIntent.putExtra(WIDGET_ID, widgetId);
            context.startService(startServiceIntent);
        }
    }
}
