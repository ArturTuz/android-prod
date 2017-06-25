package com.spot.im.qaapp;

import android.view.View;

/**
 * Created by nissimpardo on 23/06/2017.
 */



public class ViewHolderFactory {

    static BaseViewHolder viewHolder(int viewType, View view, View.OnClickListener listener) {
        if (viewType == 0) {
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(view);
            headerViewHolder.setOnClickListener(listener);
            return new HeaderViewHolder(view);
        }
        return new InputViewHolder(view);
    }
}
