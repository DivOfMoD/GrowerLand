package com.example.nick.growerland;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nick.growerland.database.models.GardenModel;

public class CardCursorAdapter extends RecyclerView.Adapter<CardHolder> {

    private final Cursor mCursor;
    private final Context mContext;
    private final Object mView;

    public CardCursorAdapter(final Cursor pCursor, final Context pContext, final Object mObject) {
        mCursor = pCursor;
        mContext = pContext;
        mView = mObject;
    }

    @Override
    public CardHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(mContext).inflate((Integer) mView, parent, false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardHolder holder, final int position) {
        if (mCursor.moveToPosition(position)) {
            final String name = mCursor.getString(mCursor.getColumnIndex(GardenModel.NAME));
            final String location = mCursor.getString(mCursor.getColumnIndex(GardenModel.PLACE));
            holder.mTitle.setText(name);
            holder.mLocation.setText(location);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}