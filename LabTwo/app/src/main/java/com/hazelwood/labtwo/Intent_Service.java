package com.hazelwood.labtwo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hazelwood on 10/2/14.
 */
public class Intent_Service extends IntentService {
    static final String ACTION_SAVED_DATA = "com.hazelwood.labtwo.ACTION_SAVE_DATA";
    static final String ACTION_UPDATE_GRID = "com.hazelwood.labtwo.ACTION_UPDATE_GRID";

    static final String IMAGE_BYTES = "com.hazelwood.labtwo.EXTRA_IMAGE_URI";


    public Intent_Service() {
        super("Hazelwood_IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        byte[] imagesBytes;

        String[] strings = {
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/bobmarley_exclusive_34.jpg",
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/tumblr_lyzb8emhHI1qm9rypo1_1280.jpg",
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/marley.jpg",
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/david-burnett-bob-soccer-685x1024.jpg",
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/AsoRLq-CMAABDPr.jpg",
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/bob-marley-soccer-1024x694.jpg",
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/bob-marley-soccer-football-dread-rasta-4.jpg",
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/bob-marley-soccer-football-dread-rasta-2-767x1024.jpg",
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/bob-marley-playing-soccer07.jpg",
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/a180e1536f1de71d67adcd8da981fc3f.jpg",
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/8540820074_3349f407cc_z.jpg",
                "http://bobmarley.cdn.junip.com/wp-content/uploads/2013/11/5152313914_e0e19047a8_o.jpg"
        };

        for (int i = 0; i < strings.length; i++){
            try {
                URL url = new URL(strings[i]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                imagesBytes = IOUtils.toByteArray(is);
                is.close();
                connection.disconnect();

                File extFolder = getExternalFilesDir(null);
                File extFile = new File(extFolder, i + "IMAGES.dat");
                FileOutputStream fos = new FileOutputStream(extFile);
                fos.write(imagesBytes);
                fos.close();
                Log.d("SERVICE", "SENT");

                intent = new Intent(ACTION_UPDATE_GRID);
                sendBroadcast(intent);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
