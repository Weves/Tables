package com.msushanth.tablesapp.PresentationLayer.FormClasses.Account;

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
import com.msushanth.tablesapp.MainActivity;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation.SelectMatchedUsersForm;
import com.msushanth.tablesapp.R;

/*
 */
public class LogInForm extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        emailET = (EditText) findViewById(R.id.emailEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);
        firebaseAuth = FirebaseAuth.getInstance();
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

                // If login successful, go to the search tab on the main page.
                if(task.isSuccessful()) {
                    firebaseAuth.getCurrentUser().reload();
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    //Toast.makeText(LogInForm.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                    if(user.isEmailVerified()) {
                        Intent mainActivity = new Intent(LogInForm.this, MainActivity.class);
                        startActivity(mainActivity);
                        finish();
                    } else {
                        Toast.makeText(LogInForm.this, "Your email has not yet been verified. Check your email.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogInForm.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void createAccount(View v) {
        Intent createAccountIntent = new Intent(LogInForm.this, CreateAccountForm.class);
        startActivity(createAccountIntent);
        finish();
    }

    // use this method to handle resetting password
    // when the user click forgot password, this method will be called
    public void resetPassword(View view) {
        Intent resetPasswordIntent = new Intent(LogInForm.this, PasswordRecoveryForm.class);
        startActivity(resetPasswordIntent);
    }
}
