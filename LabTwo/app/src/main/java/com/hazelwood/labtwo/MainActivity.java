package com.hazelwood.labtwo;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class MainActivity extends Activity implements Grid_Fragment.Grid_FragmentListener{
    public static final String TAG = "MainActivityTAG";
    static final String ACTION_UPDATE_GRID = "com.hazelwood.labtwo.ACTION_UPDATE_GRID";

    FragmentManager mFragManager;
    File[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragManager = getFragmentManager();
        mFragManager.beginTransaction()
                .replace(R.id.container, Grid_Fragment.newInstance(files), Grid_Fragment.TAG)
                .commit();

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_UPDATE_GRID)){
                File extFolder = context.getExternalFilesDir(null);
                File[] files = extFolder.listFiles();
                Log.d(TAG, "WE made it!");
                try{
                    for (int i = 0; i < files.length; i++){
                        if (files[i].exists()) {
                            FileInputStream fin = new FileInputStream(files[i]);
                            ObjectInputStream oin = new ObjectInputStream(fin);
//                        infoArrayList = (ArrayList<Info>) oin.readObject();
                            oin.close();
                        }

                        mFragManager.beginTransaction()
                                .replace(R.id.container, Grid_Fragment.newInstance(files), Grid_Fragment.TAG)
                                .commit();

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_GRID);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            Intent service = new Intent(MainActivity.this, Intent_Service.class);
            startService(service);
            Log.d(TAG, "REFRESHED");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
