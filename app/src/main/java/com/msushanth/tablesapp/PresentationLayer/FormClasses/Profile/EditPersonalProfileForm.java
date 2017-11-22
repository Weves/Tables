package com.msushanth.tablesapp.PresentationLayer.FormClasses.Profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import com.msushanth.tablesapp.Interfaces.Profile.EditPersonalProfileInterface;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.User;

/**
 * Created by Sushanth on 11/9/17.
 */

public class EditPersonalProfileForm extends AppCompatActivity implements EditPersonalProfileInterface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_personal_profile_form); // TODO: change this
    }

    @Override
    public void editProfile(User user) {

    }
}
