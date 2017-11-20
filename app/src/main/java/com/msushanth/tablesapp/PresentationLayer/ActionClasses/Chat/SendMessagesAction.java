package com.msushanth.tablesapp.PresentationLayer.ActionClasses.Chat;

import com.msushanth.tablesapp.Interfaces.Chat.SendMessagesInterface;
import com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses.ChatDispatch;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/9/17
 * Last Edited by Yuhong on 11/19/17.
 */

public class SendMessagesAction implements SendMessagesInterface{
    Room thisRoom;
    User sender;

    public SendMessagesAction(Room thisRoom, User sender){
        this.thisRoom = thisRoom;
        this.sender = sender;
    }

    public void postMessage(String message){
        ChatDispatch msgDisp = new ChatDispatch(thisRoom, sender);
        msgDisp.postMessage(message);
    }
}
