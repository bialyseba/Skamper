package com.workspaceapp.skamper.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.utils.ActivityUtils;

public class StartActivity extends AppCompatActivity {

    private StartPresenter mStartPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        StartFragment startFragment =
                (StartFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder);
        if (startFragment == null) {
            // Create the fragment
            startFragment = StartFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), startFragment, R.id.fragment_placeholder);
        }


        // Create the presenter
        mStartPresenter = new StartPresenter(startFragment);

    }
}
