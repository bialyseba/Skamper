package com.workspaceapp.skamper.login;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.workspaceapp.skamper.BasePresenter;
import com.workspaceapp.skamper.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void submitLoginForm();
        void submitRegisterForm();
        void startMainActivity();
        void showDialogBox(String content);
        void submitGoogleLogin(Activity activity);
    }

    interface Presenter extends BasePresenter {
        void registerUser(String username, String email, String password);
        void loginUser(String email, String password, Context context);
        void signInGoogle(Activity activity);
        void firebaseAuthWithGoogle(GoogleSignInAccount acct, Context context);
    }
}
