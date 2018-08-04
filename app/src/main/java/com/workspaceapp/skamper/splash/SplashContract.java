package com.workspaceapp.skamper.splash;

import android.content.Context;

import com.workspaceapp.skamper.BasePresenter;
import com.workspaceapp.skamper.BaseView;

public interface SplashContract {

    interface View extends BaseView<Presenter> {
        void openMainActivity();
        void openLoginActivity();
    }

    interface Presenter extends BasePresenter {
        void decideNextActivity(Context context);
    }
}
