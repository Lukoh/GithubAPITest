package com.goforer.base.model.event;

/**
 * Created by USER on 2016-12-08.
 */

public class ResponseUserEvent extends ResponseEvent {
    private boolean mIsNew = true;

    public ResponseUserEvent() {
        this(false);
    }

    public ResponseUserEvent(boolean isNew) {
        mIsNew = isNew;
    }

    public boolean isNew() {
        return mIsNew;
    }
}
