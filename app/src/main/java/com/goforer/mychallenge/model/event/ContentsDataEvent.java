package com.goforer.mychallenge.model.event;

import com.goforer.base.model.event.ResponseListEvent;

public class ContentsDataEvent extends ResponseListEvent {
    private int mType;

    public ContentsDataEvent(boolean isNew, int type) {
        super(isNew);

        mType = type;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

}
