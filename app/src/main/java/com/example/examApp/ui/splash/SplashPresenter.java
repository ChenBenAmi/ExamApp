package com.example.examApp.ui.splash;

import android.content.Context;
import android.os.Handler;
import com.example.examApp.ui.base.BasePresenter;


public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    private static final long TIME = 2000;
    SplashPresenter(Context context) {
        super(context);
    }


    @Override
    public void openHomeActivity() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMvpView().openHomeActivity();
            }
        }, TIME);
    }
}
