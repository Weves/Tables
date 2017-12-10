package com.msushanth.tablesapp.DataAccessLayer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.ControllerLayer.InvitationController;
import com.msushanth.tablesapp.Objects.ListUser;
import com.msushanth.tablesapp.Objects.Room;
import com.msushanth.tablesapp.Objects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sushanth on 11/19/17.
 */

public class InvitationDAO {

    // get from database the ten users we want to show on the screen
    public ArrayList<ListUser> getUsersToShow(final ArrayList<String> names, final ArrayList<String> tags, final ArrayList<String> IDs) {

        final ArrayList<ListUser> users = new ArrayList<>();

        /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser fireBaseUser = firebaseAuth.getCurrentUser();
        String currentUserID = fireBaseUser.getUid();*/

        // go into the database a single time
        CurrentUserInfo.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.child(CurrentUserInfo.currentUserID).getValue(User.class);
                List<String> currentUserRooms = currentUser.getRoom_ids();

                // do not get users the current user has already added
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

                    // otherwise, order of priority given to us by a search algorithm (order of passed in list)
                    // add them to the arraylist to be displayed
                    if(!usersAlreadyAdded) {
                        ListUser user = new ListUser(names.get(i), tags.get(i), IDs.get(i));
                        users.add(user);
                    }
                }

                InvitationController.setAdapter(users);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        // return the array of users that we are to display
        return users;

    }


    // send invitations to other users
    public void sendInvitations(final ArrayList<ListUser> selectedUsers) {

        final ArrayList<String> roomIDs = new ArrayList<String>();

        CurrentUserInfo.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // send actual invites to the users in the selected users array
                for(int i=0; i<selectedUsers.size(); i++) {
                    Room room = new Room(CurrentUserInfo.currentUserID, selectedUsers.get(i).getID());
                    String roomID = CurrentUserInfo.databaseReference.push().getKey();
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

                    List<String> chatRoomsUser2 = user2.getRoom_ids();
                    chatRoomsUser2.add(room.getRoomID());
                    user2.setRoom_ids(chatRoomsUser2);

                    CurrentUserInfo.databaseReference.child(room.getRoomID()).setValue(room);
                    //databaseReference.child(user1.getIdForFirebase()).setValue(user1);
                    CurrentUserInfo.databaseReference.child(user2.getIdForFirebase()).setValue(user2);
                }


                User user1 = dataSnapshot.child(CurrentUserInfo.currentUserID).getValue(User.class);

                List<String> chatRoomsUser1 = user1.getRoom_ids();
                for(int i=0; i<roomIDs.size(); i++) {
                    chatRoomsUser1.add(roomIDs.get(i));
                }
                user1.setRoom_ids(chatRoomsUser1);


                CurrentUserInfo.databaseReference.child(user1.getIdForFirebase()).setValue(user1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}