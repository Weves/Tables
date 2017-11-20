package com.msushanth.tablesapp;

import java.util.List;
import java.util.Map;

/**
 * Created by allenqian on 11/19/17.
 */


public class User {
    String username;
    String first_name;
    String last_name;
    String gender;
    List<Course> Courses;
    Map<String,Integer> Interests;
    List<String> Tags;
    List<Integer> met_history;
    List<Room> room_ids;

    public User(String username, String first_name, String last_name, String gender, List<Course> Courses,
                Map<String,Integer> Interests, List<String> Tags, List<Integer> met_hsitory, List<Room> room_ids){
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.Courses = Courses;
        this.Interests = Interests;
        this.Tags = Tags;
        this.met_history = met_hsitory;
        this.room_ids = room_ids;
    }
}


