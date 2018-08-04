package com.workspaceapp.skamper.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.DataManager;


public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";

    private static AppPreferencesHelper INSTANCE = null;

    public static AppPreferencesHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (AppPreferencesHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppPreferencesHelper();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public int getCurrentUserLoggedInMode(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_KEY_USER_LOGGED_IN_MODE, DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(Context context, DataManager.LoggedInMode mode) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE,mode.getType()).apply();
    }
}
