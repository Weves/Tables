package com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses;

import com.msushanth.tablesapp.Interfaces.Search.SearchInterface;

/**
 * Created by Sushanth on 11/10/17.
 * Implemented by YinlongQian on 11/19/17
 */

public class SearchManager implements SearchInterface{
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

    @Override
    public void searchEngine(int num){

    }

    @Override
    public String[] getIdList(){
        return this.idList;
    }

    @Override
    public String[] getLastNameList(){
        return this.lastNameList;
    }

    @Override
    public String[] getFirstNameList(){
        return this.firstNameList;
    }

    @Override
    public String[][] getTagMatrix(){
        return this.tagMatrix;
    }
}
