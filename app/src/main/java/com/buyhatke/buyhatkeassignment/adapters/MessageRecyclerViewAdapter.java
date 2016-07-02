package com.buyhatke.buyhatkeassignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buyhatke.buyhatkeassignment.R;
import com.buyhatke.buyhatkeassignment.models.SmsModel;

import java.util.ArrayList;

/**
 * Created by ashishgupta on 02/07/16.
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.MessageViewHolder> {

    private final Context mContext;
    private final ArrayList<SmsModel> messages;

    public MessageRecyclerViewAdapter(Context mContext, ArrayList<SmsModel> messages) {
        this.messages = messages;
        this.mContext = mContext;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.message_row, viewGroup, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        SmsModel smsModel = messages.get(holder.getAdapterPosition());
        if (smsModel != null) {
            holder.messageBodyTV.setText(smsModel.getBody());
        }
    }

    @Override
    public int getItemCount() {
        return messages != null ? messages.size() : 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private final TextView messageBodyTV;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageBodyTV = (TextView) itemView.findViewById(R.id.message_body);
        }
    }
}
