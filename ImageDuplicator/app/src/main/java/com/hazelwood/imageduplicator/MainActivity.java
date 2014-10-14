package com.hazelwood.imageduplicator;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends Activity implements Grid_Fragment.OnFragmentInteractionListener {
    public static final String ACTION_UPDATE_LIST = "HAZELWOOD_ACTION_UPDATE_LIST";
    public static final String EXTRA_UPDATE_LIST =  "HAZELWOOD_EXTRA_UPDATE_LIST";
    public static final String ACTION_DELETE =  "HAZELWOOD_DELETE";
    public static final String EXTRA_DELETE =  "HAZELWOOD_EXTRA_DELETE";
    public int NOTIFY_ID = 0x00100403;

    FragmentManager mFragManager;
    ArrayList<String> oldStrings;
    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oldStrings = new ArrayList<String>();
        mFragManager = getFragmentManager();

        ArrayList<String> oldStrings = new ArrayList<String>();

        try {
            File extFolder = this.getExternalFilesDir(null);
            File file = new File(extFolder, "PICTURES.dat");

            if (file.exists()) {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream oin = new ObjectInputStream(fin);
                oldStrings = (ArrayList<String>) oin.readObject();
                oin.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        mFragManager.beginTransaction().replace(R.id.container, Grid_Fragment.newInstance(oldStrings),Grid_Fragment.TAG ).commit();

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_UPDATE_LIST)){
                String path = intent.getStringExtra(EXTRA_UPDATE_LIST);

                try {
                    File extFolder = context.getExternalFilesDir(null);
                    File file = new File(extFolder, "PICTURES.dat");

                    if (file.exists()) {
                        FileInputStream fin = new FileInputStream(file);
                        ObjectInputStream oin = new ObjectInputStream(fin);
                        oldStrings = (ArrayList<String>) oin.readObject();
                        oin.close();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

                ArrayList<String> newString = new ArrayList<String>();
                if (oldStrings != null){
                    newString = oldStrings;
                }

                newString.add(path);

                try {
                    File extFolder = context.getExternalFilesDir(null);
                    File extFile = new File(extFolder, "PICTURES.dat");
                    FileOutputStream fos = new FileOutputStream(extFile);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(newString);
                    oos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mFragManager.beginTransaction().replace(R.id.container, Grid_Fragment.newInstance(newString),Grid_Fragment.TAG ).commitAllowingStateLoss();

                Log.d("TAG", path);
            }
            if (intent.getAction().equals(ACTION_DELETE)){
                String path = intent.getStringExtra(EXTRA_DELETE);
                try {
                    File extFolder = context.getExternalFilesDir(null);
                    File file = new File(extFolder, "PICTURES.dat");

                    if (file.exists()) {
                        FileInputStream fin = new FileInputStream(file);
                        ObjectInputStream oin = new ObjectInputStream(fin);
                        oldStrings = (ArrayList<String>) oin.readObject();
                        oin.close();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

                ArrayList<String> newString = new ArrayList<String>();
                for (String string: oldStrings){
                    Log.d("STRING", string);
                    if (path.equals(string)){
                        oldStrings.remove(string);
                    }
                }

                if (oldStrings != null){
                    newString = oldStrings;
                }

                try {
                    File extFolder = context.getExternalFilesDir(null);
                    File extFile = new File(extFolder, "PICTURES.dat");
                    FileOutputStream fos = new FileOutputStream(extFile);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(newString);
                    oos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mFragManager.beginTransaction().replace(R.id.container, Grid_Fragment.newInstance(newString),Grid_Fragment.TAG ).commitAllowingStateLoss();
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(NOTIFY_ID);

            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_LIST);
        intentFilter.addAction(ACTION_DELETE);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
