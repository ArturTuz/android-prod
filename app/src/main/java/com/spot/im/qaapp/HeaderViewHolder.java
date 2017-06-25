package com.spot.im.qaapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by nissimpardo on 23/06/2017.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder implements BaseViewHolder {

    private TextView mTextView;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.textView);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mTextView.setOnClickListener(listener);
    }

    @Override
    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setIndex(int index) {
        mTextView.setId(index);
    }
}
