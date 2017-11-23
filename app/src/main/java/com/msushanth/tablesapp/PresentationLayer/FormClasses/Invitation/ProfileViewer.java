package com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.msushanth.tablesapp.R;

public class ProfileViewer extends AppCompatActivity {

    TextView usersName;
    TextView usersEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_viewer);

        usersName = (TextView) findViewById(R.id.UsersName);
        usersEmail = (TextView) findViewById(R.id.UsersEmail);

        usersName.setText("Jon John");
        usersEmail.setText("JonJohn@ucsd.edu");
    }


    public void backButtonClicked(View v) {
        finish();
    }

}
