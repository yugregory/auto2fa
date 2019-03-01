package com.example.auto2fa;

import android.content.Context;
import android.widget.Toast;

public class Util {

    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
