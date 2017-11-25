package com.msushanth.tablesapp.DataAccessLayer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.msushanth.tablesapp.Interfaces.Profile.ProfileInterface;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/19/17.
 */

public class ProfileDAO implements ProfileInterface {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private User user;

    public ProfileDAO(User user) {
        this.user = user;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Create unique ID for our artists. Use push method of firebase. This will create a unique key.
        // Use the get method to get the unique ID and assign it to the user.
        //user.setIdForFirebase(databaseReference.push().getKey());
    }


    @Override
    public void setProfile(User user) {
        this.user.setProfileCreated(true);
        this.user.setIdForFirebase(firebaseUser.getUid());
        this.user.printUserData();

        // Use the setValue method to store the user in the firebase database.
        // Use child() to store the artist in its unique ID that the constructor created.
        // Use the same ID to update the same user.
        // Use the user we updated in the constructor by using this.user in place of user
        // (I'm pretty sure user and this.user are the same tho... but just to be safe)
        databaseReference.child(firebaseUser.getUid()).setValue(this.user);
    }


    @Override
    public void editProfile(User user) {}
}
