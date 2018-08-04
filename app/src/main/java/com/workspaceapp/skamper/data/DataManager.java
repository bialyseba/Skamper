package com.workspaceapp.skamper.data;

import com.workspaceapp.skamper.data.prefs.PreferencesHelper;

public interface DataManager extends PreferencesHelper {

    void setUserAsLoggedOut();

    void updateUserInfo(
            String email,
            String password);

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
