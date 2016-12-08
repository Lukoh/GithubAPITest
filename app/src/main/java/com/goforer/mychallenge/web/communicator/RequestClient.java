package com.goforer.mychallenge.web.communicator;

import android.content.Context;

import com.goforer.base.model.event.ResponseEvent;
import com.goforer.mychallenge.model.data.Responses;
import com.goforer.mychallenge.utility.ConnectionUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public enum RequestClient {
    INSTANCE;

    private static final long READ_TIME_OUT = 5;
    private static final long WRITE_TIME_OUT = 5;
    private static final long CONNECT_TIME_OUT = 5;

    private RequestMethod mRequestor;

    private static Context mContext;

    private String mRawResponseBody;

    public RequestMethod getRequestMethod(Context context) {
        mContext = context;

        if (mRequestor == null) {
            final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Connection", "keep-alive")
                            .method(original.method(), original.body())
                            .build();

                    Response response = chain.proceed(request);

                    mRawResponseBody = response.body().string();

                    return response.newBuilder()
                            .body(ResponseBody.create(response.body().contentType(),
                                    mRawResponseBody)).build();
                }
            });

            OkHttpClient client = httpClient.build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/users")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mRequestor = retrofit.create(RequestMethod.class);
        }

        return mRequestor;
    }

    public String getBody() {
        return mRawResponseBody;
    }

    /**
     * Communicates responses from Server or offline requests.
     * One and only one method will be invoked in response to a given request.
     */
    static public class RequestCallback implements Callback<Responses> {
        private ResponseEvent mEvent;

        public RequestCallback(ResponseEvent event) {
            mEvent = event;
        }

        @Override
        public void onResponse(Call<Responses> call,
                               retrofit2.Response<Responses> response) {
            if (mEvent != null) {
                mEvent.setResponseClient(response.body());
                mEvent.getResponseClient().setStatus(Responses.SUCCESSFUL);
                mEvent.parseInResponse();

                EventBus.getDefault().post(mEvent);
            }
        }

        @Override
        public void onFailure(Call<Responses> call, Throwable t) {
            boolean isDeviceEnabled = true;

            if (!ConnectionUtils.isNetworkAvailable(mContext)) {
                isDeviceEnabled = false;
            }

            if (mEvent != null) {
                mEvent.setResponseClient(new Responses());
                if (!isDeviceEnabled) {
                    mEvent.getResponseClient().setStatus(Responses.NETWORK_ERROR);
                } else {
                    mEvent.getResponseClient().setStatus(Responses.GENERAL_ERROR);
                }

                EventBus.getDefault().post(mEvent);
            }
        }
    }

    public interface RequestMethod {
        @GET("jakewharton")
        Call<Responses> getUser();

        @GET("/jakewharton/repos")
        Call<Responses> getRepos();
    }
}
