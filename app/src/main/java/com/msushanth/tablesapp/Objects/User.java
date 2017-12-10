package com.msushanth.tablesapp.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by allenqian on 11/19/17.
 */


public class User {

    // ***  IMPORTANT: MUST ADD GETTERS AND SETTERS SO THAT WE CAN STORE THE DATA IN FIREBASE  ***
    // *** FIREBASE REQUIRES GETTERS AND SETTERES ***
    // *** USE REFACTOR -> ENCAPSULATE FIELDS TO GENERATE GETTERS AND SETTERS
    private boolean profileCreated;
    private String idForFirebase;
    private String username;
    private String first_name;
    private String last_name;
    private String gender;
    private String availability;
    private String bio;
    private List<String> courses;
    private Map<String,Integer> interests;
    private List<String> tags;
    private List<Integer> met_history;
    private List<String> room_ids;




    public User() {
        this.setProfileCreated(false);
        this.setIdForFirebase("");
        this.setUsername("");
        this.setFirst_name("");
        this.setLast_name("");
        this.setGender("");
        this.setCourses(new ArrayList<String>());
        this.setInterests(new HashMap<String, Integer>());
        this.setTags(new ArrayList<String>());
        this.setMet_history(new ArrayList<Integer>());
        this.setRoom_ids(new ArrayList<String>());
        this.setAvailability("");
        this.setBio("");
    }




    public User(String username, String first_name, String last_name, String gender, List<String> courses,
                Map<String,Integer> interests, List<String> tags, List<Integer> met_history, List<String> room_ids,
                String bio) {
        this.setProfileCreated(true);
        this.setIdForFirebase("");
        this.setUsername(username);
        this.setFirst_name(first_name);
        this.setLast_name(last_name);
        this.setGender(gender);
        this.setCourses(courses);
        this.setInterests(interests);
        this.setTags(tags);
        this.setMet_history(met_history);
        this.setRoom_ids(room_ids);
        this.setAvailability("Available");
        this.setBio(bio);
    }




    public void printUserData() {
        System.out.println("Is account created: " + isProfileCreated());
        System.out.println("ID for Firebase: " + getIdForFirebase());
        System.out.println("Username: " + getUsername());
        System.out.println("First name: " + getFirst_name());
        System.out.println("Last name: " + getLast_name());
        System.out.println("Gender: " + getGender());
        System.out.println("Courses: ");
        for(String str : getCourses()) {
            System.out.println("\t" + str);
        }
        System.out.println("Interests: ");
        for (String key : getInterests().keySet()) {
            System.out.print("\tKey = " + key);
            System.out.println("\tValue = " + getInterests().get(key));
        }
        System.out.println("Tags: ");
        for(String str : getTags()) {
            System.out.println("\t" + str);
        }
        System.out.println("Met History: ");
        for(Integer ints : getMet_history()) {
            System.out.println("\t" + ints);
        }
        System.out.println("Room Ids: ** not yet implemented**");
    }




    public String userDataToPrint() {
        String allData = "";

        allData += "Is account created: " + isProfileCreated() + "\n";
        allData += "ID for Firebase: " + getIdForFirebase() + "\n";
        allData += "Username: " + getUsername() + "\n";
        allData += "First name: " + getFirst_name() + "\n";
        allData += "Last name: " + getLast_name() + "\n";
        allData += "Gender: " + getGender() + "\n";
        allData += "Courses: " + "\n";
        for(String str : getCourses()) {
            allData += "\t" + str + "\n";
        }
        allData += "Interests: " + "\n";
        for (String key : getInterests().keySet()) {
            allData += "\tKey = " + key;
            allData += "\tValue = " + getInterests().get(key) + "\n";
        }
        allData += "Tags: ";
        for(String str : getTags()) {
            allData += "\t" + str + "\n";
        }
        allData += "Met History: ";
        for(Integer ints : getMet_history()) {
            allData += "\t" + ints + "\n";
        }
        allData += "Bio: " + getBio() + "\n";
        allData += "Availability: " + getAvailability() + "\n";

        return allData;
    }




    public boolean isProfileCreated() {
        return profileCreated;
    }
    public void setProfileCreated(boolean profileCreated) {
        this.profileCreated = profileCreated;
    }


    public String getIdForFirebase() {
        return idForFirebase;
    }
    public void setIdForFirebase(String idForFirebase) {
        this.idForFirebase = idForFirebase;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }


    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }


    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }


    public List<String> getCourses() {
        return courses;
    }
    public void setCourses(List<String> courses) {
        this.courses = courses;
    }


    public Map<String, Integer> getInterests() {
        return interests;
    }
    public void setInterests(Map<String, Integer> interests) {
        this.interests = interests;
    }


    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public List<Integer> getMet_history() {
        return met_history;
    }
    public void setMet_history(List<Integer> met_history) {
        this.met_history = met_history;
    }


    public List<String> getRoom_ids() {
        return room_ids;
    }
    public void setRoom_ids(List<String> room_ids) {
        this.room_ids = room_ids;
    }

    public String getAvailability() {
        return availability;
    }
    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
}


