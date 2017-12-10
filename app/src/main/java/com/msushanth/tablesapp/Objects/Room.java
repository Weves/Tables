package com.msushanth.tablesapp.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allenqian on 11/19/17.
 * Updated by Sushanth Mukkamalla on 11/27/17.
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

    private boolean user1SentInvite;
    private boolean user2SentInvite;
    private String user1Accepted;
    private String user2Accepted;



    public String printRoomData() {
        String returnStr = "";

        returnStr += "**** RoomID: ";
        returnStr += this.getRoomID();
        returnStr += "\n";

        returnStr += "**** Time: ";
        returnStr += this.getTime();
        returnStr += "\n";

        returnStr += "**** Date: ";
        returnStr += this.getDate();
        returnStr += "\n";

        returnStr += "**** Location: ";
        returnStr += this.getLocation();
        returnStr += "\n";

        returnStr += "**** User 1 Name: ";
        returnStr += this.getUser1Name();
        returnStr += "\n";

        returnStr += "**** User 1 ID: ";
        returnStr += this.getUser1ID();
        returnStr += "\n";

        returnStr += "**** User 2 Name: ";
        returnStr += this.getUser2Name();
        returnStr += "\n";

        returnStr += "**** User 2 ID: ";
        returnStr += this.getUser2ID();
        returnStr += "\n";

        returnStr += "**** User 1 Sent Invite: ";
        returnStr += this.isUser1SentInvite();
        returnStr += "\n";

        returnStr += "**** User 2 Sent Invite: ";
        returnStr += this.isUser2SentInvite();
        returnStr += "\n";

        returnStr += "**** User 1 Accepted: ";
        returnStr += this.getUser1Accepted();
        returnStr += "\n";

        returnStr += "**** User 2 Accepted: ";
        returnStr += this.getUser2Accepted();
        returnStr += "\n";

        return returnStr;
    }




    public Room() {
        this.setRoomID("");
        this.setTime("");
        this.setDate("");
        this.setLocation("");
        this.setUsers(new ArrayList<String>());
        this.setUser1Name("");
        this.setUser1ID("");
        this.setUser2Name("");
        this.setUser2ID("");
        this.setUser1SentInvite(false);
        this.setUser2SentInvite(false);
        this.setUser1Accepted("NOT_YET");
        this.setUser2Accepted("NOT_YET");
    }




    public Room(String user1ID, String user2ID) {
        this.setUser1ID(user1ID);
        this.setUser2ID(user2ID);
    }




    public Room(String roomID, String user1ID, String user2ID) {
        this.roomID = roomID;
        this.setUser1ID(user1ID);
        this.setUser2ID(user2ID);
    }




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

    public boolean isUser1SentInvite() {
        return user1SentInvite;
    }
    public void setUser1SentInvite(boolean user1SentInvite) {
        this.user1SentInvite = user1SentInvite;
    }

    public boolean isUser2SentInvite() {
        return user2SentInvite;
    }
    public void setUser2SentInvite(boolean user2SentInvite) {
        this.user2SentInvite = user2SentInvite;
    }

    public String getUser1Accepted() {
        return user1Accepted;
    }
    public void setUser1Accepted(String user1Accepted) {
        this.user1Accepted = user1Accepted;
    }

    public String getUser2Accepted() {
        return user2Accepted;
    }
    public void setUser2Accepted(String user2Accepted) {
        this.user2Accepted = user2Accepted;
    }
}
