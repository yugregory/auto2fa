package com.example.auto2fa;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Arrays;

public class NotificationListener extends NotificationListenerService {
    private String duoPackageName = "com.duosecurity.duomobile";
    private String duoNotificationTitle = "Duo Mobile";

    private String TAG = this.getClass().getSimpleName();
    static private boolean checker = true;

    public void setChecker(boolean value) {
        checker = value;
        Log.d(TAG, "*****checker has been changed to: " + value);
    }

    public boolean getChecker() {
        return checker;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d(TAG, "*****in onNotificationPosted");
        Log.d(TAG, "*****checker is: " + checker);
        if (checker == true) {
            Log.d(TAG, "*****hello");
            String notificationPackageName = sbn.getPackageName();
            Notification n = sbn.getNotification();

            if (notificationPackageName.equals(duoPackageName)) {
                if (n.extras.containsKey("android.title")) {
                    String notificationTitle = n.extras.get("android.title").toString();
                    if (duoNotificationTitle.equals(notificationTitle)) {
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
        }
    }
}