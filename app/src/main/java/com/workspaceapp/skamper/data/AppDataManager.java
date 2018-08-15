package com.workspaceapp.skamper.data;

import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.workspaceapp.skamper.data.network.AppFirebaseHelper;
import com.workspaceapp.skamper.data.network.FirebaseHelper;
import com.workspaceapp.skamper.data.prefs.AppPreferencesHelper;
import com.workspaceapp.skamper.data.prefs.PreferencesHelper;
import com.workspaceapp.skamper.utils.HashHelper;

import java.security.NoSuchAlgorithmException;


public class AppDataManager implements DataManager {
    private PreferencesHelper mPreferencesHelper = null;
    private FirebaseHelper mFirebaseHelper = null;

    private static AppDataManager INSTANCE = null;

    public static AppDataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppDataManager();
        }
        return INSTANCE;
    }

    @Override
    public void setUserAsLoggedOut() {
        //TODO
    }

    @Override
    public void updateUserInfo(String email, String password) throws NoSuchAlgorithmException {
        setEmail(email);
        setHashedPassword(HashHelper.hashString(password));
    }

    @Override
    public int getCurrentUserLoggedInMode(Context context) {
        if(mPreferencesHelper == null){
            mPreferencesHelper = new AppPreferencesHelper();
        }
        return mPreferencesHelper.getCurrentUserLoggedInMode(context);
    }

    @Override
    public void setCurrentUserLoggedInMode(Context context, LoggedInMode mode) {
        if(mPreferencesHelper == null){
            mPreferencesHelper = new AppPreferencesHelper();
        }
        mPreferencesHelper.setCurrentUserLoggedInMode(context,mode);
    }

    @Override
    public void setEmail(String email) {
        if(mPreferencesHelper == null){
            mPreferencesHelper = new AppPreferencesHelper();
        }
        mPreferencesHelper.setEmail(email);
    }

    @Override
    public void setHashedPassword(String hashedPassword) {
        if(mPreferencesHelper == null){
            mPreferencesHelper = new AppPreferencesHelper();
        }
        mPreferencesHelper.setHashedPassword(hashedPassword);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        if(mFirebaseHelper == null){
            mFirebaseHelper = new AppFirebaseHelper();
        }
        return mFirebaseHelper.getCurrentUser();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password, OnCompleteListener<AuthResult> listener) {
        if(mFirebaseHelper == null){
            mFirebaseHelper = new AppFirebaseHelper();
        }
        mFirebaseHelper.signInWithEmailAndPassword(email, password, listener);
    }

    @Override
    public void createUserWithEmailAndPassword(String email, String password, OnCompleteListener<AuthResult> listener) {
        if(mFirebaseHelper == null){
            mFirebaseHelper = new AppFirebaseHelper();
        }
        mFirebaseHelper.createUserWithEmailAndPassword(email, password, listener);
    }
}
