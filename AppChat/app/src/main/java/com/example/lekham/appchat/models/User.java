package com.example.lekham.appchat.models;

/**
 * Created by Le Kham on 5/20/2017.
 */

public class User {
    private String uid;
    private String email;
    private String firebaseToken;

    public User()
    {

    }

    public User(String uid,String email,String firebaseToken)
    {
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
