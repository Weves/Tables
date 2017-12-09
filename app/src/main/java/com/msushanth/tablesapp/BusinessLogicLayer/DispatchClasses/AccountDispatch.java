package com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses;

import android.content.Context;

import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.AccountController;

/**
 * Created by Sushanth on 11/10/17.
 */

public class AccountDispatch {
    public void logout(){
        AccountController ma = new AccountController();
        ma.logout();
    }

    public void passwordRecovery(String email, Context context){
        AccountController ma = new AccountController();
        ma.passwordRecovery(email, context);
    }
}
