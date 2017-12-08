package com.msushanth.tablesapp.PresentationLayer.ViewClasses.Invitation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.InvitationController;
import com.msushanth.tablesapp.Interfaces.Invitation.SelectMatchedUsersInterface;
import com.msushanth.tablesapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 11/9/17.
 */


// handles displaying and selecting of users after the user has searched
public class SelectMatchedUsersView extends AppCompatActivity implements SelectMatchedUsersInterface {

    private Toolbar mToolbar;

    static private RecyclerView recyclerView;
    static private RecyclerView.Adapter adapter;
    private static ProgressDialog progressDialog;

    private List<ListUser> users = new ArrayList<ListUser>();
    private static Context context;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser fireBaseUser;
    String currentUserID;


    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> tags = new ArrayList<String>();
    ArrayList<String> IDs = new ArrayList<String>();

    List<String> roomIDs;

    InvitationController controller = new InvitationController();

    // initialize the screen and get the recycler view set up with all ther specified users
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_users);

        // set the toolbar/title
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        TextView pageTitle = (TextView) mToolbar.findViewById(R.id.pageTitle);
        setSupportActionBar(mToolbar);
        pageTitle.setText("Select Users");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // add the back button to the toolbar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // create recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        fireBaseUser = firebaseAuth.getCurrentUser();
        currentUserID = fireBaseUser.getUid();*/

        // get the users to display passed in by one of the
        Intent intent = getIntent();
        names = intent.getStringArrayListExtra("matchedUsersNames");
        tags = intent.getStringArrayListExtra("matchedUsersTags");
        IDs = intent.getStringArrayListExtra("matchedUsersIDs");

        context = getApplicationContext();

        progressDialog = ProgressDialog.show(SelectMatchedUsersView.this, "Please wait...", "Processing", true);
        users = getUsersToShow(names, tags, IDs);

        // Check if two users have already added each other. If they have, then dont display them on the list of results.
        // REMOVES ALREADY ADDED USERS

       /* databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
        });*/

    }


    // implement the back button to the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    // onclick method for when the sendinvitationsbutton is clicked. Sends invites to the selected users
    // and returns the user to the main screen
    public void sendInvitationsButtonClicked(View v) {

        // go through the list of users and get the selected users
        ArrayList<ListUser> selectedUsers = new ArrayList<ListUser>();
        String selectedUsersNames = "";
        for (int i=0; i< users.size(); i++) {
            if (users.get(i).getSelected()) {
                selectedUsers.add(users.get(i));
                selectedUsersNames += users.get(i).getName() + ", ";
            }
        }

        // tell the users invites are being sent
        if (!selectedUsers.isEmpty()) {
            Toast.makeText(this, "Sending invites to: " +
                    selectedUsersNames.substring(0, selectedUsersNames.length() - 2), Toast.LENGTH_LONG).show();
        }

        // send invitations to the desired users
        sendInvitations(selectedUsers);

        // Add all the rooms the user created by selecting and adding people to the list of room_ids he has
        // I couldnt do this in the method in DAO because it was only adding one of the rooms when user selects more than one
        /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // send actual invites to the users in the selected users array
                roomIDs = new ArrayList<String>();
                for(int i=0; i<selectedUsers.size(); i++) {
                    Room room = new Room(currentUserID, selectedUsers.get(i).getID());
                    String roomID = databaseReference.push().getKey();
                    roomIDs.add(roomID);
                    room.setRoomID(roomID);
                    room.setUser1SentInvite(true);
                    room.setUser2SentInvite(false);
                    room.setUser1Accepted("YES");
                    room.setUser2Accepted("NOT_YET");
                    //createChatDAO.createChatRoom(room, roomID);

                    User user1 = dataSnapshot.child(room.getUser1ID()).getValue(User.class);
                    User user2 = dataSnapshot.child(room.getUser2ID()).getValue(User.class);

                    // Add the users names to the Room Object
                    room.setUser1Name(user1.getFirst_name() + " " + user1.getLast_name());
                    room.setUser2Name(user2.getFirst_name() + " " + user2.getLast_name());

                    // Edit the users rooms list and add the edit to the database
                    /*List<String> chatRoomsUser1 = user1.getRoom_ids();
                    chatRoomsUser1.add(room.getRoomID());
                    user1.setRoom_ids(chatRoomsUser1);*/

                   /* List<String> chatRoomsUser2 = user2.getRoom_ids();
                    chatRoomsUser2.add(room.getRoomID());
                    user2.setRoom_ids(chatRoomsUser2);

                    databaseReference.child(room.getRoomID()).setValue(room);
                    //databaseReference.child(user1.getIdForFirebase()).setValue(user1);
                    databaseReference.child(user2.getIdForFirebase()).setValue(user2);
                }


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
        }); */

        finish();
    }


    // called once the database has gotten the users to display
    public static void setAdapter(ArrayList<ListUser> users) {
        // set the recyclerview to the designated list items and dismiss the dialogue
        adapter = new ListAdapter(users, context);
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();
    }


    // pass down the arraylist
    public void sendInvitations(final ArrayList<ListUser> selectedUsers) {
        controller.sendInvitations(selectedUsers);
    }


    // pass down the values of these arrays to the next layer
    public ArrayList<ListUser> getUsersToShow(ArrayList<String> names, ArrayList<String> tags, ArrayList<String> IDs) {
        return controller.getUsersToShow(names, tags, IDs);
    }


}
