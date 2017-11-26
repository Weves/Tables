package com.msushanth.tablesapp.PresentationLayer.ActionClasses.Chat;

import com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses.ChatDispatch;
import com.msushanth.tablesapp.Interfaces.Chat.SetWhenAndWhereToMeetInterface;
import com.msushanth.tablesapp.Room;

/**
 * Created by Sushanth on 11/10/17.
 */

public class SetWhenAndWheretoMeetAction implements SetWhenAndWhereToMeetInterface {
    public SetWhenAndWheretoMeetAction() {}
    @Override
    public void setTimeDateLocation(Room room){
        ChatDispatch dispatch = new ChatDispatch();
        dispatch.setTimeDateLocation(room);
    }

}