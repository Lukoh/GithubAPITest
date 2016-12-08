package com.goforer.mychallenge;

import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

public class MyChallenge extends MultiDexApplication {
    private static final String TAG = "MyChallenge";

    public static Context mContext;
    public static Resources mResources;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mResources = getResources();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.UncaughtExceptionHandler exceptionHandler =
                        Thread.getDefaultUncaughtExceptionHandler();
                try {
                    exceptionHandler = new com.goforer.mychallenge.utility.ExceptionHandler(MyChallenge.this, exceptionHandler,
                            new com.goforer.mychallenge.utility.ExceptionHandler.OnFindCrashLogListener() {

                                @Override
                                public void onFindCrashLog(String log) {
                                    Log.e("onFindCrashLog", log);
                                }

                                @Override
                                public void onCaughtCrash(Throwable throwable) {
                                    Log.e(TAG, "Ooooooops Crashed!!");
                                }
                            });

                    Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
                } catch( NullPointerException e ) {
                    e.printStackTrace();
                }
            }
        }).start();

        //initialize and create the image loader logic
    }

    public static void closeApplication() {
        System.exit(0);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(MyChallenge.this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}

