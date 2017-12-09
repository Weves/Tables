package com.msushanth.tablesapp.PresentationLayer.ViewClasses.Search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.SearchController;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Invitation.SelectMatchedUsersView;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.User;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sushanth on 10/26/17.
 */


// class to handle
public class SearchView extends android.support.v4.app.Fragment {

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

    private SearchController searchController = new SearchController();


    protected FirebaseAuth firebaseAuth;
    protected DatabaseReference dbReference;
    protected FirebaseUser fireBaseUser;

    protected User currentUserProfile;
    protected ArrayList<User> allUsers;
    private ArrayList<ArrayList<String>> usersR;
    private ArrayList<ArrayList<String>> usersS;
    protected ArrayList<String> names;
    protected ArrayList<String> tags;
    protected ArrayList<String> IDs;
    private ArrayList<String> IDsForRandom;
    protected ArrayList<Pair<Double,Integer>> allLevels;

    TextView label;

    // create the search screen that gives users two options
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.rootView = inflater.inflate(R.layout.search_fragment, container, false);
        //label = (TextView) rootView.findViewById(R.id.search_label);
        searchForUsersButton = (Button) rootView.findViewById(R.id.searchForUsersButton);
        searchForRandomUsersButton = (Button)rootView.findViewById(R.id.searchForRandomUsersButton);


        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;

        // use algorithm to find similar users
        usersS = searchController.searchEngine();

        searchForUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //send arraylist of possible users (found by the algorithm) as part of this intent
                Intent selectMatchedUsersIntent = new Intent(getActivity(), SelectMatchedUsersView.class);
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersNames", usersS.get(NAMES));
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersTags", usersS.get(TAGS));
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersIDs", usersS.get(IDS));
                startActivity(selectMatchedUsersIntent);

                usersS = searchController.searchEngine();
            }
        });

        // find random users
        usersR = searchController.randomSearch();

        searchForRandomUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Send arraylist of random users as part of this intent
                Intent selectMatchedUsersIntent = new Intent(getActivity(), SelectMatchedUsersView.class);
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersNames", usersR.get(NAMES));
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersTags", usersR.get(TAGS));
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersIDs", usersR.get(IDS));
                startActivity(selectMatchedUsersIntent);

                usersR = searchController.randomSearch();

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