package com.msushanth.tablesapp.BusinessLogicLayer.ManagerClasses;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

    public boolean passwordRecovery(String email){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Task exist =  auth.fetchProvidersForEmail(email);
        if (exist.isSuccessful()){
            AccountDAO da = new AccountDAO();
            return da.passwordRecovery(email);
        }
        else{
            return false;
        }
    }
}
