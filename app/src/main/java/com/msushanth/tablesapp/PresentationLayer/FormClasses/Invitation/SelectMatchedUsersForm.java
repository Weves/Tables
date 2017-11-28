package com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.DataAccessLayer.ChatRoomsDAO;
import com.msushanth.tablesapp.Interfaces.Invitation.SelectMatchedUsersInterface;
import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Invitation.SelectMatchedUserAction;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Profile.EditPersonalProfileForm;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sushanth on 11/9/17.
 */

public class SelectMatchedUsersForm extends AppCompatActivity implements SelectMatchedUsersInterface {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListUser> users = new ArrayList<ListUser>();

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser fireBaseUser;
    String currentUserID;

    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> tags = new ArrayList<String>();
    ArrayList<String> IDs = new ArrayList<String>();

    List<String> roomIDs;

    SelectMatchedUserAction action = new SelectMatchedUserAction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_users);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        fireBaseUser = firebaseAuth.getCurrentUser();
        currentUserID = fireBaseUser.getUid();

        Intent intent = getIntent();
        names = intent.getStringArrayListExtra("matchedUsersNames");
        tags = intent.getStringArrayListExtra("matchedUsersTags");
        IDs = intent.getStringArrayListExtra("matchedUsersIDs");

        // Check if two users have already added each other. If they have, then dont display them on the list of results.
        // REMOVES ALREADY ADDED USERS
        final ProgressDialog progressDialog = ProgressDialog.show(SelectMatchedUsersForm.this, "Please wait...", "Processing", true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.child(currentUserID).getValue(User.class);
                List<String> currentUserRooms = currentUser.getRoom_ids();

                for (int i=0; i <names.size(); i++) {
                    User checkListUser = dataSnapshot.child(IDs.get(i)).getValue(User.class);
                    List<String> checkListUserRooms = checkListUser.getRoom_ids();

                    boolean usersAlreadyAdded = false;
                    for(int x=0; x<currentUserRooms.size(); x++) {
                        for(int y=0; y<checkListUserRooms.size(); y++) {
                            if(currentUserRooms.get(x).equals(checkListUserRooms.get(y))) {
                                usersAlreadyAdded = true;
                            }
                        }
                    }

                    if(!usersAlreadyAdded) {
                        ListUser user = new ListUser(names.get(i), tags.get(i), IDs.get(i));
                        users.add(user);
                    }
                }

                adapter = new ListAdapter(users, getApplicationContext());
                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    public void sendInvitationsButtonClicked(View v) {
        ArrayList<ListUser> selectedUsers = new ArrayList<ListUser>();
        String selectedUsersNames = "";
        for (int i=0; i< users.size(); i++) {
            if (users.get(i).getSelected()) {
                selectedUsers.add(users.get(i));
                selectedUsersNames += users.get(i).getName() + ", ";
            }
        }

        if (!selectedUsers.isEmpty()) {
            Toast.makeText(this, "Sending invites to: " +
                    selectedUsersNames.substring(0, selectedUsersNames.length() - 2), Toast.LENGTH_LONG).show();
        }

        // send actual invites to the users in the selected users array
        ChatRoomsDAO createChatDAO = new ChatRoomsDAO();
        roomIDs = new ArrayList<String>();
        for(int i=0; i<selectedUsers.size(); i++) {
            Room room = new Room(currentUserID, selectedUsers.get(i).getID());
            String roomID = databaseReference.push().getKey();
            roomIDs.add(roomID);
            createChatDAO.createChatRoom(room, roomID);
        }

        // Add all the rooms the user created by selecting and adding people to the list of room_ids he has
        // I couldnt do this in the method in DAO because it was only adding one of the rooms when user selects more than one
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.child(currentUserID).getValue(User.class);

                List<String> chatRoomsUser1 = user1.getRoom_ids();
                for(int i=0; i<roomIDs.size(); i++) {
                    chatRoomsUser1.add(roomIDs.get(i));
                }
                user1.setRoom_ids(chatRoomsUser1);


                databaseReference.child(user1.getIdForFirebase()).setValue(user1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        finish();
    }




    public void backButtonClicked(View view) {
        finish();
    }
}
