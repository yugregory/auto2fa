package com.example.auto2fa;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

public class NotificationListener extends NotificationListenerService{
    private String duoPackageName = "com.duosecurity.duomobile";

    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        String notificationPackageName = sbn.getPackageName();
        Notification n = sbn.getNotification();

        if (notificationPackageName.equals(duoPackageName)) {
            try {
                if (n.actions != null && n.actions.length > 0) {
                    n.actions[0].actionIntent.send();
                }
            } catch (android.app.PendingIntent.CanceledException e) {
                //Log.e(TAG, e.getMessage());
            }
        }

    }
}