package com.msushanth.tablesapp.DataAccessLayer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Chris Weaver on 12/4/2017.
 */

public class CurrentUserDAO {

    public static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser fireBaseUser = firebaseAuth.getCurrentUser();
    public static String currentUserID = fireBaseUser.getUid();

}
