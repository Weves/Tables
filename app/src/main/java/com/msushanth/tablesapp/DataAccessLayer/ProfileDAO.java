package com.msushanth.tablesapp.DataAccessLayer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.Interfaces.Profile.ProfileInterface;
import com.msushanth.tablesapp.ViewLayer.Profile.ProfileViewerForm;
import com.msushanth.tablesapp.Objects.User;

/**
 * Created by Sushanth on 11/19/17.
 */

public class ProfileDAO implements ProfileInterface {


    private static User user;

    // set the users profile
    @Override
    public void setProfile(User user) {
        user.setProfileCreated(true);
        user.setIdForFirebase(CurrentUserInfo.fireBaseUser.getUid());
        //user.printUserData();

        // Use the setValue method to store the user in the firebase database.
        // Use child() to store the artist in its unique ID that the constructor created.
        // Use the same ID to update the same user.
        CurrentUserInfo.databaseReference.child(CurrentUserInfo.fireBaseUser.getUid()).setValue(user);
        CurrentUserInfo.setUser();
    }

    // set the new values for the users profile
    @Override
    public void editProfile(User user) {
        CurrentUserInfo.databaseReference.child(user.getIdForFirebase()).setValue(user);
        CurrentUserInfo.setUser();
    }

    // get the current user from the database
    public User getFields() {
        return CurrentUserInfo.getUser();
    }

    // set the fields in the current user class
    public void setFields() {
        CurrentUserInfo.setUser();
    }

    // get a specific users info and set the display elements on the screen with that users info
    public void getUser(final String ID, final ProfileViewerForm viewClass) {


        CurrentUserInfo.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This is the current users profile
                User currentUserProfile = dataSnapshot.child(ID).getValue(User.class);
                viewClass.setDisplayElements(currentUserProfile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

}
