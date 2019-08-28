package com.example.practicetwo.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.practicetwo.R;

public class ExternalStoragePermissionChecker {
    public static void check(Activity activity, Context context){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            // Checking if need to show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showDialogForUser(activity, context);
            } else {
                //request permission
                requestPermission(activity);
            }
        }
    }

    private static void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RequestCodes.STORAGE_PERMISSIONS_CODE);
    }

    private static void showDialogForUser(final Activity activity, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.explanationDialogTitle);
        builder.setMessage(R.string.explanationDialogMessage);
        builder.setPositiveButton(R.string.okBtn, (dialogInterface, i) -> requestPermission(activity));
        builder.create().show();
    }
}
