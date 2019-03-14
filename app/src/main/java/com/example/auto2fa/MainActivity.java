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
    private NotificationReceiver nReceiver;

    private String TAG = this.getClass().getSimpleName();
    private TextView notificationAccessInstructionsTextView;
    private Button notificationAccessButton;
    private TextView noActionRequiredTextView;


    // CREATION OF NOTIFICATION BUTTON VARIABLES
    //private Button button_notify;
    //private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    //private static final int NOTIFICATION_ID = 0;
    //private NotificationManager mNotifyManager;

    /*
    public void createNotificationChannel()
    {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
    */

    /*
    private NotificationCompat.Builder getNotificationBuilder(){
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_launcher_background);
        return notifyBuilder;
    }
    */

    /*
    public void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //createNotificationChannel();

        // TODO: START LISTENER
        NotificationListener listener = new NotificationListener();

        notificationAccessButton = findViewById(R.id.grantNotificationAccessButton);
        notificationAccessInstructionsTextView = findViewById(R.id.getNotificationAccessInstructions);
        noActionRequiredTextView = findViewById(R.id.noActionView);

        // ask user for notification permissions if necessary
        boolean canAccessNotifications = isNotificationAccessEnabled();

        if (!canAccessNotifications) {
            Log.i(TAG,"Requesting notification access");
            //Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            //startActivity(intent);
            showNoNotificationAccess();
        } else {
            Log.i(TAG,"Can already access notifications");
            showSetupComplete();
            //Toast.makeText(getApplicationContext(), "I have notification access", Toast.LENGTH_SHORT).show();
        }


        // SEND NOTIFICATIONS
        //button_notify = findViewById(R.id.notify);
        /*
        button_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
        */
    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean canAccessNotifications = isNotificationAccessEnabled();

        if (!canAccessNotifications) {
            Log.i(TAG,"Requesting notification access");
            showNoNotificationAccess();
        } else {
            Log.i(TAG,"Can already access notifications");
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


    class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG,"**********  onReceive");
            /*String temp = intent.getStringExtra("notification_event") + "\n" + txtView.getText();
            txtView.setText(temp);*/
        }
    }

}
