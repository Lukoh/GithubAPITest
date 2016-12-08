package com.goforer.mychallenge.model.event;

import com.goforer.base.model.event.ResponseListEvent;

public class ContentsDataEvent extends ResponseListEvent {
    public ContentsDataEvent(boolean isNew) {
        super(isNew);
    }
}
