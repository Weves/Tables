package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;

import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.msushanth.tablesapp.Interfaces.Chat.SetWhenAndWhereToMeetInterface;
import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Chat.SetWhenAndWheretoMeetAction;

/**
 * Created by Sushanth on 11/9/17.
 */

public class SetWhenAndWhereToMeetForm extends AppCompatActivity implements SetWhenAndWhereToMeetInterface {

    TimePicker meetingTimePicker;
    DatePicker meetingDatePicker;
    EditText meetingLocationText;
    String time;
    String Date;
    String meetingLocation;
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(//TODO);

    }*/
    @Override
    public void setMeetingDate(String meetingDate){
        SetWhenAndWheretoMeetAction act = new SetWhenAndWheretoMeetAction();
        act.setMeetingDate(meetingDate);
    }
    @Override
    public void setMeetingTime(String meetingTime){
        SetWhenAndWheretoMeetAction act = new SetWhenAndWheretoMeetAction();
        act.setMeetingDate(meetingTime);
    }
    @Override
    public void setMeetingLocation(String meetingLocation){
        SetWhenAndWheretoMeetAction act = new SetWhenAndWheretoMeetAction();
        act.setMeetingDate(meetingLocation);
    }
}