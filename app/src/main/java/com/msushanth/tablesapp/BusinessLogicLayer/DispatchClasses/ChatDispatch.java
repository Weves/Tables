package com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses;

import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.ChatManager;
import com.msushanth.tablesapp.Interfaces.Chat.ChatInterface;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/10/17.
 * Last Edited by Yuhong on 11/19/17
 */


public class ChatDispatch implements ChatInterface {
    Room thisRoom;
    User sender;

    public ChatDispatch(Room thisRoom, User sender){
        this.thisRoom = thisRoom;
        this.sender = sender;
        //other coders add other constructors below if needed
    }
    public ChatDispatch(){
    }

    @Override
    public void postMessage(String message) {
        ChatManager msgMan = new ChatManager(thisRoom, sender);
        msgMan.postMessage(message);
    }
    public void deleteChatRoom() {
        ChatManager ma = new ChatManager(thisRoom, sender);
        ma.deleteChatRoom();
    }

    @Override
    public void setTimeDateLocation(Room room) {

    }
}
