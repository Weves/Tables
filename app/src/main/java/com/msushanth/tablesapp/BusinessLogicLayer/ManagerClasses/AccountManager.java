package com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses;

import com.msushanth.tablesapp.DataAccessLayer.AccountDAO;

/**
 * Created by Sushanth on 11/10/17.
 */

public class AccountManager {
    public void logout(){
        AccountDAO da = new AccountDAO();
        da.logout();
    }

    public void passwordRecovery(String email){
        AccountDAO da = new AccountDAO();
        da.passwordRecovery(email);
    }
}
