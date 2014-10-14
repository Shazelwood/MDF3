package com.hazelwood.imageduplicator;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.io.File;

public class Service extends IntentService {
    public int NOTIFY_ID = 0x00100403;

    public static final String ACTION_UPDATE_LIST =  "HAZELWOOD_ACTION_UPDATE_LIST";
    public static final String EXTRA_UPDATE_LIST =  "HAZELWOOD_EXTRA_UPDATE_LIST";

    public static final String ACTION_DELETE =  "HAZELWOOD_DELETE";
    public static final String EXTRA_DELETE =  "HAZELWOOD_EXTRA_DELETE";

    public static final String PICTURE_RECIEVED = "hazelwood.imageduplicated";

    public Service() {
        super("HAZELWOOD_Service");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onHandleIntent(Intent intent) {
        String path = intent.getStringExtra("IMAGE");
//            Bitmap picture = BitmapFactory.decodeFile(path);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 10;
        Bitmap image = BitmapFactory.decodeFile(path.toString(), opts);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_stat_name);

        NotificationManager notificationManager = (NotificationManager) Service.this.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Service.this);
        builder.setSmallIcon(android.R.drawable.ic_menu_gallery);
        builder.setLargeIcon(image);
        builder.setContentTitle("NEW IMAGE");

        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.setBigContentTitle("NEW IMAGE");
        style.bigPicture(image);
        style.bigLargeIcon(largeIcon);
        builder.setStyle(style);

        intent = new Intent(Intent.ACTION_VIEW);
        File imageFILE = new File(path);
        intent.setDataAndType(Uri.fromFile(imageFILE), "image/*");
        PendingIntent pendingIntent1 = PendingIntent.getActivity(Service.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent1);

        //Share
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
        PendingIntent pendingIntent2 = PendingIntent.getActivity(Service.this, 0, share, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(android.R.drawable.ic_menu_share, "SHARE", pendingIntent2);

        //Delete
        intent = new Intent(ACTION_DELETE);
        intent.putExtra(EXTRA_DELETE, path);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(Service.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(android.R.drawable.ic_menu_delete, "DELETE", pendingIntent3);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(NOTIFY_ID, notification);

        intent = new Intent(ACTION_UPDATE_LIST);
        intent.putExtra(EXTRA_UPDATE_LIST, path);
        this.sendBroadcast(intent);


    }
}
