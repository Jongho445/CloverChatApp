package com.example.cloverchatapp.util;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.cloverchatapp.MainActivity;

public class DialogRenderer {

    public static void showConfirmDialog(
            MainActivity activity, String msg,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener
    ) {
        new AlertDialog.Builder(activity)
                .setTitle("알림")
                .setMessage(msg)
                .setPositiveButton("확인", positiveListener)
                .setNegativeButton("취소", negativeListener)
                .show();
    }

    public static void showAlertDialog(MainActivity activity, String msg) {
        new AlertDialog.Builder(activity)
                .setTitle("알림")
                .setMessage(msg)
                .setPositiveButton("확인", null)
                .show();
    }
}
