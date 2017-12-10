package com.msushanth.tablesapp.DataAccessLayer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.User;

/**
 * Created by Chris Weaver on 12/4/2017.
 */

public class CurrentUserInfo {

    public static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser fireBaseUser;
    public static String currentUserID;
    private static User user;

    // store status of current user profile
    public static void setUser() {

        fireBaseUser = firebaseAuth.getCurrentUser();
        currentUserID = fireBaseUser.getUid();

        CurrentUserInfo.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.child(CurrentUserInfo.currentUserID).getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    // get status of current user profile
    public static User getUser() {
        return user;
    }

}
