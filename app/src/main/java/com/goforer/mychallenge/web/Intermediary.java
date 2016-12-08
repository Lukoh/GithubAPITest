package com.goforer.mychallenge.web;

import android.content.Context;

import com.goforer.base.model.event.ResponseEvent;
import com.goforer.mychallenge.model.data.Responses;
import com.goforer.mychallenge.web.communicator.RequestClient;

import retrofit2.Call;
import retrofit2.Response;

public enum Intermediary {
    INSTANCE;

    public void getUser(Context context, ResponseEvent event)  {

        Call<Responses> call = RequestClient.INSTANCE.getRequestMethod(context)
                .getUser();
        call.enqueue(new RequestClient.RequestCallback(event) {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getRepos(Context context, String place, ResponseEvent event) {
        Call<Responses> call = RequestClient.INSTANCE.getRequestMethod(context)
                .getRepos();
        call.enqueue(new RequestClient.RequestCallback(event) {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}
