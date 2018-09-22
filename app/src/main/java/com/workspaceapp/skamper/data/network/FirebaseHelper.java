package com.workspaceapp.skamper.data.network;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

public interface FirebaseHelper {

    FirebaseUser getCurrentUser();

    void signInWithEmailAndPassword(String email, String password, OnCompleteListener<AuthResult> listener);

    void createUserWithEmailAndPassword(String email, String password, OnCompleteListener<AuthResult> listener);

    void existsEmailOnDb(String username, ValueEventListener valueEventListener);

    void addUserToDb(String username, String email, String hashedPassword);
}
