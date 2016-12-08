package com.goforer.mychallenge.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.goforer.base.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class User extends BaseModel implements Parcelable {
    @SerializedName("id")
    private long mId;
    @SerializedName("avatar_url")
    private String mAvatarUrl;
    @SerializedName("gravatar_id")
    private String mAvatarId;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("name")
    private String mName;

    public User() {
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getAvartarUrl() {
        return mAvatarUrl;
    }

    public void setAvartarUrl(String avartarUrl) {
        mAvatarUrl = avartarUrl;
    }

    public String getAvartarId() {
        return mAvatarId;
    }

    public void setAvartarId(String avartarId) {
        mAvatarId = avartarId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    protected User(Parcel in) {
        mId = in.readLong();
        mAvatarUrl = in.readString();
        mAvatarId = in.readString();
        mUrl = in.readString();
        mName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mAvatarUrl);
        dest.writeString(mAvatarId);
        dest.writeString(mUrl);
        dest.writeString(mName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
