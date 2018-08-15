package com.workspaceapp.skamper.data.prefs;

import android.content.Context;

import com.workspaceapp.skamper.data.DataManager;

public interface PreferencesHelper {

    int getCurrentUserLoggedInMode(Context context);

    void setCurrentUserLoggedInMode(Context context, DataManager.LoggedInMode mode);

    void setEmail(String email);

    void setHashedPassword(String hashedPassword);
}
