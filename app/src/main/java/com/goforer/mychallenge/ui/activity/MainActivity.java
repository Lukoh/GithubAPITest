package com.goforer.mychallenge.ui.activity;

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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private ReposAdatper mAdapter;

    private ThumbnailImageView mImageView;
    private TextView mNameView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);

        if (!ConnectionUtils.isNetworkAvailable(this)) {
            showMessage(getResources().getString(R.string.not_connected_internet));
            return;
        }

        mImageView = (ThumbnailImageView) findViewById(R.id.iv_image);
        mNameView = (TextView) findViewById(R.id.tv_name);
        mListView = (ListView) findViewById(R.id.lv_repos);

        mAdapter = new ReposAdatper(this);

        ContentsDataEvent reposEvent = new ContentsDataEvent(true, 1);
        Intermediary.INSTANCE.getRepos(getApplicationContext(), reposEvent);

        ContentsUserDataEvent userEvent = new ContentsUserDataEvent(true, 0);
        Intermediary.INSTANCE.getUser(getApplicationContext(), userEvent);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("")
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(ContentsUserDataEvent event) {
        new parseUserTask(this, event).execute();
    }

    @SuppressWarnings("")
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(ContentsDataEvent event) {
        new parsePeposTask(this, event).execute();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
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
            Collections.sort(mRepos, new ReposComparator());
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
