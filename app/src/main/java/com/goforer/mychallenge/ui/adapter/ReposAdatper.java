package com.goforer.mychallenge.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goforer.base.ui.view.ThumbnailImageView;
import com.goforer.mychallenge.R;
import com.goforer.mychallenge.model.data.Repos;

import java.util.ArrayList;
import java.util.List;

public class ReposAdatper extends BaseAdapter {
    private static final int ITEM_VIEW_TYPE_MAX = 2;

    private final Context mContext;
    private List<Repos> mItems = new ArrayList<>() ;

    public ReposAdatper(final Context context) {
        mContext = context;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX ;
    }


    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewTitleHolder titleholder = null;
        ViewItemHolder itemHolder = null;
        final int viewType = getItemViewType(position);
        final Repos item = mItems.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            switch (viewType) {
                case Repos.LIST_TITLE_TYPE:
                    convertView = inflater.inflate(R.layout.list_repos_title, parent, false);
                    titleholder = new ViewTitleHolder();
                    titleholder.mImageView = (ThumbnailImageView) convertView.findViewById(R.id.iv_image);
                    titleholder.mUserNameView = (TextView) convertView.findViewById(R.id.tv_name);
                    convertView.setTag(titleholder);
                    break;
                case Repos.LIST_ITEM_TYPE:
                    convertView = inflater.inflate(R.layout.list_repos_item, parent, false);
                    itemHolder = new ViewItemHolder();
                    itemHolder.mTvName = (TextView) convertView.findViewById(R.id.tv_name) ;
                    itemHolder.mTvDescription = (TextView) convertView.findViewById(R.id.tv_description) ;
                    itemHolder.mTvCount = (TextView) convertView.findViewById(R.id.tv_count) ;
                    convertView.setTag(itemHolder);
                    break;
            }
        } else {
            switch (viewType) {
                case Repos.LIST_TITLE_TYPE:
                    titleholder = (ViewTitleHolder) convertView.getTag();
                    break;
                case Repos.LIST_ITEM_TYPE:
                    itemHolder = (ViewItemHolder) convertView.getTag();
                    break;
            }
        }

        switch (viewType) {
            case Repos.LIST_TITLE_TYPE:
                titleholder.mImageView.setImage(mContext, item.getAvatarUrl());
                titleholder.mUserNameView.setText(item.getUserName());
                break;
            case Repos.LIST_ITEM_TYPE:
                itemHolder.mTvName.setText(item.getName());
                itemHolder.mTvDescription.setText(item.getDescription());
                itemHolder.mTvCount.setText(String.valueOf(item.getStarCount()));
                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position) ;
    }

    @Deprecated
    public void addItems(List<Repos> reposList) {
        mItems = reposList;
    }

    public void addItem(Repos repos) {
        mItems.add(repos);
    }

    static private class ViewTitleHolder {
        ThumbnailImageView mImageView;
        TextView mUserNameView;
    }

    static private class ViewItemHolder {
        TextView mTvName;
        TextView mTvDescription;
        TextView mTvCount;
    }
}
