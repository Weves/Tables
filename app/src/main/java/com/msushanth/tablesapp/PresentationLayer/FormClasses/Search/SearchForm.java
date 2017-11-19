package com.msushanth.tablesapp.PresentationLayer.FormClasses.Search;

import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Search.SearchAction;

/**
 * Created by Sushanth on 11/9/17.
 */

public class SearchForm {
    private String[] idList;
    private String[] lastNameList;
    private String[] firstNameList;
    private String[][] tagMatrix;

    public SearchForm(int inputNum){
        this.idList = new String[inputNum];
        this.lastNameList = new String[inputNum];
        this.firstNameList = new String[inputNum];
        this.tagMatrix = new String[inputNum][5];
    }

    public void searchEngine(int num){
        SearchAction nextLayer = new SearchAction(num);
        nextLayer.searchEngine(num);
        this.idList = nextLayer.getIdList();
        this.lastNameList = nextLayer.getLastNameList();
        this.firstNameList = nextLayer.getFirstNameList();
        this.tagMatrix = nextLayer.getTagMatrix();
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
