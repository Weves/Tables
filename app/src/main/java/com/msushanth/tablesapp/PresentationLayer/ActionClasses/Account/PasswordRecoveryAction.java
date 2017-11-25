package com.msushanth.tablesapp.PresentationLayer.ActionClasses.Account;

import android.content.Context;

import com.msushanth.tablesapp.BusinessLogicLayer.DispatchClasses.AccountDispatch;

/**
 * Created by Sushanth on 11/9/17.
 */

public class PasswordRecoveryAction {
    public void passwordRecovery(String email, Context context){
        AccountDispatch dis = new AccountDispatch();
        dis.passwordRecovery(email, context);
    }
}
