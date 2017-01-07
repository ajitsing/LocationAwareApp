package com.singh.ajit.locationawareapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationGenerator extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
    String locationDetails = intent.getStringExtra("detail");

    Notification notification = builder.setContentTitle("Gotcha!")
        .setContentText(locationDetails)
        .setSmallIcon(R.mipmap.ic_launcher).build();

    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(0, notification);
  }
}
