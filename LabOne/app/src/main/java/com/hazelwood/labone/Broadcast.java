package com.hazelwood.labone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Hazelwood on 9/30/14.
 */
public class Broadcast extends BroadcastReceiver {
    public static final String TAG = "BroadcastTAG";
    static final String ACTION_SAVED_DATA = "com.fullsail.android.ACTION_SAVE_DATA";
    static final String ACTION_DELETE_DATA = "com.fullsail.android.ACTION_DELETE_DATA";
    static final String ACTION_UPDATE_LIST = "com.fullsail.android.ACTION_UPDATE_LIST";


    static final String EXTRA_FIRST_NAME = "com.fullsail.android.EXTRA_FIRST_NAME";
    static final String EXTRA_LAST_NAME = "com.fullsail.android.EXTRA_LAST_NAME";
    static final String EXTRA_AGE = "com.fullsail.android.EXTRA_AGE";




    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<Info> oldArray;
        ArrayList<Info> arrayList = new ArrayList<Info>();
        File extFolder;
        if (intent.getAction().equals(ACTION_SAVED_DATA)){
            String first_name = intent.getStringExtra(EXTRA_FIRST_NAME);
            String last_name = intent.getStringExtra(EXTRA_LAST_NAME);
            int age = intent.getIntExtra(EXTRA_AGE, -1);
            arrayList.add(new Info(first_name, last_name, age));


            extFolder = context.getExternalFilesDir(null);
            File file = new File(extFolder, "INFO.dat");
            try{
                if (file.exists()){
                    FileInputStream fin = new FileInputStream(file);
                    ObjectInputStream oin = new ObjectInputStream(fin);
                    oldArray = (ArrayList<Info>) oin.readObject();
                    arrayList.addAll(oldArray);
                    oin.close();
                }

            } catch (Exception e){
                e.printStackTrace();
            }



            try {
                extFolder = context.getExternalFilesDir(null);
                File extFile = new File(extFolder, "INFO.dat");
                FileOutputStream fos = new FileOutputStream(extFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(arrayList);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            intent = new Intent(ACTION_UPDATE_LIST);
            context.sendBroadcast(intent);

        } else if (intent.getAction().equals(ACTION_DELETE_DATA)){

//            intent.getStringExtra("FIRST_NAME");
//            intent.getStringExtra("LAST_NAME");
//            intent.getStringExtra("PERSON_AGE");
            int position = intent.getIntExtra("PERSON_POSITION", 90390390);
            Log.d(TAG, "THIS POSITION: " + position);
            arrayList.remove(position);

            try {
                extFolder = context.getExternalFilesDir(null);
                File extFile = new File(extFolder, "INFO.dat");
                FileOutputStream fos = new FileOutputStream(extFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(arrayList);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            intent = new Intent(ACTION_UPDATE_LIST);
            context.sendBroadcast(intent);

        }

    }
}
