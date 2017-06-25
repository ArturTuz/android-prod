package com.spot.im.qaapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

/**
 * Created by nissimpardo on 23/06/2017.
 */

public class InputViewHolder extends RecyclerView.ViewHolder implements BaseViewHolder {
    private EditText mEditText;

    public InputViewHolder(View itemView) {
        super(itemView);
        mEditText = (EditText) itemView.findViewById(R.id.editText2);
    }

    @Override
    public void setText(String text) {
        mEditText.setHint(text);
    }

    public String getValue() {
        return mEditText.getText().toString();
    }
}
