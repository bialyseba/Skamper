package com.workspaceapp.skamper.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.utils.ActivityUtils;

public class SplashActivity extends AppCompatActivity {

    private SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");

        //ca-app-pub-7965324584547465~6878472304
        SplashFragment splashFragment =
                (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder);
        if (splashFragment == null) {
            // Create the fragment
            splashFragment = SplashFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), splashFragment, R.id.fragment_placeholder);
        }

        // Create the presenter
        mSplashPresenter = new SplashPresenter(splashFragment);
    }
}
