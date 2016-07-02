package com.buyhatke.buyhatkeassignment.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.buyhatke.buyhatkeassignment.models.SmsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by ashishgupta on 02/07/16.
 */
public class Utility {
    public static void hideKeyBoard(Context aContext) {
        if (aContext == null) return;
        try {
            View view = ((Activity) aContext).getCurrentFocus();
            if (view != null) {
                try {
                    InputMethodManager imm = (InputMethodManager) aContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {

        }
    }

    public static void readAndSaveAllSms(Context mContext) {
        Cursor cursor = mContext.getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        ArrayList<SmsModel> smsList = null;
        if (cursor != null && cursor.moveToFirst()) {
            smsList = new ArrayList<>();
            do {
                String msgData = "";
                SmsModel smsModel = new SmsModel();
                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                    switch (cursor.getColumnName(idx)) {
                        case "address":
                            smsModel.setAddress(cursor.getString(idx));
                            break;
                        case "date":
                            smsModel.setDate(cursor.getString(idx));
                            break;
                        case "date_sent":
                            smsModel.setDateSent(cursor.getString(idx));
                            break;
                        case "body":
                            smsModel.setBody(cursor.getString(idx));
                            break;
                        case "_id":
                            smsModel.setId(cursor.getString(idx));
                            break;
                        case "service_center":
                            smsModel.setServiceCenter(cursor.getString(idx));
                            break;
                    }
                }
                smsList.add(smsModel);
                smsModel = null;
            } while (cursor.moveToNext());
        } else {
        }
        saveObjDataIntoSharedPref(mContext, Constants.SMS_LIST, smsList);
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }

    public static ArrayList<SmsModel> getAllSmsList(Context mContext) {
        Object smsList = getSavedIntoSharedPrefData(mContext, Constants.SMS_LIST);
        if (smsList != null) {
            return (ArrayList<SmsModel>) smsList;
        }
        return null;
    }

    public static Object getSavedIntoSharedPrefData(Context aContext, String key) {
        try {
            if (aContext != null && key != null) {
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(aContext);
                if (sharedPrefs != null) {
                    Gson gson = new Gson();
                    String json = sharedPrefs.getString(key, null);
                    Type type = null;
                    if (key.equals(Constants.SMS_LIST)) {
                        type = new TypeToken<ArrayList<SmsModel>>() {
                        }.getType();
                        return (Object) gson.fromJson(json, type);
                    }
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    public static void saveObjDataIntoSharedPref(Context aContext, String key, Object aObject) {
        if (aContext != null) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(aContext);
            if (sharedPrefs != null) {
                SharedPreferences.Editor editor = sharedPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(aObject);
                editor.putString(key, json);
                editor.apply();
            }
        }
    }

    public static ArrayList<SmsModel> getMessagesForOneAddress(Context mContext, String smsAddress) {
        if (smsAddress != null) {
            ArrayList<SmsModel> allSms = getAllSmsList(mContext);
            if (allSms != null) {
                ArrayList<SmsModel> messageList = new ArrayList<>();
                for (SmsModel s : allSms) {
                    if (s.getAddress().equals(smsAddress)) {
                        messageList.add(s);
                    }
                }
                return messageList;
            }
        }
        return null;
    }

    public static ArrayList<String> getAllSendersList(Context mContext) {
        ArrayList<SmsModel> allSms = getAllSmsList(mContext);
        if (allSms != null) {
            HashSet<String> sendersSet = new HashSet<>();
            for (SmsModel s : allSms) {
                sendersSet.add(s.getAddress());
            }
            ArrayList<String> sendersArr = new ArrayList<>();
            for (String s : sendersSet) {
                sendersArr.add(s);
            }
            sendersSet = null;
            return sendersArr;
        }
        return null;
    }

    public static void sendMessage(Context context, String smsAddress, String message) {
        if (smsAddress == null || message == null) {
            return;
        }
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(smsAddress, null, message, null, null);
            Toast.makeText(context, "Sms sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Sms failed", Toast.LENGTH_SHORT).show();
        }
    }
}
