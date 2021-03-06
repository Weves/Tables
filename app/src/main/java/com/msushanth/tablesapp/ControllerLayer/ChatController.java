package com.msushanth.tablesapp.ControllerLayer;

import com.msushanth.tablesapp.DataAccessLayer.ChatRoomsDAO;
import com.msushanth.tablesapp.Interfaces.Chat.ChatInterface;
import com.msushanth.tablesapp.Objects.Room;
import com.msushanth.tablesapp.Objects.User;

/**
 * Created by Sushanth on 11/10/17.
 */

public class ChatController implements ChatInterface {

    Room thisRoom;
    User sender;

    public ChatController(Room thisRoom, User sender) {
        this.thisRoom = thisRoom;
        this.sender = sender;
    }
    public ChatController(){}
    @Override
    public void postMessage(String message) {
        ChatRoomsDAO msgMan = new ChatRoomsDAO(thisRoom, sender);
        msgMan.postMessage(message);
    }
    public void setTimeDateLocation(Room room) {
        ChatRoomsDAO msgMan = new ChatRoomsDAO();
        msgMan.setTimeDateLocation(room);
    }



    @Override
    public void deleteChatRoom() {
        ChatRoomsDAO msgMan = new ChatRoomsDAO(thisRoom, sender);
        msgMan.deleteChatRoom();
    }
}
