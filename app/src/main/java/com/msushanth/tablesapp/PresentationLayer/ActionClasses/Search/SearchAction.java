package com.msushanth.tablesapp.PresentationLayer.ActionClasses.Search;

import com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses.SearchDispatch;
import com.msushanth.tablesapp.Interfaces.Search.SearchInterface;

/**
 * Created by Sushanth on 11/9/17.
 * Implemented by YinlongQian on 11/19/17
 */

public class SearchAction implements SearchInterface{
    private String[] idList;
    private String[] lastNameList;
    private String[] firstNameList;
    private String[][] tagMatrix;

    public SearchAction(int inputNum){
        this.idList = new String[inputNum];
        this.lastNameList = new String[inputNum];
        this.firstNameList = new String[inputNum];
        this.tagMatrix = new String[inputNum][5];
    }

    @Override
    public void searchEngine(int num){
        SearchDispatch nextLayer = new SearchDispatch(num);
        nextLayer.searchEngine(num);
        this.idList = nextLayer.getIdList();
        this.lastNameList = nextLayer.getLastNameList();
        this.firstNameList = nextLayer.getFirstNameList();
        this.tagMatrix = nextLayer.getTagMatrix();
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
