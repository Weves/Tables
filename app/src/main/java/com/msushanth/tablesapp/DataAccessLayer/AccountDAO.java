package com.msushanth.tablesapp.DataAccessLayer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.Toast;
import java.lang.String;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.msushanth.tablesapp.MainActivity;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Account.CreateAccountForm;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Account.LogInForm;

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

    public void passwordRecovery(String email, final Context context){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Password reset email has been sent.", Toast.LENGTH_SHORT).show();
                            // Wait until the toast is done displaying before going back to the log in screen
                            (new Handler()).postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            Intent i = new Intent(context, LogInForm.class);
                                            context.startActivity(i);
                                        }
                                    }, 2500);
                        } else {
                            Toast.makeText(context, "Email failed to send, please check the email address.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
