package com.msushanth.tablesapp.DataAccessLayer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Search.SortByDist;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Search.Pair;
import com.msushanth.tablesapp.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Sushanth on 11/19/17.
 */

public class SearchDAO {

    private final int MAX_TAGS = 4;
    private final int NUM_USERS_TO_SHOW = 10;

    private int numUsers;


    User currentUserProfile;

    // use the algorithm to give back a random list of users
    public ArrayList<ArrayList<String>> randomSearch() {


        final ArrayList<String> names = new ArrayList<String>();
        final ArrayList<String> tags = new ArrayList<String>();
        final ArrayList<String> posIDs = new ArrayList<String>();
        final ArrayList<String> finalIDs = new ArrayList<String>();
        final ArrayList<ArrayList<String>> usersT = new ArrayList<>();



        CurrentUserInfo.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // This is the current users profile
                currentUserProfile = dataSnapshot.child(CurrentUserInfo.fireBaseUser.getUid()).getValue(User.class);

                // This will get the profile of everyone in the database
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    if (user.isProfileCreated()) {
                        if (!user.getIdForFirebase().equals(currentUserProfile.getIdForFirebase())) {
                            posIDs.add(user.getIdForFirebase());
                        }

                    }
                }

                numUsers = posIDs.size();

                CurrentUserInfo.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(posIDs.size() > 0) {
                            for (int i = 0; i < NUM_USERS_TO_SHOW; i++) {
                                Random random = new Random();

                                int randomNum = random.nextInt(numUsers);

                                String id = posIDs.get(randomNum);
                                posIDs.remove(id);


                                User user = dataSnapshot.child(id).getValue(User.class);
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



    // use the algorithm to give back an ordered list of users
    public ArrayList<ArrayList<String>> searchEngine() {

        final ArrayList<User> allUsers = new ArrayList<User>();
        final ArrayList<String> names = new ArrayList<String>();
        final ArrayList<String> tags = new ArrayList<String>();
        final ArrayList<String> IDs = new ArrayList<String>();
        final ArrayList<String> finalNames = new ArrayList<String>();
        final ArrayList<String> finalTags = new ArrayList<String>();
        final ArrayList<String> finalIDs = new ArrayList<String>();
        final ArrayList<Pair<Double,Integer>> allLevels = new ArrayList<Pair<Double,Integer>>();
        final ArrayList<ArrayList<String>> usersS = new ArrayList<>();


        CurrentUserInfo.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // This is the current users profile
                currentUserProfile = dataSnapshot.child(CurrentUserInfo.fireBaseUser.getUid()).getValue(User.class);
                ArrayList<Integer> userLevels = new ArrayList<Integer>(currentUserProfile.getInterests().values());

                // This will get the profiles of everyone in the database
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    // only get created profiles
                    if (user.isProfileCreated()) {
                        allUsers.add(user);

                        // get relevant info
                        names.add(user.getFirst_name() + " " + user.getLast_name());
                        String tagsString = "";
                        for (int i=0; i < MAX_TAGS && i < user.getTags().size(); i++) {
                            tagsString += user.getTags().get(i) + ", ";
                        }
                        tagsString = tagsString.substring(0, tagsString.length()-2);
                        tags.add(tagsString);
                        IDs.add(user.getIdForFirebase());

                        // find distance
                        ArrayList<Integer> currLevels = new ArrayList<Integer>(user.getInterests().values());
                        Double distance = euclideanDist(userLevels, currLevels);
                        allLevels.add(new Pair(distance, IDs.size() - 1));

                    }
                }

                // start with the closest points
                Collections.sort(allLevels, new SortByDist());


                for(int i = 0; i < NUM_USERS_TO_SHOW && i < allUsers.size(); i++){

                    int index = allLevels.get(i).index;
                    User user1 = allUsers.get(index);

                    if(!user1.getIdForFirebase().equals(currentUserProfile.getIdForFirebase())) {


                        finalNames.add(user1.getFirst_name() + " " + user1.getLast_name());
                        String tagsString = "";
                        for (int x = 0; x < MAX_TAGS && x < user1.getTags().size(); x++) {
                            tagsString += user1.getTags().get(x) + ", ";
                        }
                        tagsString = tagsString.substring(0, tagsString.length() - 2);
                        finalTags.add(tagsString);
                        finalIDs.add(user1.getIdForFirebase());
                    }
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });



        usersS.add(finalNames);
        usersS.add(finalTags);
        usersS.add(finalIDs);
        return usersS;

    }


    // get the euclidian distance between two users in the thisUser.size() dimensional space
    public Double euclideanDist(ArrayList<Integer> thisUser, ArrayList<Integer> other){
        int sum = 0;

        for(int i = 0; i < thisUser.size(); i ++){
            sum += Math.pow(thisUser.get(i) - other.get(i), 2);
        }

        Double dist = Math.sqrt(sum);

        return dist;
    }
}
