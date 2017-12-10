package com.msushanth.tablesapp.ControllerLayer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.msushanth.tablesapp.DataAccessLayer.SearchDAO;
import com.msushanth.tablesapp.Objects.User;

import java.util.ArrayList;

/**
 * Created by Sushanth on 11/10/17.
 * Implemented by YinlongQian on 11/19/17
 */

public class SearchController{

    private final int MAX_TAGS = 4;
    private final int NUM_USERS_TO_SHOW = 10;

    FirebaseAuth firebaseAuth;
    DatabaseReference dbReference;
    FirebaseUser fireBaseUser;

    ArrayList<String> names;
    ArrayList<String> tags;
    private ArrayList<String> posIDs;
    private ArrayList<String> finalIDs;
    private ArrayList<ArrayList<String>> usersT;
    int numUsers;
    User user;
    User currentUserProfile;

    SearchDAO searchDAO = new SearchDAO();


    private String[] idList;
    private String[] lastNameList;
    private String[] firstNameList;
    private String[][] tagMatrix;

    //@Override
    public ArrayList<ArrayList<String>> searchEngine(){
        return searchDAO.searchEngine();
    }










    public ArrayList<ArrayList<String>> randomSearch () {

        return searchDAO.randomSearch();

    }



    //@Override
    public String[] getIdList(){
        return this.idList;
    }

    //@Override
    public String[] getLastNameList(){
        return this.lastNameList;
    }

    //@Override
    public String[] getFirstNameList(){
        return this.firstNameList;
    }

    //@Override
    public String[][] getTagMatrix(){
        return this.tagMatrix;
    }
}
