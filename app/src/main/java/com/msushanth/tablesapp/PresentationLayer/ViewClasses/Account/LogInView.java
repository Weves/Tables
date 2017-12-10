package com.msushanth.tablesapp.PresentationLayer.ViewClasses.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.AccountController;
import com.msushanth.tablesapp.MainActivity;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Profile.CreatePersonalProfileView;
import com.msushanth.tablesapp.R;

/*
 */
public class LogInView extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;

    ProgressDialog progressDialog;

    AccountController accountController = new AccountController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        emailET = (EditText) findViewById(R.id.emailEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);

    }

    // handles user input of clicking on the sign in button
    public void signInButtonClicked(View v) {

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if(email.equals("") || password.equals("")) {
            Toast.makeText(LogInView.this, "Enter email and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog = ProgressDialog.show(LogInView.this, "Please wait...", "Processing", true);

        // try to login
        accountController.login(email, password, this);

    }


    // When the "sign up" is clicked, this method will be called
    public void createAccount(View v) {
        Intent createAccountIntent = new Intent(LogInView.this, CreateAccountView.class);
        startActivity(createAccountIntent);
        //finish();
    }

    // Use this method to handle resetting password
    // When the "forgot password" is clicked, this method will be called
    public void resetPassword(View view) {
        Intent resetPasswordIntent = new Intent(LogInView.this, PasswordRecoveryView.class);
        //Intent resetPasswordIntent = new Intent(LogInView.this, SetWhenAndWhereToMeetForm.class);
        startActivity(resetPasswordIntent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void resetPasswordButtonClicked(View view) {
        PasswordRecoveryView passwordRecoveryView = new PasswordRecoveryView();
        passwordRecoveryView.resetPassword();
    }

    // succesful login with a new account
    public void successfulLoginNew() {
        progressDialog.dismiss();
        Intent mainActivity = new Intent(LogInView.this, CreatePersonalProfileView.class);
        startActivity(mainActivity);
        finish();

    }

    // succesful login with an already created account
    public void successfulLoginOld() {
        progressDialog.dismiss();
        Intent mainActivity = new Intent(LogInView.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    // account unverified
    public void loginUnverified() {
        progressDialog.dismiss();
        Toast.makeText(LogInView.this, "Your email has not yet been verified. Check your email.", Toast.LENGTH_SHORT).show();
    }

    // incorrect username/pw
    public void incorrectCredentials() {
        progressDialog.dismiss();
        Toast.makeText(LogInView.this, "Sign In Failed.", Toast.LENGTH_SHORT).show();
    }

}
