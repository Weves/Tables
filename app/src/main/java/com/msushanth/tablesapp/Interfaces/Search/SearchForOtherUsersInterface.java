package com.msushanth.tablesapp.Interfaces.Search;

/**
 * Created by Sushanth on 11/19/17.
 * Implemented by YinlongQian on 11/21/17
 */

public interface SearchForOtherUsersInterface {
    public void searchEngine(int num);

    public String[] getIdList();

    public String[] getLastNameList();

    public String[] getFirstNameList();

    public String[][] getTagMatrix();
}
