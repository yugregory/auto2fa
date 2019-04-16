package com.example.auto2fa;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    // RECEIVER VARIABLES
    //private NotificationReceiver nReceiver;

    private String TAG = this.getClass().getSimpleName();
    private TextView notificationAccessInstructionsTextView;
    private Button notificationAccessButton;
    private Button toggleButton;
    private TextView noActionRequiredTextView;
    private TextView offText;
    private TextView onText;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NotificationListener listener = new NotificationListener();

        notificationAccessButton = findViewById(R.id.grantNotificationAccessButton);
        notificationAccessInstructionsTextView = findViewById(R.id.getNotificationAccessInstructions);
        noActionRequiredTextView = findViewById(R.id.noActionView);
        toggleButton = findViewById(R.id.toggleButton);
        offText = findViewById(R.id.offText);
        onText = findViewById(R.id.onText);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("TogglePref", 0);
        ToggleButton toggle = (ToggleButton) toggleButton;

        final SharedPreferences.Editor editor = sharedPref.edit();

        offText.setVisibility(View.GONE);
        onText.setVisibility(View.GONE);

        if (!sharedPref.contains("isChecked")){
            editor.putBoolean("isChecked", true);
            editor.commit();
        }

        if (sharedPref.getBoolean("isChecked", false)){
            toggle.setChecked(true);
            offText.setVisibility(View.GONE);
            onText.setVisibility(View.VISIBLE);
        } else {
            toggle.setChecked(false);
            onText.setVisibility(View.GONE);
            offText.setVisibility(View.VISIBLE);
        }

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    offText.setVisibility(View.GONE);
                    onText.setVisibility(View.VISIBLE);
                    listener.setChecker(true);
                    editor.putBoolean("isChecked", true);
                    editor.commit();

                } else {
                    onText.setVisibility(View.GONE);
                    offText.setVisibility(View.VISIBLE);
                    listener.setChecker(false);
                    editor.putBoolean("isChecked", false);
                    editor.commit();
                }
            }
        });

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

        toggleButton.setVisibility(View.GONE);
        offText.setVisibility(View.GONE);
        onText.setVisibility(View.GONE);
        noActionRequiredTextView.setVisibility(View.GONE);
    }

    private void showSetupComplete() {
        // noActionRequiredTextView.setVisibility(View.VISIBLE);
        toggleButton.setVisibility(View.VISIBLE);
        offText.setVisibility(View.VISIBLE);
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
    public void sendNotification(View view) {

        //Get an instance of NotificationManager//
        String CHANNEL_ID = "HI";

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

        // Configure the notification channel.
        notificationChannel.setDescription("Channel description");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
        notificationChannel.enableVibration(true);
        notificationManager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        mBuilder.setContentTitle("My notification");
        mBuilder.setContentText("Hello World!");


        // When you issue multiple notifications about the same type of event,
        // it’s best practice for your app to try to update an existing notification
        // with this new information, rather than immediately creating a new notification.
        // If you want to update this notification at a later date, you need to assign it an ID.
        // You can then use this ID whenever you issue a subsequent notification.
        // If the previous notification is still visible, the system will update this existing notification,
        // rather than create a new one. In this example, the notification’s ID is 001//


        notificationManager.notify(2, mBuilder.build());
    }
    */
}
