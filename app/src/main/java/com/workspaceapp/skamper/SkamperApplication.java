package com.workspaceapp.skamper;

import android.app.Application;

import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.workspaceapp.skamper.data.model.Contact;

import java.util.ArrayList;

public class SkamperApplication extends Application {
    public static Call call = null;
    public static SinchClient sinchClient;
    public static ArrayList<Contact> contacts;
    public static String contactsFor;

    @Override
    public void onCreate() {
        super.onCreate();
        contactsFor = "";
        contacts = new ArrayList<>();
    }
}
