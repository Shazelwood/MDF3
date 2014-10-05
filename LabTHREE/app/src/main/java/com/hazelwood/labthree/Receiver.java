package com.hazelwood.labthree;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Hazelwood on 10/4/14.
 */
public class Receiver extends BroadcastReceiver {
    public static final String ACTION_SAVE =  "HAZELWOOD_ACTION_SAVE";
    public static final String EXTRA_SAVE =  "HAZELWOOD_EXTRA_SAVE";
    public static final String ACTION_UPDATE_LIST =  "HAZELWOOD_ACTION_UPDATE_LIST";
    public static final String EXTRA_UPDATE_LIST =  "HAZELWOOD_EXTRA_UPDATE_LIST";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_SAVE)){
            NYTimes nyTimes = (NYTimes) intent.getSerializableExtra(EXTRA_SAVE);

            try {
                File extFolder = context.getExternalFilesDir(null);
                File extFile = new File(extFolder, nyTimes.getId() + "NYTimes.dat");
                FileOutputStream fos = new FileOutputStream(extFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(nyTimes);
                oos.close();
            } catch (Exception e){
                e.printStackTrace();
            }

            intent = new Intent(ACTION_UPDATE_LIST);
            intent.putExtra(EXTRA_UPDATE_LIST, nyTimes);
            context.sendBroadcast(intent);

            Log.d("SAVE", nyTimes.getTitle());

        }

    }
}
