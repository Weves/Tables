package com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses;

import com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses.CreateChatRoomManager;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

/**
 * Created by khoabui on 11/24/17.
 */

public class CreateChatRoomDispatch {
    private User user1;
    private User user2;
    private Room room;

    public CreateChatRoomDispatch(User user1, User user2){
        this.user1 = user1;
        this.user2 = user2;
        this.room = new Room(user1, user2);
    }

    public void createRoom(){
        CreateChatRoomManager nextLayer = new CreateChatRoomManager(user1, user2);
        nextLayer.createRoom();
    }
}
