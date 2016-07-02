package com.buyhatke.buyhatkeassignment.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.buyhatke.buyhatkeassignment.R;
import com.buyhatke.buyhatkeassignment.adapters.MessageRecyclerViewAdapter;
import com.buyhatke.buyhatkeassignment.utils.Constants;
import com.buyhatke.buyhatkeassignment.utils.Utility;

/**
 * Created by ashishgupta on 02/07/16.
 */
public class MessagesActivity extends AppCompatActivity implements View.OnClickListener {

    private MessagesActivity mContext;
    private MessageRecyclerViewAdapter messagesAdapter;
    private String smsAddress;
    private EditText messageEdittext;

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
        RecyclerView messagesRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (messagesRecyclerView != null) {
            messagesRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            messagesRecyclerView.setLayoutManager(layoutManager);
            messagesAdapter = new MessageRecyclerViewAdapter(mContext, Utility.getMessagesForOneAddress(mContext, smsAddress));
            messagesRecyclerView.setAdapter(messagesAdapter);
        }

        messageEdittext = (EditText) findViewById(R.id.send_message_text);
        Button sendButton = (Button) findViewById(R.id.send_message_btn);
        if (sendButton != null) {
            sendButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message_btn:
                Utility.sendMessage(mContext, smsAddress, messageEdittext.getText().toString());
                break;
        }
    }
}
