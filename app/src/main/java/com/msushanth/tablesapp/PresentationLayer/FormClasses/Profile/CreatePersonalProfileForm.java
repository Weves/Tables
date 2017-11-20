package com.msushanth.tablesapp.PresentationLayer.FormClasses.Profile;

import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.msushanth.tablesapp.Course;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.Room;

import java.util.List;
import java.util.Map;

/**
 * Created by Sushanth on 11/9/17.
 */

public class CreatePersonalProfileForm extends AppCompatActivity {

    EditText usernameEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;

    Spinner spinner;
    ArrayAdapter<CharSequence> genderAdapter;

    MultiAutoCompleteTextView macTextView;
    ArrayAdapter<CharSequence> coursesAdapter;

    SeekBar sportsSeekBar;
    SeekBar musicSeekBar;
    SeekBar gamesSeekBar;
    SeekBar movieSeekBar;
    SeekBar technologySeekBar;
    SeekBar scienceSeekBar;
    SeekBar politicsSeekBar;
    SeekBar historySeekBar;
    SeekBar engineeringSeekBar;
    SeekBar economicsSeekBar;
    SeekBar literatureSeekBar;
    SeekBar comicsSeekBar;
    SeekBar religionSeekBar;
    SeekBar artsSeekBar;
    SeekBar travelSeekBar;
    TextView sportsTextView;
    TextView musicTextView;
    TextView gamesTextView;
    TextView movieTextView;
    TextView technologyTextView;
    TextView scienceTextView;
    TextView politicsTextView;
    TextView historyTextView;
    TextView engineeringTextView;
    TextView economicsTextView;
    TextView literatureTextView;
    TextView comicsTextView;
    TextView religionTextView;
    TextView artsTextView;
    TextView travelTextView;

    String username;
    String first_name;
    String last_name;
    String gender;
    List<Course> Courses;
    Map<String,Integer> Interests;
    List<String> Tags;
    List<Integer> met_history;
    List<Room> room_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_personal_profile_form);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);

        spinner = (Spinner) findViewById(R.id.genderSpinner);
        genderAdapter = ArrayAdapter.createFromResource(this, R.array.genders, R.layout.spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(genderAdapter);

        // User types in classes. As he types, a list of auto complete suggestions pop up.
        // TODO: remove all user inputs that are not part of the auto complete suggestions
        macTextView = (MultiAutoCompleteTextView) findViewById(R.id.coursesMultiAutoCompleteTextView);
        coursesAdapter = ArrayAdapter.createFromResource(this, R.array.courses, R.layout.multi_auto_complete_item);
        macTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        macTextView.setAdapter(coursesAdapter);


        // Initialize the SeekBars and their corresponding TextViews
        sportsSeekBar = (SeekBar) findViewById(R.id.sportsSeekBar);
        sportsSeekBar.setMax(10);
        sportsSeekBar.setProgress(0);
        sportsTextView = (TextView) findViewById(R.id.sportsInterestTextView);
        sportsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                usernameEditText.clearFocus();
                firstNameEditText.clearFocus();
                lastNameEditText.clearFocus();
                sportsTextView.setText("Sports (" + i + ")");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
