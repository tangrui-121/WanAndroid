package com.example.library.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mr'Tang on 2018/4/17.
 */

public class T {
    private static Toast toast;

    public static void showShort(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(message);
        }
        toast.show();
    }

    public static void showShort(Context context, int message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(message);
        }
        toast.show();
    }

    public static void showLong(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(message);
        }
        toast.show();
    }

    public static void showLong(Context context, int message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(message);
        }
        toast.show();
    }

    public static void show(Context context, CharSequence message, int duration) {
        if (null == toast) {
            toast = Toast.makeText(context, message, duration);
        } else {
            toast.setDuration(duration);
            toast.setText(message);
        }
        toast.show();
    }

    public static void show(Context context, int message, int duration) {
        if (null == toast) {
            toast = Toast.makeText(context, message, duration);
        } else {
            toast.setDuration(duration);
            toast.setText(message);
        }
        toast.show();
    }

    public static void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }
}
