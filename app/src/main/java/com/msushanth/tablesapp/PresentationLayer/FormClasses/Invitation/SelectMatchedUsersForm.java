package com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.msushanth.tablesapp.Interfaces.Invitation.SelectMatchedUsersInterface;
import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Invitation.SelectMatchedUserAction;
import com.msushanth.tablesapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sushanth on 11/9/17.
 */

public class SelectMatchedUsersForm extends AppCompatActivity implements SelectMatchedUsersInterface {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListUser> users = new ArrayList<ListUser>();

    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> tags = new ArrayList<String>();
    ArrayList<String> IDs = new ArrayList<String>();

    SelectMatchedUserAction action = new SelectMatchedUserAction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_users);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        names = intent.getStringArrayListExtra("matchedUsersNames");
        tags = intent.getStringArrayListExtra("matchedUsersTags");
        IDs = intent.getStringArrayListExtra("matchedUsersIDs");

        for (int i=0; i <names.size(); i++) {
            ListUser user = new ListUser(names.get(i), tags.get(i), IDs.get(i));

            users.add(user);
        }

        adapter = new ListAdapter(users, this);
        recyclerView.setAdapter(adapter);
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
