package com.msushanth.tablesapp.Interfaces.Chat;

/**
 * Created by Sushanth on 11/19/17.
 * Last Edited by Yuhong on 11/19/17.
 */

public interface SendMessagesInterface {
    //writes a message into the room where all other users
    //in the room can view it and see the sender name
    void postMessage(String message);
}
