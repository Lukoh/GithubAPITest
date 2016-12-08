package com.goforer.mychallenge.model.action;

import android.support.v7.widget.RecyclerView;

public class MoveItemAction {
    private RecyclerView.LayoutManager mLayoutManager;
    private int mPosition;

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public void setPosition(int position) {
        mPosition = position;
    }
}
