package com.goforer.mychallenge.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goforer.mychallenge.R;
import com.goforer.mychallenge.model.data.Repos;

import java.util.ArrayList;

public class ReposAdatper extends BaseAdapter {
    private final Context mContext;
    private ArrayList<Repos> mItems = new ArrayList<>() ;

    public ReposAdatper(final Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        final Repos item = mItems.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_repos_item, parent, false);
            holder = new ViewHolder();
            holder.mTvName = (TextView) convertView.findViewById(R.id.tv_name) ;
            holder.mTvDescription = (TextView) convertView.findViewById(R.id.tv_description) ;
            holder.mTvCount = (TextView) convertView.findViewById(R.id.tv_count) ;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTvName.setText(item.getName());
        holder.mTvDescription.setText(item.getDescription());
        holder.mTvCount.setText(String.valueOf(item.getStarCount()));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position) ;
    }

    public void addItem(Repos repos) {
        mItems.add(repos);
    }

    static class ViewHolder {
        TextView mTvName;
        TextView mTvDescription;
        TextView mTvCount;
    }
}
