package com.hazelwood.labtwo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Hazelwood on 10/2/14.
 */
public class Grid_Broadcast extends BroadcastReceiver {
    static final String ACTION_SAVED_DATA = "com.hazelwood.labtwo.ACTION_SAVE_DATA";
    static final String ACTION_DELETE_DATA = "com.hazelwood.labtwo.ACTION_DELETE_DATA";
    static final String ACTION_UPDATE_GRID = "com.hazelwood.labtwo.ACTION_UPDATE_GRID";

    static final String IMAGE_BYTES = "com.hazelwood.labtwo.EXTRA_IMAGE_URI";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_SAVED_DATA)){
            byte image = intent.getByteExtra(IMAGE_BYTES, (byte) 0);



        } else if (intent.getAction().equals(ACTION_DELETE_DATA)){

        }

    }
}
