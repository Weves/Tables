package com.msushanth.tablesapp.DataAccessLayer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses.ChatManager;
import com.msushanth.tablesapp.Interfaces.Chat.ChatInterface;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

import java.util.List;

/**
 * Created by Sushanth on 11/10/17.
 */

public class ChatRoomsDAO implements ChatInterface {

    User sender;
    Room thisRoom;

    Room roomToUpload;


    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;



    public ChatRoomsDAO() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }


    // Add the two users ID's should already be in the Room object
    // We have to add the users names to the Room
    // All we have to do now is create a unique ID for the room and add the room to the database
    // Also update the list of chats variable for each user
    public void createChatRoom(Room roomParam, String roomID) {
        roomParam.setRoomID(roomID);

        // This is not final. we just need to declare it as final in order to access it in the listener
        this.roomToUpload = roomParam;

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user1 = dataSnapshot.child(roomToUpload.getUser1ID()).getValue(User.class);
                User user2 = dataSnapshot.child(roomToUpload.getUser2ID()).getValue(User.class);

                // Check if the two users have already added each other.


                // Add the users names to the Room Object
                roomToUpload.setUser1Name(user1.getFirst_name() + " " + user1.getLast_name());
                roomToUpload.setUser2Name(user2.getFirst_name() + " " + user2.getLast_name());

                // Edit the users rooms list and add the edit to the database
                /*List<String> chatRoomsUser1 = user1.getRoom_ids();
                chatRoomsUser1.add(room.getRoomID());
                user1.setRoom_ids(chatRoomsUser1);*/

                List<String> chatRoomsUser2 = user2.getRoom_ids();
                chatRoomsUser2.add(roomToUpload.getRoomID());
                user2.setRoom_ids(chatRoomsUser2);

                databaseReference.child(roomToUpload.getRoomID()).setValue(roomToUpload);
                //databaseReference.child(user1.getIdForFirebase()).setValue(user1);
                databaseReference.child(user2.getIdForFirebase()).setValue(user2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }













    public ChatRoomsDAO(Room thisRoom, User sender) {
        this.thisRoom = thisRoom;
        this.sender = sender;
    }

    @Override
    public void postMessage(String message) {
        // get data from database
    }


    @Override
    public void deleteChatRoom() {

    }

    @Override
    public void setTimeDateLocation(Room room) {

    }


}
