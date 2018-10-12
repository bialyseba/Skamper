package com.workspaceapp.skamper;

import android.app.Application;

import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;

public class SkamperApplication extends Application {
    public static Call call = null;
    public static SinchClient sinchClient;
    public static String videoRecipientId = null;
    public static boolean videoInitiator = false;

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
