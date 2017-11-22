package com.msushanth.tablesapp.DataAccessLayer;

import android.support.annotation.NonNull;
import android.widget.Toast;
import java.lang.String;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.msushanth.tablesapp.MainActivity;

/**
 * Created by Sushanth on 11/10/17.
 */

public class AccountDAO {
    boolean resetSuccess;

    public AccountDAO(){
        resetSuccess = false;
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public boolean passwordRecovery(String email){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            resetSuccess = true;
                        }else{
                            resetSuccess = false;
                        }
                    }
                });
        return resetSuccess;
    }
}
