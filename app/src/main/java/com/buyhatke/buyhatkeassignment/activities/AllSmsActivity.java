package com.buyhatke.buyhatkeassignment.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.buyhatke.buyhatkeassignment.R;
import com.buyhatke.buyhatkeassignment.adapters.SMSRecyclerViewAdapter;
import com.buyhatke.buyhatkeassignment.interfaces.SmsListener;
import com.buyhatke.buyhatkeassignment.interfaces.SmsRowClickListener;
import com.buyhatke.buyhatkeassignment.tasks.CustomAsyncTask;
import com.buyhatke.buyhatkeassignment.utils.Constants;
import com.buyhatke.buyhatkeassignment.utils.PermissionHandler;
import com.buyhatke.buyhatkeassignment.utils.Utility;

public class AllSmsActivity extends AppCompatActivity implements SmsListener {

    private Context mContext;
    private SMSRecyclerViewAdapter smsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mContext = AllSmsActivity.this;
        initViews();

        boolean isPermissionGranted = PermissionHandler.checkPermission(mContext,
                Constants.SMS_PERMISSIONS,
                Constants.SMS_PERMISSION_CODE);
        if (isPermissionGranted) {
            new CustomAsyncTask(mContext, Constants.TYPE_READ_SMS).execute();
        }
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView smsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (smsRecyclerView != null) {
            smsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            smsRecyclerView.setLayoutManager(layoutManager);
            SmsRowClickListener smsRowClickListener = new SmsRowClickListener() {
                @Override
                public void onSmsClicked(String address) {
                    Intent messageActivityIntent = new Intent(mContext, MessagesActivity.class);
                    messageActivityIntent.putExtra(Constants.SMS_ADDRESS, address);
                    startActivity(messageActivityIntent);
                }
            };
            smsAdapter = new SMSRecyclerViewAdapter(mContext, smsRowClickListener);
            smsRecyclerView.setAdapter(smsAdapter);
        }
    }

    @Override
    public void onSmsReadComplete() {
        String[] smsSendersArray = Utility.getAllSendersList(mContext);
        smsAdapter.setSmsSendersArr(smsSendersArray);
        smsAdapter.notifyDataSetChanged();
    }
}
