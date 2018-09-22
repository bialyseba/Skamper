package com.workspaceapp.skamper.login;

import android.widget.EditText;

import com.google.firebase.database.ValueEventListener;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.utils.EditTextValidator;

public class RegisterFormValidator {

    private EditText userNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText rePasswordEditText;

    public RegisterFormValidator(EditText userNameEditText, EditText emailEditText, EditText passwordEditText, EditText rePasswordEditText){
        this.userNameEditText = userNameEditText;
        this.emailEditText = emailEditText;
        this.passwordEditText = passwordEditText;
        this.rePasswordEditText = rePasswordEditText;
    }

    public boolean isValid(){
        boolean isUsernameValid = isUsernameValid();
        boolean isEmailValid = isEmailValid();
        boolean isPasswordValid = isPasswordValid();
        boolean isRePasswordValid = isRePasswordValid();
        return isUsernameValid && isEmailValid && isPasswordValid && isRePasswordValid;
    }

    private boolean isUsernameValid(){
        EditTextValidator usernameValidator = new EditTextValidator(userNameEditText);
        return usernameValidator.isLengthOk(5, R.string.username_to_short) && !usernameValidator.isEmpty();
    }

    public void existsEmail(ValueEventListener valueEventListener){
        String email = emailEditText.getText().toString();
        AppDataManager.getInstance().existsEmailOnDb(email, valueEventListener);
    }

    private boolean isEmailValid(){
        EditTextValidator emailValidator = new EditTextValidator(emailEditText);
        return !emailValidator.isEmpty() && emailValidator.isEmailAdressValid();
    }

    private boolean isPasswordValid(){
        EditTextValidator passwordValidator = new EditTextValidator(passwordEditText);
        return !passwordValidator.isEmpty() && passwordValidator.isLengthOk(7, R.string.password_too_short);
    }

    private boolean isRePasswordValid() {
        EditTextValidator rePasswordValidator = new EditTextValidator(rePasswordEditText);
        return !rePasswordValidator.isEmpty() && rePasswordValidator.arePasswordsSame(passwordEditText);
    }


}
