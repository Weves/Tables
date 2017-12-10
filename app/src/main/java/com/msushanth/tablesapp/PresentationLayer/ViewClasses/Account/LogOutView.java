package com.msushanth.tablesapp.PresentationLayer.ViewClasses.Account;

import android.support.v7.app.AppCompatActivity;

import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.AccountController;

/**
 * Created by Sushanth on 11/9/17.
 */

public class LogOutView extends AppCompatActivity {
    public void logout(){
        AccountController act = new AccountController();
        act.logout();
    }
}
