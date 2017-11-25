package com.msushanth.tablesapp.PresentationLayer.FormClasses.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.Interfaces.Search.FindRandomUsersInterface;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation.SelectMatchedUsersForm;
import com.msushanth.tablesapp.User;

import java.util.ArrayList;

/**
 * Created by Sushanth on 11/9/17.
 */

public class RandomSearchForm implements FindRandomUsersInterface {

    private final int MAX_TAGS = 4;

    FirebaseAuth firebaseAuth;
    DatabaseReference dbReference;
    FirebaseUser fireBaseUser;

    ArrayList<String> names;
    ArrayList<String> tags;
    ArrayList<String> IDs;

    User currentUserProfile;

    public ArrayList<ArrayList<String>> randomSearch() {

        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        fireBaseUser = firebaseAuth.getCurrentUser();

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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        ArrayList<ArrayList<String>> usersT = new ArrayList<>();
        usersT.add(names);
        usersT.add(tags);
        usersT.add(IDs);
        return usersT;

    }

}
