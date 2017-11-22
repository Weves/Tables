package com.msushanth.tablesapp;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Sushanth on 11/22/17.
 */

public class CurrentFirebaseUser {

    private static FirebaseUser user;

    private CurrentFirebaseUser() {}

    public static void setUser(FirebaseUser pUser) {
        user = pUser;
    }

    public static FirebaseUser getUser() {
        return user;
    }
}
