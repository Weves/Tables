package com.msushanth.tablesapp.ControllerLayer;

import android.content.Context;

import com.msushanth.tablesapp.DataAccessLayer.AccountDAO;
import com.msushanth.tablesapp.ViewLayer.Account.CreateAccountView;
import com.msushanth.tablesapp.ViewLayer.Account.LogInView;

/**
 * Created by Sushanth on 11/10/17.
 */

public class AccountController {
    public boolean emailExist;
    AccountDAO da = new AccountDAO();
    private static LogInView logInView;
    private static CreateAccountView createAccountView;

    public AccountController(){
        emailExist = false;
    }

    public void login(String email, String password, LogInView currLogInView) {
        logInView = currLogInView;
        da.login(email, password);
    }

    // succesful login with a new account
    public static void successfulLoginNew() {
        logInView.successfulLoginNew();
    }

    // succesful login with an already created account
    public static void successfulLoginOld() {
        logInView.successfulLoginOld();
    }

    // account unverified
    public static void loginUnverified() {
        logInView.loginUnverified();
    }

    // incorrect username/pw
    public static void incorrectCredentials() {
        logInView.incorrectCredentials();
    }

    //logout delegation
    public void logout(){
        da.logout();
    }

    // password recovery delegation
    public void passwordRecovery(String email, Context context){
        da.passwordRecovery(email, context);
    }

    // account creation delegation
    public void createAccount(String email, String password, CreateAccountView currCreateAccountView) {
        createAccountView = currCreateAccountView;
        da.createAccount(email, password);
    }

    // tell the user that the registration was unsuccessful
    public static void createAccountSuccess() {
        createAccountView.succesfulReg();
    }

    // tell the user that the registration was unsuccessful
    public static void createAccountFailed() {
        createAccountView.unsuccesfulReg();
    }
}
