package com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses;

import com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses.ChatManager;
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

    public ChatDispatch(Room thisRoom, User poster){
        this.thisRoom = thisRoom;
        this.sender = sender;
        //other coders add other constructors below if needed
    }


    @Override
    public void postMessage(String message) {
        ChatManager msgMan = new ChatManager(thisRoom, sender);
        msgMan.postMessage(message);
    }
}
