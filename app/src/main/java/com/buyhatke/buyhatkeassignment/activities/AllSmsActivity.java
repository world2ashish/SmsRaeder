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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.buyhatke.buyhatkeassignment.R;
import com.buyhatke.buyhatkeassignment.adapters.SMSRecyclerViewAdapter;
import com.buyhatke.buyhatkeassignment.interfaces.SmsListener;
import com.buyhatke.buyhatkeassignment.interfaces.SmsRowClickListener;
import com.buyhatke.buyhatkeassignment.models.SmsModel;
import com.buyhatke.buyhatkeassignment.tasks.CustomAsyncTask;
import com.buyhatke.buyhatkeassignment.utils.Constants;
import com.buyhatke.buyhatkeassignment.utils.PermissionHandler;
import com.buyhatke.buyhatkeassignment.utils.Utility;

import java.util.ArrayList;

public class AllSmsActivity extends AppCompatActivity implements SmsListener {

    private Context mContext;
    private SMSRecyclerViewAdapter smsAdapter;
    private RecyclerView smsRecyclerView;

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

        EditText searchEdittext = (EditText) findViewById(R.id.search_edittext);
        if (searchEdittext != null) {
            searchEdittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable input) {
                    ArrayList<SmsModel> allSms = Utility.getAllSmsList(mContext);
                    if (smsAdapter == null || allSms == null
                            || allSms.size() == 0 || input == null) {
                        return;
                    }
                    String s = null;
                    s = input.toString();

                    if (s.length() > 0) {
                        ArrayList<String> tempSendersList = null;
                        tempSendersList = new ArrayList<>();
                        for (SmsModel sms : allSms) {
                            if (sms.getAddress() != null && sms.getBody().toLowerCase().contains(s.toLowerCase())) {
                                tempSendersList.add(sms.getAddress());
                            }
                        }
                        if (tempSendersList.size() > 0) {
                            smsAdapter.setSmsSendersArr(tempSendersList);
//                            smsRecyclerView.setAdapter(smsAdapter);
                            smsAdapter.notifyDataSetChanged();
                        } else {
                            smsAdapter.setSmsSendersArr(null);
//                            recyclerViewContacts.setAdapter(smsAdapter);
                            smsAdapter.notifyDataSetChanged();
                        }
                    } else {
                        smsAdapter.setSmsSendersArr(Utility.getAllSendersList(mContext));
//                        recyclerViewContacts.setAdapter(smsAdapter);
                        smsAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        smsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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
        smsAdapter.setSmsSendersArr(Utility.getAllSendersList(mContext));
        smsAdapter.notifyDataSetChanged();
    }
}
