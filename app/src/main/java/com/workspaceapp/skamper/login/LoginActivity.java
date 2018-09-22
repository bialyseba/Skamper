package com.workspaceapp.skamper.login;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.utils.ActivityUtils;

public class LoginActivity extends AppCompatActivity {

    private Button loginFormButton;
    private Button registerFormButton;
    private WhichForm currentForm;
    private LoginPresenter mLoginPresenter;

    private LinearLayout signUpRedirectLayout;
    private TextView signUpRedirectTextView;

    private enum WhichForm{
        LOGIN_FORM, REGISTER_FORM
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginFormButton = findViewById(R.id.loginFormButton);
        registerFormButton = findViewById(R.id.registerFormButton);
        signUpRedirectLayout = findViewById(R.id.signUpRedirectLayout);
        signUpRedirectTextView = findViewById(R.id.signUpTextView);

        launchLoginForm();

        loginFormButton.setOnClickListener(v -> {
            if(currentForm == WhichForm.REGISTER_FORM){
                launchLoginForm();
            }
        });
        registerFormButton.setOnClickListener(v -> {
            if(currentForm == WhichForm.LOGIN_FORM){
                launchRegisterForm();
            }
        });

        signUpRedirectTextView.setOnClickListener(view -> {
            launchRegisterForm();
        });
    }

    public void launchLoginForm(){
        setButtonActive(loginFormButton);
        setButtonInactive(registerFormButton);
        setSignUpRedirectLayoutVisible(true);

        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder);
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance(R.layout.fragment_login_form, FormType.LOGIN_FORM);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), loginFragment, R.id.fragment_placeholder);
        }else{
            loginFragment = LoginFragment.newInstance(R.layout.fragment_login_form, FormType.LOGIN_FORM);
            ActivityUtils.changeFragment(getSupportFragmentManager(),loginFragment, R.id.fragment_placeholder);
        }

        currentForm = WhichForm.LOGIN_FORM;
        mLoginPresenter = new LoginPresenter(loginFragment);
    }

    private void launchRegisterForm(){
        setButtonActive(registerFormButton);
        setButtonInactive(loginFormButton);
        setSignUpRedirectLayoutVisible(false);

        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder);
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance(R.layout.fragment_register_form, FormType.REGISTER_FORM);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), loginFragment, R.id.fragment_placeholder);
        }else{
            loginFragment = LoginFragment.newInstance(R.layout.fragment_register_form, FormType.REGISTER_FORM);
            ActivityUtils.changeFragment(getSupportFragmentManager(),loginFragment, R.id.fragment_placeholder);
        }

        currentForm = WhichForm.REGISTER_FORM;
        mLoginPresenter = new LoginPresenter(loginFragment);
    }

    private void setButtonActive(Button button){
        button.setBackgroundResource(R.drawable.button_circle_checked);
        button.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    private void setButtonInactive(Button button){
        button.setBackgroundResource(R.drawable.button_circle_unchecked);
        button.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    private void setSignUpRedirectLayoutVisible(boolean visible){
        if(visible){
            signUpRedirectLayout.setVisibility(View.VISIBLE);
        }else{
            signUpRedirectLayout.setVisibility(View.GONE);
        }
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginPresenter.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                mLoginPresenter.firebaseAuthWithGoogle(account, this);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Login Fragment", "Google sign in failed", e);
                // ...
            }
        }
    }
}
