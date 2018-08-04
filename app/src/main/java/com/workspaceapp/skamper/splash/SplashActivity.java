package com.workspaceapp.skamper.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.utils.ActivityUtils;

public class SplashActivity extends AppCompatActivity {

    private SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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
