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


        /*firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        fireBaseUser = firebaseAuth.getCurrentUser();


        allUsers = new ArrayList<User>();
        names = new ArrayList<String>();
        tags = new ArrayList<String>();
        IDs = new ArrayList<String>();
        IDsForRandom = new ArrayList<String>();
        allLevels = new ArrayList<pair<Double,Integer>>();


        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // This is the current users profile
                currentUserProfile = dataSnapshot.child(fireBaseUser.getUid()).getValue(User.class);
                ArrayList<Integer> userLevels = new ArrayList<Integer>(currentUserProfile.getInterests().values());



                // This will get the profile of everyone in the database
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    //textToDisplay += "\n\n\n****************\n*** ANOTHER USER ***\n";
                    //textToDisplay += user.userDataToPrint();
                    if (user.isProfileCreated()) {
                        allUsers.add(user);


                        names.add(user.getFirst_name() + " " + user.getLast_name());
                        String tagsString = "";
                        for (int i=0; i < MAX_TAGS && i < user.getTags().size(); i++) {
                            tagsString += user.getTags().get(i) + ", ";
                        }
                        tagsString = tagsString.substring(0, tagsString.length()-2);
                        tags.add(tagsString);
                        IDs.add(user.getIdForFirebase());
                        IDsForRandom.add(user.getIdForFirebase());


                        ArrayList<Integer> currLevels = new ArrayList<Integer>(user.getInterests().values());
                        Double distance = euclideanDist(userLevels, currLevels);
                        allLevels.add(new pair(distance, IDs.size() - 1));
                    }
                }

                Collections.sort(allLevels, new SortByDist());

                names = new ArrayList<String>();
                tags = new ArrayList<String>();
                IDs = new ArrayList<String>();
                for(int i = 0; i < allLevels.size(); i++){

                    int index = allLevels.get(i).index;
                    User user1 = allUsers.get(index);

                    if(!user1.getIdForFirebase().equals(currentUserProfile.getIdForFirebase())) {


                        names.add(user1.getFirst_name() + " " + user1.getLast_name());
                        String tagsString = "";
                        for (int x = 0; x < MAX_TAGS && x < user1.getTags().size(); x++) {
                            tagsString += user1.getTags().get(x) + ", ";
                        }
                        tagsString = tagsString.substring(0, tagsString.length() - 2);
                        tags.add(tagsString);
                        IDs.add(user1.getIdForFirebase());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        }); */

        usersS = searchController.searchEngine();

        searchForUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //send arraylist of possible users (found by the algorithm) as part of this intent
                Intent selectMatchedUsersIntent = new Intent(getActivity(), SelectMatchedUsersView.class);
                System.out.println("*************" +  usersS.get(NAMES));
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersNames", usersS.get(NAMES));
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersTags", usersS.get(TAGS));
                selectMatchedUsersIntent.putStringArrayListExtra("matchedUsersIDs", usersS.get(IDS));
                startActivity(selectMatchedUsersIntent);

                usersS = searchController.searchEngine();
            }
        });


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