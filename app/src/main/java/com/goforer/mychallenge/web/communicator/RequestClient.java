package com.goforer.mychallenge.web.communicator;

import android.content.Context;

import com.goforer.base.model.event.ResponseEvent;
import com.goforer.base.model.event.ResponseReposEvent;
import com.goforer.mychallenge.model.data.Repos;
import com.goforer.mychallenge.model.data.User;
import com.goforer.mychallenge.utility.ConnectionUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
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
import retrofit2.http.Path;

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
                    .baseUrl("https://api.github.com")
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
    static public class RequestUserCallback implements Callback<User> {
        private ResponseEvent mEvent;

        public RequestUserCallback(ResponseEvent event) {
            mEvent = event;
        }

        @Override
        public void onResponse(Call<User> call,
                               retrofit2.Response<User> response) {
            if (mEvent != null) {
                mEvent.setResponseClient(response.body());
                mEvent.parseInResponse();

                EventBus.getDefault().post(mEvent);
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            boolean isDeviceEnabled = true;

            if (!ConnectionUtils.isNetworkAvailable(mContext)) {
                isDeviceEnabled = false;
            }

            if (mEvent != null) {
                mEvent.setResponseClient(new User());
                EventBus.getDefault().post(mEvent);
            }
        }
    }

    /**
     * Communicates responses from Server or offline requests.
     * One and only one method will be invoked in response to a given request.
     */
    static public class RequestReposCallback implements Callback<List<Repos>> {
        private ResponseReposEvent mEvent;

        public RequestReposCallback(ResponseReposEvent event) {
            mEvent = event;
        }

        @Override
        public void onResponse(Call<List<Repos>> call,
                               retrofit2.Response<List<Repos>> response) {
            if (mEvent != null) {
                mEvent.setResponseClient(response.body());
                mEvent.parseInResponse();

                EventBus.getDefault().post(mEvent);
            }
        }

        @Override
        public void onFailure(Call<List<Repos>> call, Throwable t) {
            boolean isDeviceEnabled = true;

            if (!ConnectionUtils.isNetworkAvailable(mContext)) {
                isDeviceEnabled = false;
            }

            if (mEvent != null) {
                EventBus.getDefault().post(mEvent);
            }
        }
    }

    public interface RequestMethod {
        @GET("/users/{name}")
        Call<User> getUser(
                @Path("name") String name
        );

        @GET("/users/{name}/repos")
        Call<List<Repos>> getRepos(
                @Path("name") String name
        );
    }
}
