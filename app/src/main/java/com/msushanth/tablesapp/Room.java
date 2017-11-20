package com.msushanth.tablesapp;

import java.util.List;

/**
 * Created by allenqian on 11/19/17.
 */

public class Room {
    String owner;
    String time;
    String data;
    String location;
    List<String> users;

    public Room(String owner, String time, String data, String location, List<String> users){
        this.owner = owner;
        this.time = time;
        this.data = data;
        this.location = location;
        this.users = users;
    }
}
