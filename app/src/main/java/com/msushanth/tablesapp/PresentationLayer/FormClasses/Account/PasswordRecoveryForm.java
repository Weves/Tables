package com.msushanth.tablesapp.PresentationLayer.FormClasses.Account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Account.PasswordRecoveryAction;
import com.msushanth.tablesapp.R;

/**
 * Created by Sushanth on 11/9/17.
 */

public class PasswordRecoveryForm extends AppCompatActivity {

    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_layout);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
    }

    public boolean passwordRecovery(String email){
        PasswordRecoveryAction act = new PasswordRecoveryAction();
        return act.passwordRecovery(email);
    }

    public void resetPasswordButtonClicked(View view) {
        String email = emailEditText.getText().toString();

        // TODO check if email is not empty.. other checks...
        if (email == null || email.isEmpty()){
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(this, "Email entered: " + email, Toast.LENGTH_SHORT).show();

        if(passwordRecovery(email)){
            Toast.makeText(this, "Password reset email has been sent to your account", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Password reset failed, please check the email address", Toast.LENGTH_SHORT).show();
        }
    }
}
