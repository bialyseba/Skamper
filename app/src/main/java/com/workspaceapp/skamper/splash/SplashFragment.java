package com.workspaceapp.skamper.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.login.LoginActivity;
import com.workspaceapp.skamper.main.MainActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class SplashFragment extends Fragment implements SplashContract.View {

    private SplashContract.Presenter mPresenter;
    private Activity activity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
    public SplashFragment() {
        // Requires empty public constructor
    }

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_splash, container, false);

        return root;
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void openMainActivity() {
        Intent intent = new Intent(activity,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(getContext());
        startActivity(intent);
    }
}
