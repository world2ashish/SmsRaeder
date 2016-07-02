package com.buyhatke.buyhatkeassignment.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buyhatke.buyhatkeassignment.R;
import com.buyhatke.buyhatkeassignment.interfaces.SmsRowClickListener;

/**
 * Created by ashishgupta on 02/07/16.
 */
public class SMSRecyclerViewAdapter extends RecyclerView.Adapter<SMSRecyclerViewAdapter.SmsViewHolder> {
    private final Context mContext;
    private final SmsRowClickListener smsRowClickListener;
    private String[] smsSendersArray;

    public SMSRecyclerViewAdapter(Context mContext, SmsRowClickListener smsRowClickListener) {
        this.smsRowClickListener = smsRowClickListener;
        this.mContext = mContext;
    }

    @Override
    public SmsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.sms_row, viewGroup, false);
        return new SmsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SmsViewHolder holder, int position) {
        final String smsSender = smsSendersArray[holder.getAdapterPosition()];
        if (smsSender != null) {
            holder.smsRowRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    smsRowClickListener.onSmsClicked(smsSender);
                }
            });
            holder.smsSenderNameTv.setText(smsSender);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return smsSendersArray != null ? smsSendersArray.length : 0;
    }

    public void setSmsSendersArr(String[] smsSendersArray) {
        this.smsSendersArray = smsSendersArray;
    }

    public class SmsViewHolder extends RecyclerView.ViewHolder {

        private final TextView smsSenderNameTv;
        private final RelativeLayout smsRowRelativeLayout;

        public SmsViewHolder(View v) {
            super(v);
            smsSenderNameTv = (TextView) v.findViewById(R.id.sms_sender_name);
            smsRowRelativeLayout = (RelativeLayout) v.findViewById(R.id.sms_row_relative_layout);
        }
    }
}