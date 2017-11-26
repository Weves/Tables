package com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.msushanth.tablesapp.Interfaces.Chat.SetWhenAndWhereToMeetInterface;
import com.msushanth.tablesapp.MainActivity;
import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Chat.SetWhenAndWheretoMeetAction;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Account.LogInForm;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sushanth on 11/9/17.
 */

public class SetWhenAndWhereToMeetForm extends AppCompatActivity implements SetWhenAndWhereToMeetInterface {
    TimePicker meetingTimePicker;
    DatePicker meetingDatePicker;
    EditText meetingLocationText;
    String roomID;
    String time;
    String date;
    String meetingLocation;
    List<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_time_location);
        meetingTimePicker = (TimePicker) findViewById(R.id.timePicker);
        meetingDatePicker = (DatePicker) findViewById(R.id.datePicker);
        meetingLocationText = (EditText) findViewById(R.id.LocationInputText);

        // assume you have a roomID and the users
        roomID = "";
        time = "";
        date = "";
        meetingLocation = "";
        users = new ArrayList<String>();
    }


    public void submitButtonClicked(View view) {

        // create the room
        //assume you have a roomID and the user assign them to null


        int hour = meetingTimePicker.getCurrentHour();
        int min = meetingTimePicker.getCurrentMinute();

        //setTimeDateLocation(room);
        /*Intent logInIntent = new Intent();
        startActivity(logInIntent);
        finish();*/
    }
    public boolean checkTimeAndDate(){return true;}
    @Override
    public void setTimeDateLocation(Room room){
        SetWhenAndWheretoMeetAction act = new SetWhenAndWheretoMeetAction();
        act.setTimeDateLocation(room);
    }

}