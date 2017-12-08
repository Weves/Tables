package com.msushanth.tablesapp.PresentationLayer.ViewClasses.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.MainActivity;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Profile.CreatePersonalProfileForm;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.User;

/*
 */
public class LogInForm extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbReference;
    FirebaseUser fireBaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        emailET = (EditText) findViewById(R.id.emailEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);
        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();

        // *** IGNORE *** If user is auto-logged in. If user is already logged in.
        /*if(firebaseAuth.getCurrentUser() != null) {
            Intent createAccountIntent = new Intent(LogInForm.this, CreateAccountForm.class);
            startActivity(createAccountIntent);
            finish();
        }*/
    }

    public void signInButtonClicked(View v) {

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if(email.equals("") || password.equals("")) {
            Toast.makeText(LogInForm.this, "Enter email and password.", Toast.LENGTH_SHORT).show();
            return;
        }


        final ProgressDialog progressDialog = ProgressDialog.show(LogInForm.this, "Please wait...", "Processing", true);

        (firebaseAuth.signInWithEmailAndPassword(email, password))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                // If login successful and email is verified, go to the search tab on the main page.
                if(task.isSuccessful()) {
                    firebaseAuth.getCurrentUser().reload();
                    fireBaseUser = firebaseAuth.getCurrentUser();
                    //Toast.makeText(LogInForm.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                    // Check if email has been verified
                    if(fireBaseUser.isEmailVerified()) {

                        // Check if the user has created a profile.
                        // If he hasn't, take him to the CreateAccountForm
                        // If he has, take him to the main activity
                        Toast.makeText(LogInForm.this, "Sign In Successful", Toast.LENGTH_SHORT).show();

                        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                boolean accountStatus = checkIfUserCreatedProfile(dataSnapshot);
                                if(accountStatus) {
                                    Intent mainActivity = new Intent(LogInForm.this, MainActivity.class);
                                    startActivity(mainActivity);
                                    finish();
                                } else {
                                    Intent mainActivity = new Intent(LogInForm.this, CreatePersonalProfileForm.class);
                                    startActivity(mainActivity);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });

                    } else {
                        Toast.makeText(LogInForm.this, "Your email has not yet been verified. Check your email.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogInForm.this, "Sign In Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    // Get data from the database to check if the current logged in user has created a profile.
    public boolean checkIfUserCreatedProfile(DataSnapshot dataSnapshot) {
        return dataSnapshot.child(fireBaseUser.getUid()).getValue(User.class).isProfileCreated();
    }


    // When the "sign up" is clicked, this method will be called
    public void createAccount(View v) {
        Intent createAccountIntent = new Intent(LogInForm.this, CreateAccountForm.class);
        startActivity(createAccountIntent);
        //finish();
    }

    // Use this method to handle resetting password
    // When the "forgot password" is clicked, this method will be called
    public void resetPassword(View view) {
        Intent resetPasswordIntent = new Intent(LogInForm.this, PasswordRecoveryForm.class);
        //Intent resetPasswordIntent = new Intent(LogInForm.this, SetWhenAndWhereToMeetForm.class);
        startActivity(resetPasswordIntent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void resetPasswordButtonClicked(View view) {
        PasswordRecoveryForm passwordRecoveryForm = new PasswordRecoveryForm();
        passwordRecoveryForm.resetPassword();
    }
}
