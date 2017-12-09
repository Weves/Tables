package com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses;

import android.content.Context;

import com.msushanth.tablesapp.DataAccessLayer.AccountDAO;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Account.LogInForm;

/**
 * Created by Sushanth on 11/10/17.
 */

public class AccountController {
    public boolean emailExist;
    AccountDAO da = new AccountDAO();
    private static LogInForm logInForm;

    public AccountController(){
        emailExist = false;
    }

    public void login(String email, String password, LogInForm currLogInForm) {
        logInForm = currLogInForm;
        da.login(email, password);
    }

    // succesful login with a new account
    public static void successfulLoginNew() {
        logInForm.successfulLoginNew();
    }

    // succesful login with an already created account
    public static void successfulLoginOld() {
        logInForm.successfulLoginOld();
    }

    // account unverified
    public static void loginUnverified() {
        logInForm.loginUnverified();
    }

    // incorrect username/pw
    public static void incorrectCredentials() {
        logInForm.incorrectCredentials();
    }

    public void logout(){
        da.logout();
    }

    public void passwordRecovery(String email, Context context){
        da.passwordRecovery(email, context);
    }
}
