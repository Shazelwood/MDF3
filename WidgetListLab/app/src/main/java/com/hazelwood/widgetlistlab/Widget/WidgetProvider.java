package com.hazelwood.widgetlistlab.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.hazelwood.widgetlistlab.Details.Detail_Activity;
import com.hazelwood.widgetlistlab.Form_Activity;
import com.hazelwood.widgetlistlab.R;

/**
 * Created by Hazelwood on 10/14/14.
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for(int i = 0; i < appWidgetIds.length; i++) {

            int widgetId = appWidgetIds[i];

            Intent intent = new Intent(context, List_Service.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            rv.setRemoteAdapter(R.id.widget_listview, intent);
            rv.setEmptyView(R.id.widget_listview, R.id.widget_emptytext);

            Intent detailIntent = new Intent(context, Detail_Activity.class);
            PendingIntent pendIntent = PendingIntent.getActivity(context, 0, detailIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.widget_listview, pendIntent);

            Intent formIntent = new Intent(context, Form_Activity.class);
            formIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            PendingIntent form_pIntent = PendingIntent.getActivity(context, 0, formIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            rv.setOnClickPendingIntent(R.id.addBTN,form_pIntent);

            appWidgetManager.updateAppWidget(widgetId, rv);
        }
    }
}
