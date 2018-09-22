package com.workspaceapp.skamper.login;

import android.widget.EditText;

import com.workspaceapp.skamper.utils.EditTextValidator;

public class LoginFormValidator {

    private EditText emailEditText;
    private EditText passwordEditText;

    public LoginFormValidator(EditText emailEditText, EditText passwordEditText){
        this.emailEditText = emailEditText;
        this.passwordEditText = passwordEditText;
    }

    public boolean isValid(){
        boolean isEmailValid = isEmailValid();
        boolean isPasswordValid = isPasswordValid();
        return isEmailValid && isPasswordValid;
    }

    private boolean isEmailValid(){
        EditTextValidator emailValidator = new EditTextValidator(emailEditText);
        return !emailValidator.isEmpty() && emailValidator.isEmailAdressValid();
    }

    private boolean isPasswordValid(){
        EditTextValidator passwordValidator = new EditTextValidator(passwordEditText);
        return !passwordValidator.isEmpty();
    }
}
