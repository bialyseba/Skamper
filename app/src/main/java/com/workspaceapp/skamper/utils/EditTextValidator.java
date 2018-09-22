package com.workspaceapp.skamper.utils;

import android.widget.EditText;

import com.workspaceapp.skamper.R;

public class EditTextValidator{
    private EditText editText;

    public EditTextValidator(EditText editText) {
        this.editText = editText;
    }

    public boolean isLengthOk(int limit, int errorMessageId){
        String string = editText.getText().toString();
        if(string.length() > limit){
            return true;
        }else{
            editText.setError(editText.getContext().getString(errorMessageId));
            return false;
        }
    }

    public boolean isEmpty(){
        String string = editText.getText().toString();
        if(string.equals("")){
            editText.setError(editText.getContext().getString(R.string.empty_field));
            return true;
        }else{
            return false;
        }
    }

    public boolean isEmailAdressValid(){
        String email = editText.getText().toString();
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else{
            editText.setError(editText.getContext().getString(R.string.wrong_email));
            return false;
        }
    }

    public boolean arePasswordsSame(EditText passwordEditText){
        String rePassword = passwordEditText.getText().toString();
        String password = editText.getText().toString();
        if(rePassword.equals(password)){
            return true;
        }else{
            editText.setError(editText.getContext().getString(R.string.password_not_same));
            return false;
        }
    }
}
