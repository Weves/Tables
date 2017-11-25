package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;

import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Account.CreateAccountAction;
import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Chat.CreateChatRoomAction;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/9/17.
 */

public class CreateChatRoomForm {

    private User user1;
    private User user2;
    private Room room;

    /*
    public CreateChatRoomForm(String owner, String time, String data, String location, List<String> users){
        this.owner = owner;
        this.time = time;
        this.data = data;
        this.location = location;
        this.users = users;
    }*/


    public CreateChatRoomForm(User user1, User user2){
        this.user1 = user1;
        this.user2 = user2;
        this.room = new Room(user1, user2);
    }


    public void createRoom(User user1, User user2) {
        CreateChatRoomAction nextLayer = new CreateChatRoomAction(user1, user2);
        nextLayer.createRoom();
    }

}
