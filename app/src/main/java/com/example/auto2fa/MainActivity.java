package com.example.auto2fa;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    // RECEIVER VARIABLES
    //private NotificationReceiver nReceiver;

    private String TAG = this.getClass().getSimpleName();
    private TextView notificationAccessInstructionsTextView;
    private Button notificationAccessButton;
    private Button toggleButton;
    private TextView noActionRequiredTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NotificationListener listener = new NotificationListener();

        notificationAccessButton = findViewById(R.id.grantNotificationAccessButton);
        notificationAccessInstructionsTextView = findViewById(R.id.getNotificationAccessInstructions);
        noActionRequiredTextView = findViewById(R.id.noActionView);
        toggleButton = findViewById(R.id.toggleButton);

        ToggleButton toggle = (ToggleButton) toggleButton;
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listener.setChecker(true);
                } else {
                    listener.setChecker(false);
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
        noActionRequiredTextView.setVisibility(View.GONE);
    }

    private void showSetupComplete() {
        // noActionRequiredTextView.setVisibility(View.VISIBLE);
        toggleButton.setVisibility(View.VISIBLE);
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


}
