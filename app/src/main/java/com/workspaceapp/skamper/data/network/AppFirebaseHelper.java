package com.workspaceapp.skamper.data.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppFirebaseHelper implements FirebaseHelper{

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    public FirebaseUser getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String hashedPassword, OnCompleteListener<AuthResult> listener) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, hashedPassword).addOnCompleteListener(listener);
    }

    @Override
    public void createUserWithEmailAndPassword(String email, String hashedPassword, OnCompleteListener<AuthResult> listener) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, hashedPassword).addOnCompleteListener(listener);
    }

    @Override
    public void existsEmailOnDb(String email, ValueEventListener valueEventListener) {
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference("Users");
        Query query = myRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void addUserToDb(String username, String email, String provider) {
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference();
        String key = myRef.child("Users").push().getKey();
        User user = new User(username,email, provider, false, new ArrayList<>(), "");
        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Users/" + key, userValues);
        myRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("FirebaseHelper", "User added");
                }else{
                    Log.d("FirebaseHelper", task.getException().toString());
                }
            }
        });
    }

    @Override
    public void addContact(String username, String email, ValueEventListener valueEventListener) {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.getReference().child("Users").orderByChild("email").equalTo(getCurrentUser().getEmail()).addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void getPhotoUriOfSpecifiedUser(String email, ValueEventListener valueEventListener) {
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.getReference().child("Users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(valueEventListener);
    }
}
