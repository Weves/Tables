package com.msushanth.tablesapp.PresentationLayer.FormClasses.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.User;

public class CreateAccountForm extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser fbUser;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailET = (EditText) findViewById(R.id.emailEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void signIn(View v) {
        Intent mainActivityIntent = new Intent(CreateAccountForm.this, LogInForm.class);
        startActivity(mainActivityIntent);
        finish();
    }


    public void createAccountButtonClicked(View v) {

        // Checking if email is valid and ends with @ucsd.edu
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        // Checking that email has a @, as there needs to be at least two
        String[] emailParts = email.split("@");
        if(emailParts.length != 2){
            //the e-mail fails
            Toast.makeText(this, "Invalid email entered.", Toast.LENGTH_SHORT).show();
            return;
        }
        //check that the e-mail has the appropriate length
        if(emailParts.length == 2) {
            //getting the portion of the e-mail behind the @ sign
            String ucsdCheck = emailParts[1];
            //ucsd.edu check
            String ucsdEduCheck = "ucsd.edu";
            if(ucsdCheck.equals(ucsdEduCheck)==false) {
                Toast.makeText(this, "Enter a valid UCSD email.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Check for illegal characters in password
        if(!containsLettersOrDigits(password)) {
            Toast.makeText(this, "Illegal characters in password.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if password length is above 8 and under 14
        if( !(password.length()<=14 && password.length()>=8) ) {
            Toast.makeText(this, "Error: Enter a password 8-14 characters long.", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Do this is the DAO layer
        // Create firebase account
        progressDialog = ProgressDialog.show(CreateAccountForm.this, "Please wait...", "Processing", true);
        (firebaseAuth.createUserWithEmailAndPassword(email, password))
                 .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // if registration successful, send email verification and go to login screen
                if(task.isSuccessful()) {
                    firebaseAuth.getCurrentUser().reload();
                    fbUser = firebaseAuth.getCurrentUser();
                    if(fbUser != null) {
                        fbUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(CreateAccountForm.this, "Registration Successful. Check your email.", Toast.LENGTH_SHORT).show();

                                    // Create a new user. Initialize User fields in database to empty by calling the empty User constructor and sending it to firebase
                                    databaseReference = FirebaseDatabase.getInstance().getReference();
                                    User newUser = new User();
                                    newUser.setIdForFirebase(fbUser.getUid());
                                    databaseReference.child(newUser.getIdForFirebase()).setValue(newUser);


                                    // Wait until the toast is done displaying before going back to the log in screen
                                    (new Handler()).postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    Intent i = new Intent(CreateAccountForm.this, LogInForm.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            }, 2500);
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(CreateAccountForm.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(CreateAccountForm.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public boolean containsLettersOrDigits(String str) {
        char[] charArray = str.toCharArray();
        for(char c : charArray) {
            int charVal = (int) c;
            if( !(((charVal>=65) && (charVal<=90)) || ((charVal>=97) && (charVal<=122)) || ((charVal>=48) && (charVal<=57))) ) {
                return false;
            }
        }
        return true;
    }
}
