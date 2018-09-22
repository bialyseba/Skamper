package com.workspaceapp.skamper.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {
    private String username;
    private String email;
    private String provider;

    public User(){

    }

    public User(String username, String email, String hashedPassword){

        this.username = username;
        this.email = email;
        this.provider = hashedPassword;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("password", provider);
        result.put("email", email);
        return result;
    }
}
