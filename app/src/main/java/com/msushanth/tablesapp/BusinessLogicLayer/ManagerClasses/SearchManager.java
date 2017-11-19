package com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses;

/**
 * Created by Sushanth on 11/10/17.
 */

public class SearchManager {
    private String[] idList;
    private String[] lastNameList;
    private String[] firstNameList;
    private String[][] tagMatrix;

    public SearchManager(int input){
        this.idList = new String[input];
        this.lastNameList = new String[input];
        this.firstNameList = new String[input];
        this.tagMatrix = new String[input][5];
    }

    public void searchEngine(int num){

    }

    public String[] getIdList(){
        return this.idList;
    }

    public String[] getLastNameList(){
        return this.lastNameList;
    }

    public String[] getFirstNameList(){
        return this.firstNameList;
    }

    public String[][] getTagMatrix(){
        return this.tagMatrix;
    }
}
