package com.workspaceapp.skamper.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.DataManager;
import com.workspaceapp.skamper.utils.ActivityUtils;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private Button loginFormButton;
    private Button registerFormButton;
    private WhichForm currentForm;
    private LoginPresenter mLoginPresenter;

    private LinearLayout signUpRedirectLayout;
    private TextView signUpRedirectTextView;

    private CallbackManager mCallbackManager;

    public static LoginButton loginButton;
    LoginFragment loginFragment;

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

        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("LoginActivity", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("LoginActivity", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("LoginActivity", "facebook:onError", error);
                // ...
            }
        });
    }

    public void launchLoginForm(){
        setButtonActive(loginFormButton);
        setButtonInactive(registerFormButton);
        setSignUpRedirectLayoutVisible(true);

        loginFragment =
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

        loginFragment =
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
        }else{
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("LoginActivity", "handleFacebookAccessToken:" + token);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginActivity", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email = user.getEmail();
                            String username = mAuth.getCurrentUser().getDisplayName();
                            AppDataManager.getInstance().existsEmailOnDb(email, new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        Log.d("LoginPresenter", email + " exists");
                                    }else{
                                        Log.d("LoginPresenter", email + " not exists");
                                        AppDataManager.getInstance().addUserToDb(username, email.toLowerCase(), "FACEBOOK");
                                    }
                                    AppDataManager.getInstance().setCurrentUserLoggedInMode(LoginActivity.this, DataManager.LoggedInMode.LOGGED_IN_MODE_FB);
                                    loginFragment.startMainActivity();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginActivity", "signInWithCredential:failure", task.getException());
                            loginFragment.showDialogBox(Objects.requireNonNull(task.getException()).getMessage());
                            //Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                              //      Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
