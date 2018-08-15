package com.workspaceapp.skamper.data.network;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public interface FirebaseHelper {

    FirebaseUser getCurrentUser();

    void signInWithEmailAndPassword(String email, String password, OnCompleteListener<AuthResult> listener);

    void createUserWithEmailAndPassword(String email, String password, OnCompleteListener<AuthResult> listener);
}
