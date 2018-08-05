package com.workspaceapp.skamper.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.utils.ActivityUtils;

public class LoginActivity extends AppCompatActivity {

    private Button loginFormButton;
    private Button registerFormButton;
    private WhichForm currentForm;

    private enum WhichForm{
        LOGIN_FORM, REGISTER_FORM
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginFormButton = findViewById(R.id.loginFormButton);
        registerFormButton = findViewById(R.id.registerFormButton);

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
    }

    private void launchLoginForm(){
        setButtonActive(loginFormButton);
        setButtonInactive(registerFormButton);

        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder);
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance(R.layout.fragment_login_form);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), loginFragment, R.id.fragment_placeholder);
        }else{
            loginFragment = LoginFragment.newInstance(R.layout.fragment_login_form);
            ActivityUtils.changeFragment(getSupportFragmentManager(),loginFragment, R.id.fragment_placeholder);
        }

        currentForm = WhichForm.LOGIN_FORM;
    }

    private void launchRegisterForm(){
        setButtonActive(registerFormButton);
        setButtonInactive(loginFormButton);

        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder);
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance(R.layout.fragment_register_form);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), loginFragment, R.id.fragment_placeholder);
        }else{
            loginFragment = LoginFragment.newInstance(R.layout.fragment_register_form);
            ActivityUtils.changeFragment(getSupportFragmentManager(),loginFragment, R.id.fragment_placeholder);
        }

        currentForm = WhichForm.REGISTER_FORM;
    }

    private void setButtonActive(Button button){
        button.setBackgroundResource(R.drawable.button_round_checked);
        button.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    private void setButtonInactive(Button button){
        button.setBackgroundResource(R.drawable.button_round_unchecked);
        button.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}
