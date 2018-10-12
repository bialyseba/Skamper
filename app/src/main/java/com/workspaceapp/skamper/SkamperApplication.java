package com.workspaceapp.skamper;

import android.app.Application;

import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.messaging.MessageClient;
import com.workspaceapp.skamper.data.db.MessagesDatabaseHelper;
import com.workspaceapp.skamper.data.model.Contact;

import java.util.ArrayList;

public class SkamperApplication extends Application {
    public static Call call = null;
    public static SinchClient sinchClient;
    public static ArrayList<Contact> contacts;
    public static String contactsFor;
    public static MessageClient messageClient;
    public static MessagesDatabaseHelper messagesDatabaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        contactsFor = "";
        contacts = new ArrayList<>();
        messagesDatabaseHelper = new MessagesDatabaseHelper(getApplicationContext(), "skamper.db");
    }
}
