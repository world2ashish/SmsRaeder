package com.buyhatke.buyhatkeassignment.utils;

import android.Manifest;

/**
 * Created by ashishgupta on 02/07/16.
 */
public class Constants {
    public static final int SMS_PERMISSION_CODE = 1;
    public static final String[] SMS_PERMISSIONS =
            new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};

    public static final int TYPE_READ_SMS = 2;
    public static final String SMS_LIST = "SMS_LIST";
    public static final String SMS_ADDRESS = "SMS_ADDRESS";
}
