package com.hazelwood.widgetlab;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RemoteViews;

/**
 * Created by Hazelwood on 10/14/14.
 */
public class Preference_Fragment extends PreferenceFragment {
    private int mWidgetId;
    public static final String TAG = "PREFERENCE_FRAGMENT.TAG";
    public static final String WIDGET_ID = "EXTRA_WIDGET_ID";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                Intent launcherIntent = getActivity().getIntent();
                Bundle extras = launcherIntent.getExtras();

                mWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

                if (extras != null) {
                    mWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                }

                if (mWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                    getActivity().setResult(getActivity().RESULT_CANCELED);
                    getActivity().finish();
                }

                if (mWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    Intent intent = new Intent(getActivity(), Configuration_Activity.class);
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
                    PendingIntent pIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    RemoteViews widgetView = new RemoteViews(getActivity().getPackageName(), R.layout.widget_layout);
                    widgetView.setOnClickPendingIntent(R.id.configureBTN, pIntent);

                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
                    appWidgetManager.updateAppWidget(mWidgetId, widgetView);
                }

                Intent startServiceIntent = new Intent(getActivity(), Service.class);
                startServiceIntent.putExtra(WIDGET_ID, mWidgetId);
                getActivity().startService(startServiceIntent);

                Intent result = new Intent();
                result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
                getActivity().setResult(getActivity().RESULT_OK, result);
                getActivity().finish();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}