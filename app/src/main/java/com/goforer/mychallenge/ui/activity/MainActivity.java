package com.goforer.mychallenge.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.goforer.base.model.event.ResponseEvent;
import com.goforer.mychallenge.R;
import com.goforer.mychallenge.model.data.Repos;
import com.goforer.mychallenge.model.data.User;
import com.goforer.mychallenge.model.event.ContentsDataEvent;
import com.goforer.mychallenge.ui.adapter.ReposAdatper;
import com.goforer.mychallenge.utility.ConnectionUtils;
import com.goforer.mychallenge.web.Intermediary;
import com.google.gson.JsonElement;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private ReposAdatper mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);

        if (!ConnectionUtils.isNetworkAvailable(this)) {
            showMessage(getResources().getString(R.string.not_connected_internet));
            return;
        }

        mListView = (ListView) findViewById(R.id.lv_repos);

        mAdapter = new ReposAdatper(this);


        ContentsDataEvent userEvent = new ContentsDataEvent(true, 0);
        Intermediary.INSTANCE.getUser(getApplicationContext(), userEvent);

        ContentsDataEvent reposEvent = new ContentsDataEvent(true, 1);
        Intermediary.INSTANCE.getRepos(getApplicationContext(), reposEvent);
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("")
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(ContentsDataEvent event) {
        if (event.getType() == 0) {
            new parseUserTask(this, event).execute();
        } else {
            new parsePeposTask(this, event).execute();
        }
    }

    private class parseUserTask extends AsyncTask<Void, Void, String> {
        private ResponseEvent mEvent;
        private WeakReference<MainActivity> activityWeakRef;

        private parseUserTask(MainActivity activity, ResponseEvent event) {
            mEvent = event;

            activityWeakRef = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(Void... params) {
            String query;
            if (mEvent.getResponseClient().isSuccessful()) {
                query = mEvent.getResponseClient().getQuery().toString();
            } else {
                showMessage(getResources().getString(R.string.no_result));
            }
            return query;
        }

        @Override
        protected void onPostExecute(String query) {
            super.onPostExecute(query);

            MainActivity activity = activityWeakRef.get();
            if (activity != null) {
                if (mEvent.getResponseClient().isSuccessful()) {
                    JsonElement results = User.gson().fromJson(query, User.class)
                    if (results == null) {
                        showMessage(getResources().getString(R.string.no_result));
                    } else {
                    }

                }
            } else {
                showMessage(getResources().getString(R.string.no_result));
            }
        }
    }

    private class parsePeposTask extends AsyncTask<Void, Void, String> {
        private ResponseEvent mEvent;
        private WeakReference<MainActivity> activityWeakRef;

        private List<Repos> mRepos;

        private parsePeposTask(MainActivity activity, ResponseEvent event) {
            mEvent = event;

            activityWeakRef = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(Void... params) {
            String query;
            if (mEvent.getResponseClient().isSuccessful()) {
                query = mEvent.getResponseClient().getQuery().toString();
            } else {
                JsonElement results = User.gson().fromJson(query, User.class)
            }
            return query;
        }

        @Override
        protected void onPostExecute(String query) {


            super.onPostExecute(query);

            MainActivity activity = activityWeakRef.get();
            if (activity != null) {
                if (mEvent.getResponseClient().isSuccessful()) {
                    JsonElement results = Repos.gson().fromJson(query, Repos.class).getResults();
                    if (results == null) {
                        showMessage(getResources().getString(R.string.no_result));
                    } else {
                        mRepos = mEvent.getResponseClient().getResult();

                        for(int i = 1; i < mRepos.size(); i++) {
                            mAdapter.addItem(mRepos.get(i));
                        }

                        mListView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                showMessage(getResources().getString(R.string.no_result));
            }
        }
    }
}
