package com.msushanth.tablesapp.PresentationLayer.FormClasses.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.msushanth.tablesapp.Interfaces.Profile.PersonalProfileInterface;
import com.msushanth.tablesapp.MainActivity;
import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Profile.CreatePersonalProfileAction;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.User;

import java.util.ArrayList;
import java.util.Arrays;
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
    EditText bioEditText;

    Spinner genderSpinner;
    ArrayAdapter<CharSequence> genderAdapter;

    EditText coursesEditText;
    ArrayList<String> coursesArrayList;
    TagView coursesTags;
    Button addCourseButton;

    EditText interestsEditText;
    ArrayList<String> interestsArrayList;
    TagView interestsTagView;
    Button addInterestButton;

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
    String bio;
    String gender;
    List<String> courses;
    Map<String,Integer> interestsMap;
    List<String> tags;
    List<Integer> met_history;
    List<String> room_ids;

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

        // User types in classes. When he completes typing and presses "add" a tag should appear under the EditText
        coursesEditText = (EditText) findViewById(R.id.coursesEditText);
        coursesTags = (TagView) findViewById(R.id.CoursesTagView);
        addCourseButton = (Button) findViewById(R.id.AddCourseButton);
        coursesArrayList = new ArrayList<String>();
        coursesTags.setOnTagDeleteListener(new TagView.OnTagDeleteListener() {

            @Override
            public void onTagDeleted(final TagView view, final Tag tag, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreatePersonalProfileForm.this);
                builder.setMessage("\"" + tag.text + "\" will be delete. Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        view.remove(position);
                        Toast.makeText(CreatePersonalProfileForm.this, "\"" + tag.text + "\" deleted", Toast.LENGTH_SHORT).show();
                        coursesArrayList.remove(tag.text);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });


        // User types in tags. When he completes typing and presses "add" a tag should appear under the EditText
        interestsEditText = (EditText) findViewById(R.id.tagsEditText);
        interestsTagView = (TagView) findViewById(R.id.InterestsTagView);
        addInterestButton = (Button) findViewById(R.id.AddInterestButton);
        interestsArrayList = new ArrayList<String>();
        interestsTagView.setOnTagDeleteListener(new TagView.OnTagDeleteListener() {

            @Override
            public void onTagDeleted(final TagView view, final Tag tag, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreatePersonalProfileForm.this);
                builder.setMessage("\"" + tag.text + "\" will be delete. Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        view.remove(position);
                        Toast.makeText(CreatePersonalProfileForm.this, "\"" + tag.text + "\" deleted", Toast.LENGTH_SHORT).show();
                        interestsArrayList.remove(tag.text);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });

        coursesEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        coursesEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);


        bioEditText = (EditText) findViewById(R.id.bioEditText);
        bioEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        bioEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);


        interestsEditText = (EditText) findViewById(R.id.tagsEditText);
        interestsEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        interestsEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);

        // Initialize the interests Map so we can add the seek bar data to it
        initializeInterestsMap();

        // Initialize the SeekBars and their corresponding TextViews
        initializeSeekBars();
    }


    // When the add button is clicked. The one next to courses input
    public void addCourse(View view) {
        String str = coursesEditText.getText().toString();
        String strCheck = str.replaceAll(" ", "");
        if(!strCheck.equals("")) {
            Tag tag = new Tag(str.substring(0,str.length()-1));
            tag.isDeletable = true;
            tag.layoutColor = getResources().getColor(R.color.colorSecondaryA200);
            coursesTags.addTag(tag);

            coursesArrayList.add(tag.text);

            coursesEditText.setText("");
        }
    }


    // When the add button is clicked. The one next to courses input
    public void addInterest(View view) {
        String str = interestsEditText.getText().toString();
        String strCheck = str.replaceAll(" ", "");
        if(!strCheck.equals("")) {
            Tag tag = new Tag(str.substring(0,str.length()-1));
            tag.isDeletable = true;
            tag.layoutColor = getResources().getColor(R.color.tagsColor);
            interestsTagView.addTag(tag);

            interestsArrayList.add(tag.text);

            interestsEditText.setText("");
        }
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
        else if(coursesArrayList.size() == 0) {
            Toast.makeText(this, "Enter enrolled courses. (separated by commas)", Toast.LENGTH_SHORT).show();
        }
        else if(interestsArrayList.size() == 0) {
            Toast.makeText(this, "Enter topics of interest. (separated by commas)", Toast.LENGTH_SHORT).show();
        }
        else if(bioEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Enter a short bio.", Toast.LENGTH_SHORT).show();
        }
        else if(bioEditText.getText().toString().length() < 30) {
            Toast.makeText(this, "Enter a longer bio.", Toast.LENGTH_SHORT).show();
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

        // True if there is a problem with the courses user input
        else if(checkIfTagsAreIllegal(Arrays.toString(coursesArrayList.toArray()))) {
            Toast.makeText(this, "There are invalid characters in the courses you entered.", Toast.LENGTH_SHORT).show();
        }

        // True if there is a problem with the tags user input
        else if(checkIfTagsAreIllegal(Arrays.toString(interestsArrayList.toArray()))) {
            Toast.makeText(this, "There are invalid characters in the interests you entered.", Toast.LENGTH_SHORT).show();
        }

        // it has passed all the cases to check valid input
        // go to the next screen (search screen?)
        else {

            // Update variable needed to create a User
            // Capitalize the first letter in the first and last name if they are not already capitalized.
            username = usernameEditText.getText().toString();
            first_name = firstNameEditText.getText().toString();
            first_name = first_name.substring(0,1).toUpperCase() + first_name.substring(1).toLowerCase();
            last_name = lastNameEditText.getText().toString();
            last_name = last_name.substring(0,1).toUpperCase() + last_name.substring(1).toLowerCase();
            gender = genderSpinner.getSelectedItem().toString();

            // Initialize variable needed to create a User
            courses  = coursesArrayList;
            tags  = interestsArrayList;
            met_history  = new ArrayList<Integer>();
            room_ids = new ArrayList<String>();

            bio = bioEditText.getText().toString();

            user = new User(username, first_name, last_name, gender, courses, interestsMap, tags, met_history, room_ids, bio);
            this.setProfile(user);

            // Go to the main activity after creating the user.
            Intent mainActivity = new Intent(CreatePersonalProfileForm.this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }

    }


    @Override
    public void setProfile(User user) {
        CreatePersonalProfileAction createProfileAction = new CreatePersonalProfileAction();
        createProfileAction.setProfile(user);
    }


    // True if there is a problem with the tags user input
    private boolean checkIfTagsAreIllegal(String str) {
        str = str.replaceAll(" ", "");
        str = str.substring(1, str.length()-1);

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
