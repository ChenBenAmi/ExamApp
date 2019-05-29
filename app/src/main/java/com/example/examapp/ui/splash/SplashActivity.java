package com.example.examapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.examapp.ui.home.HomeActivity;
import com.example.examapp.R;


public class SplashActivity extends AppCompatActivity implements SplashMvpView {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTitle(getResources().getString(R.string.app_name));
        SplashPresenter<SplashActivity> mSplashPresenter = new SplashPresenter<>(this);
        mSplashPresenter.onAttach(this);
        mSplashPresenter.openHomeActivity();

    }

    @Override
    public void openHomeActivity() {

        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
