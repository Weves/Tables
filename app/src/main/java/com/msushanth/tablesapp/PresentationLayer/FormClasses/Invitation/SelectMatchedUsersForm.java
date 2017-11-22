package com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.msushanth.tablesapp.Interfaces.Invitation.SelectMatchedUsersInterface;
import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Invitation.SelectMatchedUserAction;
import com.msushanth.tablesapp.R;

/**
 * Created by Sushanth on 11/9/17.
 */

public class SelectMatchedUsersForm extends AppCompatActivity implements SelectMatchedUsersInterface {

    SelectMatchedUserAction action = new SelectMatchedUserAction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_users);
    }

    public void viewProfileButtonClicked(View v) {
        viewProfile();
    }

    public void viewProfile() {
        action.viewProfile();
        Intent selectMatchedUsersIntent = new Intent(SelectMatchedUsersForm.this, ProfileViewer.class);
        startActivity(selectMatchedUsersIntent);

    }

}
