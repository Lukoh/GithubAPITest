package com.goforer.mychallenge.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.goforer.base.ui.activity.BaseActivity;
import com.goforer.mychallenge.R;
import com.goforer.mychallenge.utility.ActivityCaller;

public class SplashActivity extends BaseActivity {
    private static final int MIN_SPLASH_TIME = 2000;

    private long mSplashStart;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_splash);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashStart = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();

        onWait();
    }

    private void onWait() {
        long elapsed = System.currentTimeMillis() - mSplashStart;

        long more_splash = MIN_SPLASH_TIME <= elapsed ? 0 : MIN_SPLASH_TIME - elapsed;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToMain();
            }
        }, more_splash);
    }

    private void moveToMain() {
        ActivityCaller.INSTANCE.callPhotosList(this);
        finish();
    }
}
