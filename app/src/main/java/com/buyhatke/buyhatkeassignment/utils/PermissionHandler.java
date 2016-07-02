package com.buyhatke.buyhatkeassignment.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by ashishgupta on 02/07/16.
 */
public class PermissionHandler {

    public static boolean checkPermission(Context aContext, String[] permissions, int permissionRequestCode) {
        Utility.hideKeyBoard(aContext);
        boolean isPermissionGranted = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(aContext, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    try {
                        isPermissionGranted = false;
                        ActivityCompat.requestPermissions((Activity) aContext, permissions,
                                permissionRequestCode);
                    } catch (Exception e) {
                    }
                    break;
                }
            }
        }
        return isPermissionGranted;
    }
}

