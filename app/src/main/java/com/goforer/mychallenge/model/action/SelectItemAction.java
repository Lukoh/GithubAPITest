package com.goforer.mychallenge.model.action;

import com.goforer.mychallenge.model.data.User;

import java.util.List;

public class SelectItemAction {
    private User mUser;
    private List<User> mUserList;
    private int mPosition;

    public User getContents() {
        return mUser;
    }

    public List<User> getContentsList() {
        return mUserList;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setContents(User user) {
        mUser = user;
    }

    public void setContentsList(List<User> userList) {
        mUserList = userList;
    }

    public void setPosition(int position) {
        mPosition = position;
    }
}
