package com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses;

import android.content.Context;

import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.AccountManager;

/**
 * Created by Sushanth on 11/10/17.
 */

public class AccountDispatch {
    public void logout(){
        AccountManager ma = new AccountManager();
        ma.logout();
    }

    public void passwordRecovery(String email, Context context){
        AccountManager ma = new AccountManager();
        ma.passwordRecovery(email, context);
    }
}
