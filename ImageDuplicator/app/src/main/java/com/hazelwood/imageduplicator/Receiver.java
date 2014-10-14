package com.hazelwood.imageduplicator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by Hazelwood on 10/10/14.
 */
public class Receiver extends BroadcastReceiver {
    public static final String NEW_PICTURE = "com.android.camera.NEW_PICTURE";
    public static final String HARDWARE_NEW_PICTURE = "com.android.camera.NEW_PICTURE";

    public static final String PICTURE_RECIEVED = "hazelwood.imageduplicated";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NEW_PICTURE)){
            String path = getFileUri(context, intent);
            File file = new File(getFileUri(context,intent));
            if(file.exists()){
                Bitmap picture = BitmapFactory.decodeFile(path);

                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inSampleSize = 10;
                Bitmap tempImg = BitmapFactory.decodeFile(path.toString(),opts);

                intent = new Intent(context, Service.class);
                intent.putExtra("IMAGE", path);
                context.startService(intent);
            }


        }
        else if (intent.getAction().equals(HARDWARE_NEW_PICTURE)){

        }

    }

    public String getFileUri(Context context, Intent capturedIntent) {

        Uri selectedImage = capturedIntent.getData();
        String[] pathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = context.getContentResolver().query(selectedImage, pathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(pathColumn[0]);
        String imagePath = cursor.getString(columnIndex);
        cursor.close();

        return imagePath;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }
}
