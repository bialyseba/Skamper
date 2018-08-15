package com.workspaceapp.skamper.data.network;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AppFirebaseHelper implements FirebaseHelper{

    private FirebaseAuth mAuth;

    @Override
    public FirebaseUser getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    @Override
    public void createUserWithEmailAndPassword(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }
}
