package com.msushanth.tablesapp.PresentationLayer.FormClasses.Account;

import android.content.Intent;
import android.os.Bundle;
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
import com.msushanth.tablesapp.R;

public class CreateAccountForm extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;

    private FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // user is already logged in.
        //if(currentUser != null) {
            // TODO: go to the main screen (search tab)
        //}
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailET = (EditText) findViewById(R.id.emailEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);

        mAuth = FirebaseAuth.getInstance();
    }


    public void signIn(View v) {
        String print = "Sign In Clicked";
        Toast.makeText(this, print, Toast.LENGTH_SHORT).show();

        Intent mainActivityIntent = new Intent(CreateAccountForm.this, LogInForm.class);
        startActivity(mainActivityIntent);
        finish();
    }


    public void createAccountButtonClicked(View v) {

        // TODO: check if email is valid?
        // TODO: check if input is valid... no illegal characters in password etc..
        // TODO: check if email ends with ucsd.edu

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        // Checking that email has a @, as there needs to be at least two
        String[] emailParts = email.split("@");
        if(emailParts.length != 2){
            //the e-mail fails
            Toast.makeText(this, "email contains multiple @ signs", Toast.LENGTH_SHORT).show();

        }
        //check that the e-mail has the appropriate length
        if(emailParts.length == 2) {
            //getting the portion of the e-mail behind the @ sign
            Toast.makeText(this, "2nd part: " + emailParts[1], Toast.LENGTH_SHORT).show();
            String ucsdCheck = emailParts[1];
            //ucsd.edu check
            String ucsdEduCheck = "ucsd.edu";
            if(ucsdCheck.equals(ucsdEduCheck)==false) {
                Toast.makeText(this, "email does not contain ucsd.edu", Toast.LENGTH_SHORT).show();
            }

        }



        String print = "Creating Account with Email" + email + ", Passowrd: " + password;
        Toast.makeText(this, print, Toast.LENGTH_SHORT).show();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println("******User successfully created: " + user.getEmail());
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(this, "Dfsdf", Toast.LENGTH_SHORT).show();
                            System.out.println("******* Error with email");
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}
