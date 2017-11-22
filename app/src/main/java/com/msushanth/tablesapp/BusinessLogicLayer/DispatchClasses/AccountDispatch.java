package com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses;

import com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses.AccountManager;

/**
 * Created by Sushanth on 11/10/17.
 */

public class AccountDispatch {
    public void logout(){
        AccountManager ma = new AccountManager();
        ma.logout();
    }

    public void passwordRecovery(String email){
        AccountManager ma = new AccountManager();
        ma.passwordRecovery(email);
    }
}
