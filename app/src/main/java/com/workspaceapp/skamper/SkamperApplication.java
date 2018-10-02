package com.workspaceapp.skamper;

import android.app.Application;

import com.sinch.android.rtc.calling.Call;

public class SkamperApplication extends Application {
    public static Call call = null;


    @Override
    public void onCreate() {
        super.onCreate();
    }
}
