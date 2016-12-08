package com.goforer.base.model.event;

import com.goforer.mychallenge.model.data.Repos;

/**
 * Created by USER on 2016-12-08.
 */

public class ResponseReposEvent {
    protected Repos mResponse;
    protected String mTag;

    public boolean isMine(String tag){
        return tag == null || tag.equals(mTag);
    }

    public void parseInResponse() {
    }

    public Repos getResponseClient() { return mResponse; }

    public String getTag() { return mTag; }

    public void setResponseClient(Repos responses) { mResponse = responses; }

    public void setTag(String tag) { mTag = tag; }
}
