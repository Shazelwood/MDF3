package com.hazelwood.labone;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class MainActivity extends Activity implements List_Fragment.List_FragmentListener, Info_Fragment.Info_FragmentListener, Detail_Fragment.Detail_FragmentListener{

    public static final String TAG = "MainActivityTAG";
    static final String ACTION_UPDATE_LIST = "com.fullsail.android.ACTION_UPDATE_LIST";


    private FragmentManager mFragManager;
    ArrayList<Info> infoArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragManager = getFragmentManager();
        mFragManager.beginTransaction()
                .replace(R.id.container, List_Fragment.newInstance(infoArrayList), Info_Fragment.TAG)
                .commit();

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_UPDATE_LIST)){
                File extFolder = context.getExternalFilesDir(null);
                File file = new File(extFolder, "INFO.dat");
                try{
                    if (file.exists()){
                        FileInputStream fin = new FileInputStream(file);
                        ObjectInputStream oin = new ObjectInputStream(fin);
                        infoArrayList = (ArrayList<Info>) oin.readObject();
                        oin.close();
                    }
                    mFragManager.beginTransaction().replace(R.id.container, List_Fragment.newInstance(infoArrayList), Info_Fragment.TAG).commit();

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
        intentFilter.addAction(ACTION_UPDATE_LIST);
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
        if (id == R.id.action_form) {
            mFragManager.beginTransaction()
                    .replace(R.id.container, Info_Fragment.newInstance(), Info_Fragment.TAG)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getForm() {

    }

    @Override
    public void populateDetail(String fn, String ln, int _age, int _pos) {
        mFragManager.beginTransaction()
                .replace(R.id.container, Detail_Fragment.newInstance(fn, ln, _age, _pos), Detail_Fragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_EXIT_MASK)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void populateDetail() {

    }
}
