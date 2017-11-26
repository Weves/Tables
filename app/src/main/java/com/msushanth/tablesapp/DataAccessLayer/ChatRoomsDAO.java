package com.msushanth.tablesapp.DataAccessLayer;

import com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses.ChatManager;
import com.msushanth.tablesapp.Interfaces.Chat.ChatInterface;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/10/17.
 */

public class ChatRoomsDAO implements ChatInterface {

    Room thisRoom;
    User sender;

    public ChatRoomsDAO(Room thisRoom, User sender) {
        this.thisRoom = thisRoom;
        this.sender = sender;
    }

    @Override
    public void postMessage(String message) {
        // get data from database
    }

    public void createRoom(User user1, User user2){
        // set room for users
    }
}
