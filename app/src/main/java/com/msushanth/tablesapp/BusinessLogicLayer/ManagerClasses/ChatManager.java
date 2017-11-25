package com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses;

import com.msushanth.tablesapp.DataAccessLayer.ChatRoomsDAO;
import com.msushanth.tablesapp.Interfaces.Chat.ChatInterface;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/10/17.
 */

public class ChatManager implements ChatInterface {

    Room thisRoom;
    User sender;

    public ChatManager(Room thisRoom, User sender) {
        this.thisRoom = thisRoom;
        this.sender = sender;
    }
    public ChatManager(){}
    @Override
    public void postMessage(String message) {
        ChatRoomsDAO msgMan = new ChatRoomsDAO(thisRoom, sender);
        msgMan.postMessage(message);
    }
    public void setMeetingDate(String meetingDate) {
        ChatRoomsDAO msgMan = new ChatRoomsDAO();
        msgMan.setMeetingDate(meetingDate);
    }

    public void setMeetingTime(String meetingTime) {
        ChatRoomsDAO msgMan = new ChatRoomsDAO();
        msgMan.setMeetingTime(meetingTime);
    }

    public void setMeetingLocation(String meetingLocation) {
        ChatRoomsDAO msgMan = new ChatRoomsDAO();
        msgMan.setMeetingLocation(meetingLocation);
    }
}
