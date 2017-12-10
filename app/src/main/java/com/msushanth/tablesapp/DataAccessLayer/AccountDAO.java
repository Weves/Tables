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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.ControllerLayer.AccountController;
import com.msushanth.tablesapp.ViewLayer.Account.LogInView;
import com.msushanth.tablesapp.Objects.User;

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
                            FirebaseUser fireBaseUser = CurrentUserInfo.firebaseAuth.getCurrentUser();
                            CurrentUserInfo.setUser();
                            //Toast.makeText(LogInView.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                            // Check if email has been verified
                            if(fireBaseUser.isEmailVerified()) {

                                // Check if the user has created a profile.
                                // If he hasn't, take him to the CreateAccountView
                                // If he has, take him to the main activity
                                CurrentUserInfo.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        boolean accountStatus = checkIfUserCreatedProfile(dataSnapshot);
                                        if(accountStatus) {
                                            AccountController.successfulLoginOld();
                                        } else {
                                            AccountController.successfulLoginNew();
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
                                            Intent i = new Intent(context, LogInView.class);
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

    // create an account on the database (try to at least)
    public void createAccount(String email, String password) {
        (CurrentUserInfo.firebaseAuth.createUserWithEmailAndPassword(email, password))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // if registration successful, send email verification and go to login screen
                        if(task.isSuccessful()) {
                            CurrentUserInfo.firebaseAuth.getCurrentUser().reload();
                            final FirebaseUser fbUser = CurrentUserInfo.firebaseAuth.getCurrentUser();
                            if(fbUser != null) {
                                fbUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {

                                            // Create a new user. Initialize User fields in database to empty by calling the empty User constructor and sending it to firebase
                                            CurrentUserInfo.databaseReference = FirebaseDatabase.getInstance().getReference();
                                            User newUser = new User();
                                            newUser.setIdForFirebase(fbUser.getUid());
                                            CurrentUserInfo.databaseReference.child(newUser.getIdForFirebase()).setValue(newUser);

                                            AccountController.createAccountSuccess();
                                        } else {
                                            AccountController.createAccountFailed();
                                        }
                                    }
                                });
                            }

                        } else {
                            AccountController.createAccountFailed();
                        }
                    }
                });
    }

}
