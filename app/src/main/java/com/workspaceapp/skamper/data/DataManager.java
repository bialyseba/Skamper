package com.workspaceapp.skamper.data;

import com.google.firebase.auth.FirebaseUser;
import com.workspaceapp.skamper.data.network.FirebaseHelper;
import com.workspaceapp.skamper.data.prefs.PreferencesHelper;

import java.security.NoSuchAlgorithmException;

public interface DataManager extends PreferencesHelper, FirebaseHelper {

    void setUserAsLoggedOut();

    void updateUserInfo(
            String email,
            String password) throws NoSuchAlgorithmException;

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
