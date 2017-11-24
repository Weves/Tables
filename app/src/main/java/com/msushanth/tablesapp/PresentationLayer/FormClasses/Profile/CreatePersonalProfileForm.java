package com.msushanth.tablesapp.PresentationLayer.FormClasses.Profile;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.msushanth.tablesapp.Interfaces.Profile.PersonalProfileInterface;
import com.msushanth.tablesapp.MainActivity;
import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Profile.CreatePersonalProfileAction;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.Room;
import com.msushanth.tablesapp.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sushanth on 11/9/17.
 */

public class CreatePersonalProfileForm extends AppCompatActivity implements PersonalProfileInterface {

    EditText usernameEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText tagsEditText;

    Spinner genderSpinner;
    ArrayAdapter<CharSequence> genderAdapter;

    MultiAutoCompleteTextView macTextView;
    ArrayAdapter<CharSequence> coursesAdapter;

    SeekBar sportsSeekBar;
    SeekBar musicSeekBar;
    SeekBar gamesSeekBar;
    SeekBar moviesSeekBar;
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
    TextView moviesTextView;
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

    String[] interestsArray;

    User user;
    String username;
    String first_name;
    String last_name;
    String gender;
    List<String> courses;
    Map<String,Integer> interestsMap;
    List<String> tags;
    List<Integer> met_history;
    List<Room> room_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_personal_profile_form);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);

        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        genderAdapter = ArrayAdapter.createFromResource(this, R.array.genders, R.layout.spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        // User types in classes. As he types, a list of auto complete suggestions pop up.
        macTextView = (MultiAutoCompleteTextView) findViewById(R.id.coursesMultiAutoCompleteTextView);
        coursesAdapter = ArrayAdapter.createFromResource(this, R.array.courses, R.layout.multi_auto_complete_item);
        macTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        macTextView.setAdapter(coursesAdapter);
        macTextView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        macTextView.setRawInputType(InputType.TYPE_CLASS_TEXT);

        tagsEditText = (EditText) findViewById(R.id.tagsEditText);
        tagsEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        tagsEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);

        // Initialize the interests Map so we can add the seek bar data to it
        initializeInterestsMap();

        // Initialize the SeekBars and their corresponding TextViews
        initializeSeekBars();
    }


    @Override
    public void setProfile(User user) {
        CreatePersonalProfileAction createProfileAction = new CreatePersonalProfileAction();
        createProfileAction.setProfile(user);
    }


    private void initializeInterestsMap() {
        Resources res = getResources();
        interestsArray = res.getStringArray(R.array.interests);
        interestsMap = new HashMap<String, Integer>();
        for(String s : interestsArray) {
            interestsMap.put(s, 0);
        }

        //iterating over keys  and values
        /*for (String key : interestsMap.keySet()) {
            System.out.println("******Key = " + key);
            System.out.println("******Value = " + interestsMap.get(key));
        }*/
    }


    private void initializeSeekBars() {
        sportsSeekBar = (SeekBar) findViewById(R.id.sportsSeekBar);
        sportsSeekBar.setMax(10);
        sportsSeekBar.setProgress(0);
        sportsTextView = (TextView) findViewById(R.id.sportsInterestTextView);
        sportsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sportsTextView.requestFocus();
                sportsTextView.setText("Sports (" + i + ")");
                interestsMap.put(interestsArray[0], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        musicSeekBar = (SeekBar) findViewById(R.id.musicSeekBar);
        musicSeekBar.setMax(10);
        musicSeekBar.setProgress(0);
        musicTextView = (TextView) findViewById(R.id.musicInterestTextView);
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                musicTextView.requestFocus();
                musicTextView.setText("Music (" + i + ")");
                interestsMap.put(interestsArray[1], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        gamesSeekBar = (SeekBar) findViewById(R.id.gamesSeekBar);
        gamesSeekBar.setMax(10);
        gamesSeekBar.setProgress(0);
        gamesTextView = (TextView) findViewById(R.id.gamesInterestTextView);
        gamesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                gamesTextView.requestFocus();
                gamesTextView.setText("Games (" + i + ")");
                interestsMap.put(interestsArray[2], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        moviesSeekBar = (SeekBar) findViewById(R.id.moviesSeekBar);
        moviesSeekBar.setMax(10);
        moviesSeekBar.setProgress(0);
        moviesTextView = (TextView) findViewById(R.id.moviesInterestTextView);
        moviesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                moviesTextView.requestFocus();
                moviesTextView.setText("Movies (" + i + ")");
                interestsMap.put(interestsArray[3], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        technologySeekBar = (SeekBar) findViewById(R.id.technologySeekBar);
        technologySeekBar.setMax(10);
        technologySeekBar.setProgress(0);
        technologyTextView = (TextView) findViewById(R.id.technologyInterestTextView);
        technologySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                technologyTextView.requestFocus();
                technologyTextView.setText("Technology (" + i + ")");
                interestsMap.put(interestsArray[4], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        scienceSeekBar = (SeekBar) findViewById(R.id.scienceSeekBar);
        scienceSeekBar.setMax(10);
        scienceSeekBar.setProgress(0);
        scienceTextView = (TextView) findViewById(R.id.scienceInterestTextView);
        scienceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                scienceTextView.requestFocus();
                scienceTextView.setText("Science (" + i + ")");
                interestsMap.put(interestsArray[5], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        politicsSeekBar = (SeekBar) findViewById(R.id.politicsSeekBar);
        politicsSeekBar.setMax(10);
        politicsSeekBar.setProgress(0);
        politicsTextView = (TextView) findViewById(R.id.politicsInterestTextView);
        politicsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                politicsTextView.requestFocus();
                politicsTextView.setText("Politics (" + i + ")");
                interestsMap.put(interestsArray[6], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        historySeekBar = (SeekBar) findViewById(R.id.historySeekBar);
        historySeekBar.setMax(10);
        historySeekBar.setProgress(0);
        historyTextView = (TextView) findViewById(R.id.historyInterestTextView);
        historySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                historyTextView.requestFocus();
                historyTextView.setText("History (" + i + ")");
                interestsMap.put(interestsArray[7], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        engineeringSeekBar = (SeekBar) findViewById(R.id.engineeringSeekBar);
        engineeringSeekBar.setMax(10);
        engineeringSeekBar.setProgress(0);
        engineeringTextView = (TextView) findViewById(R.id.engineeringInterestTextView);
        engineeringSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                engineeringTextView.requestFocus();
                engineeringTextView.setText("Engineering (" + i + ")");
                interestsMap.put(interestsArray[8], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        economicsSeekBar = (SeekBar) findViewById(R.id.economicsSeekBar);
        economicsSeekBar.setMax(10);
        economicsSeekBar.setProgress(0);
        economicsTextView = (TextView) findViewById(R.id.economicsInterestTextView);
        economicsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                economicsTextView.requestFocus();
                economicsTextView.setText("Economics (" + i + ")");
                interestsMap.put(interestsArray[9], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        literatureSeekBar = (SeekBar) findViewById(R.id.literatureSeekBar);
        literatureSeekBar.setMax(10);
        literatureSeekBar.setProgress(0);
        literatureTextView = (TextView) findViewById(R.id.literatureInterestTextView);
        literatureSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                literatureTextView.requestFocus();
                literatureTextView.setText("Literature (" + i + ")");
                interestsMap.put(interestsArray[10], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        comicsSeekBar = (SeekBar) findViewById(R.id.comicsSeekBar);
        comicsSeekBar.setMax(10);
        comicsSeekBar.setProgress(0);
        comicsTextView = (TextView) findViewById(R.id.comicsInterestTextView);
        comicsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                comicsTextView.requestFocus();
                comicsTextView.setText("Comics (" + i + ")");
                interestsMap.put(interestsArray[11], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        religionSeekBar = (SeekBar) findViewById(R.id.religionSeekBar);
        religionSeekBar.setMax(10);
        religionSeekBar.setProgress(0);
        religionTextView = (TextView) findViewById(R.id.religionInterestTextView);
        religionSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                religionTextView.requestFocus();
                religionTextView.setText("Religion (" + i + ")");
                interestsMap.put(interestsArray[12], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        artsSeekBar = (SeekBar) findViewById(R.id.artsSeekBar);
        artsSeekBar.setMax(10);
        artsSeekBar.setProgress(0);
        artsTextView = (TextView) findViewById(R.id.artsInterestTextView);
        artsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                artsTextView.requestFocus();
                artsTextView.setText("Arts (" + i + ")");
                interestsMap.put(interestsArray[13], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        travelSeekBar = (SeekBar) findViewById(R.id.travelSeekBar);
        travelSeekBar.setMax(10);
        travelSeekBar.setProgress(0);
        travelTextView = (TextView) findViewById(R.id.travelInterestTextView);
        travelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                travelTextView.requestFocus();
                travelTextView.setText("Travel (" + i + ")");
                interestsMap.put(interestsArray[14], i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }


    /* TODO add check to make sure all inputs are valid */
    // TODO: remove all user inputs that are not part of the auto complete suggestions
    public void continueCreateProfileButtonClicked(View view) {

        // check if input is empty
        if(usernameEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Enter a username.", Toast.LENGTH_SHORT).show();
        }
        else if(firstNameEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Enter a first name.", Toast.LENGTH_SHORT).show();
        }
        else if(lastNameEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Enter a last name.", Toast.LENGTH_SHORT).show();
        }
        else if(macTextView.getText().toString().equals("")) {
            Toast.makeText(this, "Enter enrolled courses. (separated by commas)", Toast.LENGTH_SHORT).show();
        }
        else if(tagsEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Enter topics of interest. (separated by commas)", Toast.LENGTH_SHORT).show();
        }

        // make sure a gender is selected
        else if(genderSpinner.getSelectedItem().toString().equals("Select")) {
            Toast.makeText(this, "Please select a gender.", Toast.LENGTH_SHORT).show();
        }

        // check if username contains non alphanumeric characters
        else if(!containsLettersOrDigits(usernameEditText.getText().toString())) {
            Toast.makeText(this, "Invalid username. Only use Letters and Numbers.", Toast.LENGTH_SHORT).show();
        }

        // check if first name and last name only contain letters
        else if(!containsLettersOnly(firstNameEditText.getText().toString())) {
            Toast.makeText(this, "Invalid First Name.", Toast.LENGTH_SHORT).show();
        }
        else if(!containsLettersOnly(lastNameEditText.getText().toString())) {
            Toast.makeText(this, "Invalid Last Name.", Toast.LENGTH_SHORT).show();
        }

        // make sure that some interest levels have been selected.
        // true if no interests selected. (all are 0)
        else if(checkIfInterestLevelsSelected()) {
            Toast.makeText(this, "Use the sliders to select how interested you are in the topics above.", Toast.LENGTH_SHORT).show();
        }

        // True if there is a problem with the tags user input
        else if(checkIfTagsAreIllegal(tagsEditText.getText().toString())) {
            Toast.makeText(this, "There are invalid characters in the tags you entered.", Toast.LENGTH_SHORT).show();
        }

        // it has passed all the cases to check valid input
        // go to the next screen (search screen?)
        else {

            // Update variable needed to create a User
            username = usernameEditText.getText().toString();
            first_name = firstNameEditText.getText().toString();
            last_name = lastNameEditText.getText().toString();
            gender = genderSpinner.getSelectedItem().toString();

            // Initialize variable needed to create a User
            courses  = new ArrayList<String>();
            tags  = new ArrayList<String>();
            met_history  = new ArrayList<Integer>();
            room_ids = new ArrayList<Room>();

            String[] coursesArray = macTextView.getText().toString().split(",");
            for(int i=0; i<coursesArray.length; i++) {
                courses.add(coursesArray[i]);
            }

            String[] tagArray = tagsEditText.getText().toString().split(",");
            for(int i=0; i<tagArray.length; i++) {
                tags.add(tagArray[i]);
            }

            user = new User(username, first_name, last_name, gender, courses, interestsMap, tags, met_history, room_ids);
            this.setProfile(user);

            // Go to the main activity after creating the user.
            Intent mainActivity = new Intent(CreatePersonalProfileForm.this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }

    }


    // True if there is a problem with the tags user input
    private boolean checkIfTagsAreIllegal(String str) {
        str = str.replaceAll(" ", "");
        String[] tags = str.split(",");
        for(String tag : tags) {
            if(!containsLettersOrDigits(tag)) {
                return true;
            }
        }
        return false;
    }


    // make sure that some interest levels have been selected
    // true if no interests selected.
    private boolean checkIfInterestLevelsSelected() {
        boolean allZeros = true;
        for (String key : interestsArray) {
            if(interestsMap.get(key) != 0) {
                allZeros = false;
            }
        }
        return allZeros;
    }


    public boolean containsLettersOrDigits(String str) {
        char[] charArray = str.toCharArray();
        for(char c : charArray) {
            int charVal = (int) c;
            if( !(((charVal>=65) && (charVal<=90)) || ((charVal>=97) && (charVal<=122)) || ((charVal>=48) && (charVal<=57))) ) {
                return false;
            }
        }
        return true;
    }


    public boolean containsLettersOnly(String str) {
        char[] charArray = str.toCharArray();
        for(char c : charArray) {
            int charVal = (int) c;
            if( !(((charVal>=65) && (charVal<=90)) || ((charVal>=97) && (charVal<=122))) ) {
                return false;
            }
        }
        return true;
    }

}
