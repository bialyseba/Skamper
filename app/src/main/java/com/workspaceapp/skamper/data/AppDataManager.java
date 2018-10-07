package com.workspaceapp.skamper.data;

import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
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
    public void clearEmail() {
        if(mPreferencesHelper == null){
            mPreferencesHelper = new AppPreferencesHelper();
        }
        mPreferencesHelper.clearEmail();
    }

    @Override
    public void clearHashedPassword() {
        if(mPreferencesHelper == null){
            mPreferencesHelper = new AppPreferencesHelper();
        }
        mPreferencesHelper.clearHashedPassword();
    }

    @Override
    public FirebaseUser getCurrentUser() {
        if(mFirebaseHelper == null){
            mFirebaseHelper = new AppFirebaseHelper();
        }
        return mFirebaseHelper.getCurrentUser();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String hashedPassword, OnCompleteListener<AuthResult> listener) {
        if(mFirebaseHelper == null){
            mFirebaseHelper = new AppFirebaseHelper();
        }
        mFirebaseHelper.signInWithEmailAndPassword(email, hashedPassword, listener);
    }

    @Override
    public void createUserWithEmailAndPassword(String email, String hashedPassword, OnCompleteListener<AuthResult> listener) {
        if(mFirebaseHelper == null){
            mFirebaseHelper = new AppFirebaseHelper();
        }
        mFirebaseHelper.createUserWithEmailAndPassword(email, hashedPassword, listener);
    }

    @Override
    public void existsEmailOnDb(String email, ValueEventListener valueEventListener) {
        if(mFirebaseHelper == null){
            mFirebaseHelper = new AppFirebaseHelper();
        }
        mFirebaseHelper.existsEmailOnDb(email, valueEventListener);
    }

    @Override
    public void addUserToDb(String username, String email, String provider) {
        if(mFirebaseHelper == null){
            mFirebaseHelper = new AppFirebaseHelper();
        }
        mFirebaseHelper.addUserToDb(username, email, provider);
    }

    @Override
    public void addContact(String username, String email, ValueEventListener valueEventListener) {
        if(mFirebaseHelper == null){
            mFirebaseHelper = new AppFirebaseHelper();
        }
        mFirebaseHelper.addContact(username, email, valueEventListener);
    }

    @Override
    public void getPhotoUriOfSpecifiedUser(String email, ValueEventListener valueEventListener) {
        if(mFirebaseHelper == null){
            mFirebaseHelper = new AppFirebaseHelper();
        }
        mFirebaseHelper.getPhotoUriOfSpecifiedUser(email, valueEventListener);
    }
}
