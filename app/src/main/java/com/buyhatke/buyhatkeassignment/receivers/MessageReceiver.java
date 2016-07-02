package com.buyhatke.buyhatkeassignment.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by ashishgupta on 02/07/16.
 */

public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        String code = "";

        if (bundle != null) {
            final Object[] pdusObj = (Object[]) bundle.get("pdus");

            if (pdusObj != null) {
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
//                String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String message = null;
                    if (currentMessage != null) {
                        message = currentMessage.getDisplayMessageBody();
                    }
                }
            }
        }
    }
}