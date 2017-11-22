package com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.msushanth.tablesapp.R;

public class ProfileViewer extends AppCompatActivity {

    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_viewer);
        userName = (TextView) findViewById(R.id.UserName);
        userName.setText("Jon");
    }


    public void backButtonClicked(View v) {
        finish();
    }

}
