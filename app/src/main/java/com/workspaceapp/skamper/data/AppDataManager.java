package com.workspaceapp.skamper.data;

import android.content.Context;

import com.workspaceapp.skamper.data.prefs.PreferencesHelper;


public class AppDataManager implements DataManager {
    private final PreferencesHelper mPreferencesHelper;

    private static AppDataManager INSTANCE = null;

    public static AppDataManager getInstance(PreferencesHelper preferencesHelper) {
        if (INSTANCE == null) {
            INSTANCE = new AppDataManager(preferencesHelper);
        }
        return INSTANCE;
    }

    public AppDataManager(PreferencesHelper preferencesHelper) {
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public void setUserAsLoggedOut() {

    }

    @Override
    public void updateUserInfo(String email, String password) {

    }

    @Override
    public int getCurrentUserLoggedInMode(Context context) {
        return mPreferencesHelper.getCurrentUserLoggedInMode(context);
    }

    @Override
    public void setCurrentUserLoggedInMode(Context context, LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(context,mode);
    }
}
