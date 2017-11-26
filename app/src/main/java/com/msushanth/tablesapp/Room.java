package com.msushanth.tablesapp;

import java.util.List;

/**
 * Created by allenqian on 11/19/17.
 */

public class Room {
    String roomID;
    String time;
    String date;
    String location;
    List<String> users;

    public Room(String roomID, String time, String date, String location, List<String> users){
        this.roomID = roomID;
        this.time = time;
        this.date = date;
        this.location = location;
        this.users = users;
    }

    public Room (User u1, User u2) {}
}
