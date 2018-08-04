package com.workspaceapp.skamper.splash;

import android.content.Context;
import android.support.annotation.NonNull;

import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.DataManager;
import com.workspaceapp.skamper.data.prefs.AppPreferencesHelper;

import static com.google.common.base.Preconditions.checkNotNull;

public class SplashPresenter implements SplashContract.Presenter{

    private final SplashContract.View mStartView;

    public SplashPresenter(@NonNull SplashContract.View startView) {

        mStartView = checkNotNull(startView, "tasksView cannot be null!");

        mStartView.setPresenter(this);
    }
    @Override
    public void start(Context context) {
        decideNextActivity(context);
    }

    @Override
    public void decideNextActivity(Context context) {
        if (AppDataManager.getInstance(AppPreferencesHelper.getInstance()).getCurrentUserLoggedInMode(context)
                == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()) {
            mStartView.openLoginActivity();
        } else {
            mStartView.openMainActivity();
        }
    }
}