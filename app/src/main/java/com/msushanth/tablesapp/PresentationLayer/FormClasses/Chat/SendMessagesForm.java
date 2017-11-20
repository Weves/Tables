package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;


import com.msushanth.tablesapp.Interfaces.Chat.SendMessagesInterface;
import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Chat.SendMessagesAction;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/9/17
 * Last Edited by Yuhong on 11/19/17.
 */

public class SendMessagesForm implements SendMessagesInterface {
    //This class must operate from within a chatroom
    Room thisRoom;
    User sender;

    public SendMessagesForm(Room thisRoom, User sender){
        this.thisRoom = thisRoom;
        this.sender = sender;
    }

    public void postMessage(String message){
        SendMessagesAction msgAct = new SendMessagesAction(thisRoom, sender);
        msgAct.postMessage(message);
    }
}
