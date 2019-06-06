package com.example.examApp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.examApp.ui.home.HomeActivity;
import com.example.examApp.R;

import javax.inject.Inject;

/**
 * @author Chen.
 * @version 1 at 30/5/2019.
 *Splash activity view
 */
public class SplashActivity extends AppCompatActivity implements SplashMvpView {

    private static final String TAG = "SplashActivity";

    @Inject
    SplashPresenter<SplashActivity> mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTitle(getResources().getString(R.string.app_name));

        mSplashPresenter.onAttach(this);
        mSplashPresenter.openHomeActivity();

    }

    @Override
    public void openHomeActivity() {

        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        mSplashPresenter.onDetach();
        super.onDestroy();
    }
}
