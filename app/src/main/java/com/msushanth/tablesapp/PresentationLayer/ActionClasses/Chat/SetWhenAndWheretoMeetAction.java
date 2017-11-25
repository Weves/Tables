package com.msushanth.tablesapp.PresentationLayer.ActionClasses.Chat;

import com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses.ChatDispatch;
import com.msushanth.tablesapp.Interfaces.Chat.SetWhenAndWhereToMeetInterface;

/**
 * Created by Sushanth on 11/10/17.
 */

public class SetWhenAndWheretoMeetAction implements SetWhenAndWhereToMeetInterface {
    public SetWhenAndWheretoMeetAction() {}
    @Override
    public void setMeetingDate(String meetingDate){
        ChatDispatch dispatch = new ChatDispatch();
        dispatch.setMeetingDate(meetingDate);
    }
    @Override
    public void setMeetingTime(String meetingTime){
        ChatDispatch dispatch = new ChatDispatch();
        dispatch.setMeetingDate(meetingTime);
    }
    @Override
    public void setMeetingLocation(String meetingLocation){
        ChatDispatch dispatch = new ChatDispatch();
        dispatch.setMeetingDate(meetingLocation);
    }
}