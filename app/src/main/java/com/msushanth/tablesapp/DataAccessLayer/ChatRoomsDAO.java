package com.msushanth.tablesapp.DataAccessLayer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses.ChatManager;
import com.msushanth.tablesapp.Interfaces.Chat.ChatInterface;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

import java.util.List;

/**
 * Created by Sushanth on 11/10/17.
 */

public class ChatRoomsDAO implements ChatInterface {

    Room thisRoom;
    User sender;



    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;



    public ChatRoomsDAO() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }


    // Add the two users to the chat room
    // Add the Chat Room ID to the room id list of each user
    // All we have to do now is create a unique ID for the room and add the room to the database
    public void createChatRoom(Room room, User user1, User user2) {
        this.thisRoom = room;

        thisRoom.setRoomID(databaseReference.push().getKey());
        databaseReference.child(thisRoom.getRoomID()).setValue(this.thisRoom);


        // Edit the users rooms list and add the edit to the database
        List<String> chatRoomsUser1 = user1.getRoom_ids();
        chatRoomsUser1.add(thisRoom.getRoomID());
        user1.setRoom_ids(chatRoomsUser1);

        List<String> chatRoomsUser2 = user2.getRoom_ids();
        chatRoomsUser2.add(thisRoom.getRoomID());
        user2.setRoom_ids(chatRoomsUser1);

        databaseReference.child(user1.getIdForFirebase()).setValue(user1);
        databaseReference.child(user2.getIdForFirebase()).setValue(user2);

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
