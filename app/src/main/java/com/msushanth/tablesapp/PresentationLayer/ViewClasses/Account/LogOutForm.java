package com.msushanth.tablesapp.PresentationLayer.ViewClasses.Account;

import android.support.v7.app.AppCompatActivity;

import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Account.LogOutAction;

/**
 * Created by Sushanth on 11/9/17.
 */

public class LogOutForm  extends AppCompatActivity {
    public void logout(){
        LogOutAction act = new LogOutAction();
        act.logout();
    }
}
