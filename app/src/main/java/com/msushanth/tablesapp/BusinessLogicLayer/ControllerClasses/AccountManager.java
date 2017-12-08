package com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses;

import android.content.Context;

import com.msushanth.tablesapp.DataAccessLayer.AccountDAO;

/**
 * Created by Sushanth on 11/10/17.
 */

public class AccountManager {
    public boolean emailExist;

    public AccountManager(){
        emailExist = false;
    }

    public void logout(){
        AccountDAO da = new AccountDAO();
        da.logout();
    }

    public void passwordRecovery(String email, Context context){
        AccountDAO da = new AccountDAO();
        da.passwordRecovery(email, context);
    }
}
