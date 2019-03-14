package com.example.auto2fa;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
//import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // RECEIVER VARIABLES
    //private NotificationReceiver nReceiver;

    private String TAG = this.getClass().getSimpleName();
    private TextView notificationAccessInstructionsTextView;
    private Button notificationAccessButton;
    private TextView noActionRequiredTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationListener listener = new NotificationListener();

        notificationAccessButton = findViewById(R.id.grantNotificationAccessButton);
        notificationAccessInstructionsTextView = findViewById(R.id.getNotificationAccessInstructions);
        noActionRequiredTextView = findViewById(R.id.noActionView);

        // ask user for notification permissions if necessary
        boolean canAccessNotifications = isNotificationAccessEnabled();

        if (!canAccessNotifications) {
            showNoNotificationAccess();
        } else {
            showSetupComplete();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean canAccessNotifications = isNotificationAccessEnabled();

        if (!canAccessNotifications) {
            showNoNotificationAccess();
        } else {
            showSetupComplete();
        }
    }

    private void showNoNotificationAccess() {
        notificationAccessInstructionsTextView.setVisibility(View.VISIBLE);
        notificationAccessButton.setVisibility(View.VISIBLE);

        noActionRequiredTextView.setVisibility(View.GONE);
    }

    private void showSetupComplete() {
        noActionRequiredTextView.setVisibility(View.VISIBLE);

        notificationAccessInstructionsTextView.setVisibility(View.GONE);
        notificationAccessButton.setVisibility(View.GONE);
    }


    //check notification access setting is enabled or not
    public boolean isNotificationAccessEnabled() {
        try{
            return Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    "enabled_notification_listeners").contains(getApplicationContext().getPackageName());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getNotificationAccess(View view) {
        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        startActivity(intent);
    }

    /*
    class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG,"**********  onReceive");
            //String temp = intent.getStringExtra("notification_event") + "\n" + txtView.getText();
            //txtView.setText(temp);
        }
    }
    */

}
