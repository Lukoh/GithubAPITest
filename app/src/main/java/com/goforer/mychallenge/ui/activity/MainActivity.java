package com.goforer.mychallenge.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goforer.base.model.event.ResponseEvent;
import com.goforer.base.model.event.ResponseListEvent;
import com.goforer.base.ui.view.ThumbnailImageView;
import com.goforer.mychallenge.R;
import com.goforer.mychallenge.model.data.Repos;
import com.goforer.mychallenge.model.data.User;
import com.goforer.mychallenge.model.data.sort.ReposComparator;
import com.goforer.mychallenge.model.event.ContentsDataEvent;
import com.goforer.mychallenge.model.event.ContentsUserDataEvent;
import com.goforer.mychallenge.ui.adapter.ReposAdatper;
import com.goforer.mychallenge.utility.ConnectionUtils;
import com.goforer.mychallenge.web.Intermediary;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String USER_NAME = "user";

    private ListView mListView;
    private ReposAdatper mAdapter;

    private ThumbnailImageView mImageView;
    private TextView mNameView;

    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);

        if (!ConnectionUtils.isNetworkAvailable(this)) {
            showMessage(getResources().getString(R.string.not_connected_internet));
            return;
        }

        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            mUserName = uri.getQueryParameter(USER_NAME);
        } else {
            mUserName = "jakewharton";
        }

        mImageView = (ThumbnailImageView) findViewById(R.id.iv_image);
        mNameView = (TextView) findViewById(R.id.tv_name);
        mListView = (ListView) findViewById(R.id.lv_repos);

        mAdapter = new ReposAdatper(this);

        ContentsUserDataEvent userEvent = new ContentsUserDataEvent(true, 0);
        Intermediary.INSTANCE.getUser(getApplicationContext(), mUserName, userEvent);

        ContentsDataEvent reposEvent = new ContentsDataEvent(true, 1);
        Intermediary.INSTANCE.getRepos(getApplicationContext(), mUserName, reposEvent);
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("")
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(ContentsUserDataEvent event) {
        if (event.getResponseClient().equals(null)) {
            showMessage(getResources().getString(R.string.no_result));

            return;
        }

        new parseUserTask(this, event).execute();
    }

    @SuppressWarnings("")
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(ContentsDataEvent event) {
        if (event.getResponseClient().equals(null) || event.getResponseClient().isEmpty()) {
            showMessage(getResources().getString(R.string.no_result));

            return;
        }

        new parsePeposTask(this, event).execute();
    }

    private class parseUserTask extends AsyncTask<Void, Void, User> {
        private ResponseEvent mEvent;
        private WeakReference<MainActivity> activityWeakRef;

        private parseUserTask(MainActivity activity, ResponseEvent event) {
            mEvent = event;

            activityWeakRef = new WeakReference<>(activity);
        }

        @Override
        protected User doInBackground(Void... params) {
            return mEvent.getResponseClient();
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            MainActivity activity = activityWeakRef.get();
            if (activity != null) {
                if (user == null) {
                    showMessage(getResources().getString(R.string.no_result));
                } else {
                    mImageView.setImage(getApplicationContext(), user.getAvartarUrl());
                    mNameView.setText(user.getName());
                }
            } else {
                showMessage(getResources().getString(R.string.no_result));
            }
        }
    }

    private class parsePeposTask extends AsyncTask<Void, Void, List> {
        private ResponseListEvent mEvent;
        private WeakReference<MainActivity> activityWeakRef;

        private List<Repos> mRepos;

        private parsePeposTask(MainActivity activity, ResponseListEvent event) {
            mEvent = event;

            activityWeakRef = new WeakReference<>(activity);
        }

        @Override
        protected List doInBackground(Void... params) {
            mRepos = mEvent.getResponseClient();
            if (mRepos != null && !mRepos.isEmpty()) {
                Collections.sort(mRepos, new ReposComparator());
            }

            return mRepos;
        }

        @Override
        protected void onPostExecute(final List items) {
            super.onPostExecute(items);

            MainActivity activity = activityWeakRef.get();
            if (activity != null) {
                mAdapter.addItems((List<Repos>) items);
                mListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } else {
                showMessage(getResources().getString(R.string.no_result));
            }
        }
    }
}
