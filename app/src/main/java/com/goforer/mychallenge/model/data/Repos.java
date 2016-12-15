package com.goforer.mychallenge.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.goforer.base.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Repos extends BaseModel implements Parcelable {
    public static final int LIST_TITLE_TYPE = 0;
    public static final int LIST_ITEM_TYPE = 1;

    @SerializedName("id")
    private long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("owner")
    private Owner mOwner;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("stargazers_count")
    private int mStarCount;

    private String mAvatarUrl;
    private String mUserName;

    private int mType;

    public Repos() {
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Owner get0wner() {
        return mOwner;
    }

    public void setOwner(Owner owner) {
        mOwner = owner;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getStarCount() {
        return mStarCount;
    }

    public void setStarCount(int starCount) {
        mStarCount = starCount;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvartarUrl(String avartarUrl) {
        mAvatarUrl = avartarUrl;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    protected Repos(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mOwner = in.readParcelable(Owner.class.getClassLoader());
        mDescription = in.readString();
        mStarCount = in.readInt();
        mAvatarUrl = in.readString();
        mUserName = in.readString();
        mType = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeParcelable(mOwner, flags);
        dest.writeString(mDescription);
        dest.writeInt(mStarCount);
        dest.writeString(mAvatarUrl);
        dest.writeString(mUserName);
        dest.writeInt(mType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Repos> CREATOR = new Parcelable.Creator<Repos>() {
        @Override
        public Repos createFromParcel(Parcel in) {
            return new Repos(in);
        }

        @Override
        public Repos[] newArray(int size) {
            return new Repos[size];
        }
    };

    public final static class Owner implements Parcelable {
        @SerializedName("id")
        private long mId;
        @SerializedName("avatar_url")
        private String mAvartarUrl;
        @SerializedName("repos_url")
        private String mReposUrl;

        public long getId() {
            return mId;
        }

        public String getAvartarUrl() {
            return mAvartarUrl;
        }

        public String getReposUrl() {
            return mReposUrl;
        }

        protected Owner(Parcel in) {
            mId = in.readLong();
            mAvartarUrl = in.readString();
            mReposUrl = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(mId);
            dest.writeString(mAvartarUrl);
            dest.writeString(mReposUrl);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<Owner> CREATOR
                = new Parcelable.Creator<Owner>() {
            @Override
            public Owner createFromParcel(Parcel in) {
                return new Owner(in);
            }

            @Override
            public Owner[] newArray(int size) {
                return new Owner[size];
            }
        };
    }
}