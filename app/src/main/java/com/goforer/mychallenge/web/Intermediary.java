package com.goforer.mychallenge.web;

import android.content.Context;

import com.goforer.base.model.event.ResponseEvent;
import com.goforer.base.model.event.ResponseReposEvent;
import com.goforer.mychallenge.model.data.Repos;
import com.goforer.mychallenge.model.data.User;
import com.goforer.mychallenge.web.communicator.RequestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public enum Intermediary {
    INSTANCE;

    public void getUser(Context context, String userName, ResponseEvent event)  {

        Call<User> call = RequestClient.INSTANCE.getRequestMethod(context)
                .getUser(userName);
        call.enqueue(new RequestClient.RequestUserCallback(event) {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getRepos(Context context, String userName, ResponseReposEvent event) {
        Call<List<Repos>> call = RequestClient.INSTANCE.getRequestMethod(context)
                .getRepos(userName);
        call.enqueue(new RequestClient.RequestReposCallback(event) {
            @Override
            public void onResponse(Call<List<Repos>> call, Response<List<Repos>> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<Repos>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}
