package com.ikubinfo.pushnotificationclient;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by egorari on 6/7/2017.
 */
public class GCMPushReceiverService extends GcmListenerService {
    private static final String TAG = "GCMListenerService";
    @Override
    public void onMessageReceived(String from, Bundle bundle) {
        Log.d(TAG, "From: " + from);
        String message = bundle.getString("message");
        String title = bundle.getString("title");
        String notification = bundle.getString("notification");
        sendNotification(message, title);
    }

    private void sendNotification(String message, String title){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        //setup notification
        //sound
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //build notification
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noBuilder.build()); //0=id of Notification
    }

}
