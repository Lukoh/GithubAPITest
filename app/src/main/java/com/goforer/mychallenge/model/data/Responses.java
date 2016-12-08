package com.goforer.mychallenge.model.data;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class Responses {
    public static final int GENERAL_ERROR = -1;
    public static final int NETWORK_ERROR = -2;
    public static final int SUCCESSFUL = 1;

    public static final String OK = "ok";

    @SerializedName("stat")
    private String mStat;
    @SerializedName("page")
    private int mPage;
    @SerializedName("total_page")
    private int mTotalPage;
    @SerializedName("photos")
    private JsonElement mPhotos;

    private int mStatus;

    public String getStat() {
        return mStat;
    }

    public int getPage() {
        return mPage;
    }

    public int getTotalPage() {
        return mTotalPage;
    }

    public JsonElement getPhotos() {
        return mPhotos;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public boolean isSuccessful(){
        if (OK.equals(mStat)) {
            return true;
        } else {
            return false;
        }
    }
}
