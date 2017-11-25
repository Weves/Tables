package com.msushanth.tablesapp.PresentationLayer.FormClasses.Search;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation.SelectMatchedUsersForm;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sushanth on 10/26/17.
 */



public class SearchFormXML extends android.support.v4.app.Fragment {

    final private int NAMES = 0;
    final private int TAGS = 1;
    final private int IDS = 2;
    final private int MAX_TAGS = 4;

    Button searchForUsersButton;
    Button searchForRandomUsersButton;

    View rootView;
    LayoutInflater inflater;
    ViewGroup container;
    Bundle savedInstanceState;


    protected FirebaseAuth firebaseAuth;
    protected DatabaseReference dbReference;
    protected FirebaseUser fireBaseUser;

    protected User currentUserProfile;
    protected ArrayList<User> allUsers;
    private ArrayList<ArrayList<String>> usersT;
    protected ArrayList<String> names;
    protected ArrayList<String> tags;
    protected ArrayList<String> IDs;

    TextView label;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.rootView = inflater.inflate(R.layout.search_fragment, container, false);
        label = (TextView) rootView.findViewById(R.id.search_label);
        searchForUsersButton = (Button) rootView.findViewById(R.id.searchForUsersButton);
        searchForRandomUsersButton = (Button)rootView.findViewById(R.id.searchForRandomUsersButton);


        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;


        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        fireBaseUser = firebaseAuth.getCurrentUser();


        allUsers = new ArrayList<User>();
        names = new ArrayList<String>();
        tags = new ArrayList<String>();
        IDs = new ArrayList<String>();

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
                    allUsers.add(user);
                    if (user.isProfileCreated()) {
                        names.add(user.getFirst_name() + " " + user.getLast_name());
                        String tagsString = "";
                        for (int i=0; i < MAX_TAGS && i < user.getTags().size(); i++) {
                            tagsString += user.getTags().get(i) + ", ";

                        }
                        tagsString = tagsString.substring(0, tagsString.length()-2);
                        tags.add(tagsString);

                        IDs.add(user.getIdForFirebase());


                    }
                }


                //label.setText(textToDisplay);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        searchForUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: send arraylist of possible users (found by the algorithm) as part of this intent
                Intent selectMatchedUsersIntent = new Intent(getActivity(), SelectMatchedUsersForm.class);
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersNames", names);
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersTags", tags);
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersIDs", IDs);
                startActivity(selectMatchedUsersIntent);
            }
        });

        RandomSearchForm randomSearchForm = new RandomSearchForm();
        usersT = randomSearchForm.randomSearch();

        searchForRandomUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Send arraylist of random users as part of this intent
                Intent selectMatchedUsersIntent = new Intent(getActivity(), SelectMatchedUsersForm.class);
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersNames", usersT.get(NAMES));
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersTags", usersT.get(TAGS));
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersIDs", usersT.get(IDS));
                startActivity(selectMatchedUsersIntent);

            }
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