package com.workspaceapp.skamper.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {
    private String username;
    private String email;
    private String provider;
    private boolean isOnline;
    private ArrayList<Contact> contacts;
    private String photoUri;

    public User(){

    }

    public User(String username, String email, String provider, boolean isOnline, ArrayList<Contact> contacts, String photoUri){
        this.username = username;
        this.email = email;
        this.provider = provider;
        this.isOnline = isOnline;
        this.contacts = contacts;
        this.photoUri = photoUri;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("provider", provider);
        result.put("email", email);
        result.put("isOnline", isOnline);
        result.put("contacts", contacts);
        result.put("photoUri", photoUri);
        return result;
    }
}
