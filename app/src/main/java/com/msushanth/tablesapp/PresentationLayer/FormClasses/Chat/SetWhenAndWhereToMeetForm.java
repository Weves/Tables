package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.msushanth.tablesapp.Interfaces.Chat.SetWhenAndWhereToMeetInterface;
import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Chat.SetWhenAndWheretoMeetAction;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.User;

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
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_time_location);
        meetingTimePicker = (TimePicker) findViewById(R.id.timePicker);
        meetingDatePicker = (DatePicker) findViewById(R.id.datePicker);
        meetingLocationText = (EditText) findViewById(R.id.LocationInputText);



    }
    public void submitButtonClicked(View view) {

    }
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