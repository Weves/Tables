package com.msushanth.tablesapp.PresentationLayer.FormClasses.Account;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Account.LogOutAction;

/**
 * Created by Sushanth on 11/9/17.
 */

public class LogOutForm  extends AppCompatActivity {
    public void logout(){
        LogOutAction act = new LogOutAction();
        act.logout();
        Toast.makeText(this,"Account logout!", Toast.LENGTH_SHORT).show();
    }
}
