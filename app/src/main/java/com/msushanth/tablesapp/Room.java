package com.msushanth.tablesapp;

import java.util.List;

/**
 * Created by allenqian on 11/19/17.
 */

public class Room {
    private String roomID;
    private String time;
    private String date;
    private String location;
    private List<String> users;

    private String user1Name;
    private String user1ID;

    private String user2Name;
    private String user2ID;




    public Room(String roomID, String user1Name, String user1ID, String user2Name, String user2ID) {
        this.setRoomID(roomID);
        this.setUser1Name(user1Name);
        this.setUser1ID(user1ID);
        this.setUser2Name(user2Name);
        this.setUser2ID(user2ID);
    }

    public Room(String roomID, String time, String date, String location, List<String> users){
        this.setRoomID(roomID);
        this.setTime(time);
        this.setDate(date);
        this.setLocation(location);
        this.setUsers(users);
    }

    public Room (User u1, User u2) {}




    public String getRoomID() {
        return roomID;
    }
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getUsers() {
        return users;
    }
    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getUser1Name() {
        return user1Name;
    }
    public void setUser1Name(String user1Name) {
        this.user1Name = user1Name;
    }

    public String getUser1ID() {
        return user1ID;
    }
    public void setUser1ID(String user1ID) {
        this.user1ID = user1ID;
    }

    public String getUser2Name() {
        return user2Name;
    }
    public void setUser2Name(String user2Name) {
        this.user2Name = user2Name;
    }

    public String getUser2ID() {
        return user2ID;
    }
    public void setUser2ID(String user2ID) {
        this.user2ID = user2ID;
    }
}
