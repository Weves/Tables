package com.msushanth.tablesapp.PresentationLayer.FormClasses.Account;

import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Account.PasswordRecoveryAction;

/**
 * Created by Sushanth on 11/9/17.
 */

public class PasswordRecoveryForm {
    public void passwordRecovery(String email){
        PasswordRecoveryAction act = new PasswordRecoveryAction();
        act.passwordRecovery(email);
    }
}
