package com.msushanth.tablesapp.ViewLayer.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.msushanth.tablesapp.ControllerLayer.AccountController;
import com.msushanth.tablesapp.R;

public class CreateAccountView extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser fbUser;

    ProgressDialog progressDialog;

    AccountController accountController = new AccountController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailET = (EditText) findViewById(R.id.emailEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void signIn(View v) {
        Intent mainActivityIntent = new Intent(CreateAccountView.this, LogInView.class);
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

        // Create firebase account
        accountController.createAccount(email, password, this);


        progressDialog = ProgressDialog.show(CreateAccountView.this, "Please wait...", "Processing", true);

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


    // in the case of a successful registration
    public void succesfulReg() {
        progressDialog.dismiss();
        Toast.makeText(CreateAccountView.this, "Registration Successful. Check your email.", Toast.LENGTH_SHORT).show();

        // Wait until the toast is done displaying before going back to the log in screen
        (new Handler()).postDelayed(
                new Runnable() {
                    public void run() {
                        Intent i = new Intent(CreateAccountView.this, LogInView.class);
                        startActivity(i);
                        finish();
                    }
                }, 2500);
    }

    // tell the user registration was unsuccessful
    public void unsuccesfulReg() {
        progressDialog.dismiss();
        Toast.makeText(CreateAccountView.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
    }


}
