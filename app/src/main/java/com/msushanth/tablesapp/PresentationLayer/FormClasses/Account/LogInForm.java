package com.msushanth.tablesapp.PresentationLayer.FormClasses.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.msushanth.tablesapp.MainActivity;
import com.msushanth.tablesapp.R;

/*
 */
public class LogInForm extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        usernameET = (EditText) findViewById(R.id.usernameEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);
    }

    public void signInButtonClicked(View v) {

        String print = "Signing on with Username: " + usernameET.getText().toString() + ", Passowrd: " + passwordET.getText().toString();
        Toast.makeText(this, print, Toast.LENGTH_SHORT).show();

        Intent mainActivity = new Intent(LogInForm.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }


    public void createAccount(View v) {

        String print = "Create Account Clicked";
        Toast.makeText(this, print, Toast.LENGTH_SHORT).show();

        Intent createAccountIntent = new Intent(LogInForm.this, CreateAccountForm.class);
        //this is a SelectMathcedUsersForm test
        //Intent createAccountIntent = new Intent(LogInForm.this, SelectMatchedUsersForm.class);
        startActivity(createAccountIntent);
        finish();
    }

}
