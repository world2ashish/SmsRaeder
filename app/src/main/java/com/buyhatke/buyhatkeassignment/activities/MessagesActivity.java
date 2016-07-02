package com.buyhatke.buyhatkeassignment.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.buyhatke.buyhatkeassignment.R;
import com.buyhatke.buyhatkeassignment.adapters.MessageRecyclerViewAdapter;
import com.buyhatke.buyhatkeassignment.utils.Constants;
import com.buyhatke.buyhatkeassignment.utils.Utility;

/**
 * Created by ashishgupta on 02/07/16.
 */
public class MessagesActivity extends AppCompatActivity {

    private MessagesActivity mContext;
    private MessageRecyclerViewAdapter messagesAdapter;
    private String smsAddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        this.mContext = MessagesActivity.this;

        if (getIntent().getExtras() != null) {
            smsAddress = getIntent().getExtras().getString(Constants.SMS_ADDRESS);
        }

        initViews();
    }

    private void initViews() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        RecyclerView messagesRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (messagesRecyclerView != null) {
            messagesRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            messagesRecyclerView.setLayoutManager(layoutManager);
            messagesAdapter = new MessageRecyclerViewAdapter(mContext, Utility.getMessagesForOneAddress(mContext,smsAddress));
            messagesRecyclerView.setAdapter(messagesAdapter);
        }
    }
}
