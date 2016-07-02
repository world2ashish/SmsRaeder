package com.buyhatke.buyhatkeassignment.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.buyhatke.buyhatkeassignment.interfaces.SmsListener;
import com.buyhatke.buyhatkeassignment.utils.Constants;
import com.buyhatke.buyhatkeassignment.utils.Utility;

/**
 * Created by ashishgupta on 02/07/16.
 */
public class CustomAsyncTask extends AsyncTask<String, String, String> {
    private final Context mContext;
    private final int taskType;

    public CustomAsyncTask(Context mContext, int type) {
        this.mContext = mContext;
        this.taskType = type;
    }

    @Override
    protected String doInBackground(String... params) {
        if (taskType == Constants.TYPE_READ_SMS) {
            Utility.readAndSaveAllSms(mContext);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (taskType == Constants.TYPE_READ_SMS) {
            ((SmsListener) mContext).onSmsReadComplete();
        }
    }
}
