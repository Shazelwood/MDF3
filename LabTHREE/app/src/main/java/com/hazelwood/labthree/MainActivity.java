package com.hazelwood.labthree;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;


public class MainActivity extends Activity implements List_Fragment.List_FragmentListener {
    public static final String ACTION_UPDATE_LIST = "HAZELWOOD_ACTION_UPDATE_LIST";
    public static final String EXTRA_UPDATE_LIST =  "HAZELWOOD_EXTRA_UPDATE_LIST";


    SharedPreferences preferenceManager;
    private FragmentManager fragmentManager;
    NYTimes nyTimes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        preferenceManager = getSharedPreferences("HAZELWOOD_PREF", MODE_PRIVATE);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_UPDATE_LIST)){
                nyTimes = (NYTimes) intent.getSerializableExtra(EXTRA_UPDATE_LIST);

                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, List_Fragment.newInstance(nyTimes), List_Fragment.TAG).commit();
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Intent service = new Intent(MainActivity.this, Service.class);

        if (preferenceManager.getBoolean("firstrun", true)) {
            AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0, service, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() /*+ 30000*/, 5000, pendingIntent);

            preferenceManager.edit().putBoolean("firstrun", false).commit();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_LIST);
        registerReceiver(broadcastReceiver, intentFilter);

    }

    @Override
    public void populateDetail() {

    }
}
