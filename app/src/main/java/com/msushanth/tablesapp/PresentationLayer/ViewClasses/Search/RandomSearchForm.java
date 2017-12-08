package com.msushanth.tablesapp.PresentationLayer.ViewClasses.Search;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.Interfaces.Search.FindRandomUsersInterface;
import com.msushanth.tablesapp.User;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Sushanth on 11/9/17.
 */

public class RandomSearchForm implements FindRandomUsersInterface {

    private final int MAX_TAGS = 4;
    private final int NUM_USERS_TO_SHOW = 10;


    FirebaseAuth firebaseAuth;
    DatabaseReference dbReference;
    FirebaseUser fireBaseUser;

    ArrayList<String> names;
    ArrayList<String> tags;
    private ArrayList<String> posIDs;
    private ArrayList<String> finalIDs;
    private ArrayList<ArrayList<String>> usersT;
    int numUsers;
    User user;


    User currentUserProfile;

    public ArrayList<ArrayList<String>> randomSearch() {

        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        fireBaseUser = firebaseAuth.getCurrentUser();

        names = new ArrayList<String>();
        tags = new ArrayList<String>();
        posIDs = new ArrayList<String>();
        finalIDs = new ArrayList<String>();
        usersT = new ArrayList<>();



        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // This is the current users profile
                currentUserProfile = dataSnapshot.child(fireBaseUser.getUid()).getValue(User.class);

                // This will print out the profile of everyone in the database
                // TODO: store these users in an arraylist to use for the algorithm
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    if (user.isProfileCreated()) {
                        if (!user.getIdForFirebase().equals(currentUserProfile.getIdForFirebase())) {
                            posIDs.add(user.getIdForFirebase());
                        }

                    }
                }

                numUsers = posIDs.size();

                dbReference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(posIDs.size() > 0) {
                            for (int i = 0; i < NUM_USERS_TO_SHOW; i++) {
                                Random random = new Random();

                                int randomNum = random.nextInt(numUsers);

                                String id = posIDs.get(randomNum);
                                posIDs.remove(id);


                                user = dataSnapshot.child(id).getValue(User.class);
                                names.add(user.getFirst_name() + " " + user.getLast_name());
                                String tagsString = "";
                                for (int j = 0; j < MAX_TAGS && j < user.getTags().size(); j++) {
                                    tagsString += user.getTags().get(j) + ", ";

                                }
                                tagsString = tagsString.substring(0, tagsString.length() - 2);
                                tags.add(tagsString);

                                finalIDs.add(user.getIdForFirebase());

                                if (numUsers == 1) {
                                    break;
                                } else {
                                    numUsers--;
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });



        usersT.add(names);
        usersT.add(tags);
        usersT.add(finalIDs);
        return usersT;

    }

}
