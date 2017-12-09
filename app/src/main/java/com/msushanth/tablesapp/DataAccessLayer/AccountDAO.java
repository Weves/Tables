package com.msushanth.tablesapp.DataAccessLayer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.Toast;
import java.lang.String;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.AccountController;
import com.msushanth.tablesapp.MainActivity;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Account.LogInForm;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Profile.CreatePersonalProfileView;
import com.msushanth.tablesapp.User;

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

    public void login(String email, String password) {
        (CurrentUserInfo.firebaseAuth.signInWithEmailAndPassword(email, password))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        // If login successful and email is verified, go to the search tab on the main page.
                        if(task.isSuccessful()) {
                            CurrentUserInfo.firebaseAuth.getCurrentUser().reload();
                            //fireBaseUser = CurrentUserInfo.firebaseAuth.getCurrentUser();
                            //Toast.makeText(LogInForm.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                            // Check if email has been verified
                            if(CurrentUserInfo.fireBaseUser.isEmailVerified()) {

                                // Check if the user has created a profile.
                                // If he hasn't, take him to the CreateAccountForm
                                // If he has, take him to the main activity
                                CurrentUserInfo.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        boolean accountStatus = checkIfUserCreatedProfile(dataSnapshot);
                                        if(accountStatus) {
                                            AccountController.successfulLoginOld();
                                        } else {
                                            AccountController.successfulLoginOld();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {}
                                });

                            } else {
                                AccountController.loginUnverified();
                            }
                        } else {
                            AccountController.incorrectCredentials();
                        }
                    }
                });
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


    // Get data from the database to check if the current logged in user has created a profile.
    public boolean checkIfUserCreatedProfile(DataSnapshot dataSnapshot) {
        return dataSnapshot.child(CurrentUserInfo.fireBaseUser.getUid()).getValue(User.class).isProfileCreated();
    }

}
