package com.msushanth.tablesapp.PresentationLayer.ActionClasses.Account;

import android.accounts.Account;

import com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses.AccountDispatch;

/**
 * Created by Sushanth on 11/9/17.
 */

public class LogOutAction {
    public void logout(){
        AccountDispatch dis = new AccountDispatch();
        dis.logout();
    }
}
