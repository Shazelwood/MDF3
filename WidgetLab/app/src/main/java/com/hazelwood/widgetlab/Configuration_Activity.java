package com.hazelwood.widgetlab;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by Hazelwood on 10/14/14.
 */
public class Configuration_Activity extends Activity {

    private int mWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new Preference_Fragment(), Preference_Fragment.TAG).commit();
    }
}
