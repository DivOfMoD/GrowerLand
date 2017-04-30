package com.example.nick.growerland;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CardHolder extends RecyclerView.ViewHolder {

    public TextView mTitle;
    public TextView mLocation;

    public CardHolder(final View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.name);
        mLocation = (TextView) itemView.findViewById(R.id.location);
    }
}
