package com.goforer.mychallenge.model.event;

import com.goforer.base.model.event.ResponseUserEvent;

/**
 * Created by USER on 2016-12-08.
 */

public class ContentsUserDataEvent extends ResponseUserEvent {
    private int mType;

    public ContentsUserDataEvent(boolean isNew, int type) {
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