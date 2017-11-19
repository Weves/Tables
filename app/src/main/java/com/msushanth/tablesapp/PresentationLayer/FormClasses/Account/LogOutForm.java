package com.msushanth.tablesapp.PresentationLayer.FormClasses.Account;

import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Account.LogOutAction;

/**
 * Created by Sushanth on 11/9/17.
 */

public class LogOutForm {
    public void logout(){
        LogOutAction act = new LogOutAction();
        act.logout();
    }
}
