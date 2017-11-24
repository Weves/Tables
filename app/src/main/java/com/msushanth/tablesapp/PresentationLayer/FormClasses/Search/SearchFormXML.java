package com.msushanth.tablesapp.PresentationLayer.FormClasses.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.User;

import java.util.ArrayList;

/**
 * Created by Sushanth on 10/26/17.
 */

public class SearchFormXML extends android.support.v4.app.Fragment {

    FirebaseAuth firebaseAuth;
    DatabaseReference dbReference;
    FirebaseUser fireBaseUser;

    User currentUserProfile;
    ArrayList<User> allUsers;

    TextView label;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        label = (TextView) rootView.findViewById(R.id.search_label);


        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        fireBaseUser = firebaseAuth.getCurrentUser();

        allUsers = new ArrayList<User>();

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // This is the current users profile
                currentUserProfile = dataSnapshot.child(fireBaseUser.getUid()).getValue(User.class);


                String textToDisplay = "";
                textToDisplay += "***This is the current user *** \n";
                textToDisplay += currentUserProfile.userDataToPrint();


                // This will print out the profile of everyone in the database
                // TODO: store these users in an arraylist to use for the algorithm
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    textToDisplay += "\n\n\n****************\n*** ANOTHER USER ***\n";
                    textToDisplay += user.userDataToPrint();
                }


                label.setText(textToDisplay);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        return rootView;
    }

}


// The for loop gets all the users in the database. Create an arraylist (or other data structure) to store
// all these users. Then you can do your algorithm.
// I DID NOT USE LAYERS. I DID EVERYTHING IN THIS FILE.
// Please implement this file using layers.

// *** IMPORTANT: make a check to only add users to the arraylist when the "accountCreated" value of the User
// is set to true. Otherwise, the app will crash.