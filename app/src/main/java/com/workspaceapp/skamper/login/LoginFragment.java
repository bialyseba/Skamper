package com.workspaceapp.skamper.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.main.MainActivity;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.google.common.base.Preconditions.checkNotNull;

public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;
    private static int mResource;
    private static FormType mFormType;

    private Button registerButton;
    private Button loginButton;
    private ImageButton googleButton;
    private ImageButton fbButton;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText rePasswordEditText;


    public LoginFragment() {
        // Requires empty public constructor
    }

    public static LoginFragment newInstance(@LayoutRes int resource, FormType formType) {
        mResource = resource;
        mFormType = formType;
        return new LoginFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start(getContext());
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(mResource, container, false);

        if(mFormType == FormType.LOGIN_FORM){
            emailEditText = root.findViewById(R.id.emailEditText);
            passwordEditText = root.findViewById(R.id.passwordEditText);
            loginButton = root.findViewById(R.id.submitLoginButton);
            loginButton.setOnClickListener(view -> {
              submitLoginForm();
              Log.d("LoginFragment", "username: " + emailEditText.getText().toString() + "password: " + passwordEditText.toString());
            });
            googleButton = root.findViewById(R.id.googleButton);
            googleButton.setOnClickListener(view -> {
                submitGoogleLogin(getActivity());
            });
            fbButton = root.findViewById(R.id.facebookButton);
            fbButton.setOnClickListener(view -> {
                LoginActivity.loginButton.performClick();
            });
        }else{
            usernameEditText = root.findViewById(R.id.usernameEditText);
            emailEditText = root.findViewById(R.id.emailEditText);
            passwordEditText = root.findViewById(R.id.passwordEditText);
            rePasswordEditText = root.findViewById(R.id.rePasswordEditText);
            registerButton = root.findViewById(R.id.submitRegisterButton);
            registerButton.setOnClickListener(v -> {
                submitRegisterForm();
            });
        }

        return root;
    }

    @Override
    public void submitLoginForm() {
        LoginFormValidator validator = new LoginFormValidator(emailEditText, passwordEditText);
        if(validator.isValid()){
            mPresenter.loginUser(emailEditText.getText().toString(), passwordEditText.getText().toString(), getContext());
        }
    }

    @Override
    public void submitRegisterForm() {
        RegisterFormValidator validator = new RegisterFormValidator(usernameEditText, emailEditText, passwordEditText, rePasswordEditText);
        if(validator.isValid()){
            mPresenter.registerUser(getContext(), usernameEditText.getText().toString(), emailEditText.getText().toString(), passwordEditText.getText().toString());
        }
    }

    @Override
    public void startMainActivity(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        getActivity().finish();
        startActivity(intent);


    }

    @Override
    public void showDialogBox(String content) {
        new AlertDialog.Builder(this.getContext())
                .setMessage(content)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                    }
                })
                .show();

    }

    @Override
    public void submitGoogleLogin(Activity activity) {
        mPresenter.signInGoogle(activity);
    }


}
