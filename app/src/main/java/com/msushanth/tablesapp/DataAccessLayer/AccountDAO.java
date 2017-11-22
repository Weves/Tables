package com.msushanth.tablesapp.DataAccessLayer;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Sushanth on 11/10/17.
 */

public class AccountDAO {
    public void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public void passwordRecovery(String email){
        //TODO
    }
}
