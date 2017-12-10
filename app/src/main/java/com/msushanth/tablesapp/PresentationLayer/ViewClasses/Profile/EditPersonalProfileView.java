package com.msushanth.tablesapp.PresentationLayer.ViewClasses.Profile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
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
import com.google.firebase.database.DatabaseReference;
import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.ProfileController;
import com.msushanth.tablesapp.Interfaces.Profile.EditPersonalProfileInterface;
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

// handles displaying and taking in user input from the edit personal profile screen
public class EditPersonalProfileView extends AppCompatActivity implements EditPersonalProfileInterface {

    private Toolbar mToolbar;


    EditText usernameEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText bioEditText;

    Spinner genderSpinner;
    ArrayAdapter<CharSequence> genderAdapter;

    EditText coursesEditText;
    ArrayList<String> coursesArrayList;
    TagView coursesTagView;
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



    DatabaseReference dbReference;
    String currentUserID;

    User currentUser;
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


    ProfileController profileController = new ProfileController();

    // create the edit profile screen and assign variables to different display elements
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_personal_profile_form);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        TextView pageTitle = (TextView) mToolbar.findViewById(R.id.pageTitle);
        setSupportActionBar(mToolbar);
        pageTitle.setText("Edit Profile");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // add the back button to the toolbar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Create progress dialog and dismiss it once all the data is written to the screen
        final ProgressDialog progressDialog = ProgressDialog.show(EditPersonalProfileView.this, "Please wait...", "Processing", true);


        currentUserID = getIntent().getStringExtra("UserID");


        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        bioEditText = (EditText) findViewById(R.id.bioEditText);

        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        genderAdapter = ArrayAdapter.createFromResource(this, R.array.genders, R.layout.spinner_item_dark);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        coursesEditText = (EditText) findViewById(R.id.coursesEditText);
        coursesTagView = (TagView) findViewById(R.id.CoursesTagView);
        addCourseButton = (Button) findViewById(R.id.AddCourseButton);

        interestsEditText = (EditText) findViewById(R.id.tagsEditText);
        interestsTagView = (TagView) findViewById(R.id.InterestsTagView);
        addInterestButton = (Button) findViewById(R.id.AddInterestButton);

        // set fields on the screen that rely on database values
        setFields();

        /*dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("******* EDITING USER *******");
                currentUser = dataSnapshot.child(currentUserID).getValue(User.class);

                usernameEditText.setText(currentUser.getUsername());
                firstNameEditText.setText(currentUser.getFirst_name());
                lastNameEditText.setText(currentUser.getLast_name());
                bioEditText.setText(currentUser.getBio());

                genderSpinner.setSelection(genderAdapter.getPosition(currentUser.getGender()));

                // User types in classes. When he completes typing and presses "add" a tag should appear under the EditText
                coursesArrayList = new ArrayList<String>(currentUser.getCourses());
                for(String course : coursesArrayList) {
                    Tag tag = new Tag(course);
                    tag.isDeletable = true;
                    tag.layoutColor = getResources().getColor(R.color.colorSecondaryA200);
                    coursesTagView.addTag(tag);
                }
                coursesTagView.setOnTagDeleteListener(new TagView.OnTagDeleteListener() {

                    @Override
                    public void onTagDeleted(final TagView view, final Tag tag, final int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditPersonalProfileView.this);
                        builder.setMessage("\"" + tag.text + "\" will be delete. Are you sure?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                view.remove(position);
                                Toast.makeText(EditPersonalProfileView.this, "\"" + tag.text + "\" deleted", Toast.LENGTH_SHORT).show();
                                coursesArrayList.remove(tag.text);
                            }
                        });
                        builder.setNegativeButton("No", null);
                        builder.show();
                    }
                });

                // User types in tags. When he completes typing and presses "add" a tag should appear under the EditText
                interestsArrayList = new ArrayList<String>(currentUser.getTags());
                for(String tagStr : interestsArrayList) {
                    Tag tag = new Tag(tagStr);
                    tag.isDeletable = true;
                    tag.layoutColor = getResources().getColor(R.color.colorSecondaryA200);
                    interestsTagView.addTag(tag);
                }
                interestsTagView.setOnTagDeleteListener(new TagView.OnTagDeleteListener() {

                    @Override
                    public void onTagDeleted(final TagView view, final Tag tag, final int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditPersonalProfileView.this);
                        builder.setMessage("\"" + tag.text + "\" will be delete. Are you sure?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                view.remove(position);
                                Toast.makeText(EditPersonalProfileView.this, "\"" + tag.text + "\" deleted", Toast.LENGTH_SHORT).show();
                                interestsArrayList.remove(tag.text);
                            }
                        });
                        builder.setNegativeButton("No", null);
                        builder.show();
                    }
                });


                initializeInterestsMap(currentUser);
                initializeSeekBars(currentUser);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        }); */

        // finish setting up the screen
        coursesEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        coursesEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);


        bioEditText = (EditText) findViewById(R.id.bioEditText);
        bioEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        bioEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);


        interestsEditText = (EditText) findViewById(R.id.tagsEditText);
        interestsEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        interestsEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);

        progressDialog.dismiss();
    }


    // implement the back button to the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void continueEditProfileButtonClicked(View view) {
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
            Toast.makeText(this, "Enter enrolled courses.", Toast.LENGTH_SHORT).show();
        }
        else if(interestsArrayList.size() == 0) {
            Toast.makeText(this, "Enter topics of interest.", Toast.LENGTH_SHORT).show();
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

            currentUser.setUsername(username);
            currentUser.setFirst_name(first_name);
            currentUser.setLast_name(last_name);
            currentUser.setGender(gender);
            currentUser.setCourses(courses);
            currentUser.setInterests(interestsMap);
            currentUser.setTags(tags);
            currentUser.setMet_history(met_history);
            currentUser.setRoom_ids(room_ids);
            currentUser.setBio(bio);


            this.editProfile(currentUser);


            // Finish editing profile activity when updated profile.
            Toast.makeText(EditPersonalProfileView.this, "Profile has been edited.", Toast.LENGTH_SHORT).show();


            // Wait until the toast is done displaying before going back exiting this activity
            (new Handler()).postDelayed(
                    new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 2000);
        }

    }




    @Override
    public void editProfile(User user) {
        ProfileController profileController = new ProfileController();
        profileController.editProfile(user);
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

    // make sure string contains only letters or digits
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

    // make sure string contains only letters
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




    // When the add button next to courses input is clicked.
    public void addCourse(View view) {
        String str = coursesEditText.getText().toString();
        String strCheck = str.replaceAll(" ", "");
        if(!strCheck.equals("")) {
            Tag tag = new Tag(str);
            tag.isDeletable = true;
            tag.layoutColor = getResources().getColor(R.color.tagsColor);
            coursesTagView.addTag(tag);

            coursesArrayList.add(tag.text);

            coursesEditText.setText("");
        }
    }




    // When the add button next to interests input is clicked.
    public void addInterest(View view) {
        String str = interestsEditText.getText().toString();
        String strCheck = str.replaceAll(" ", "");
        if(!strCheck.equals("")) {
            Tag tag = new Tag(str);
            tag.isDeletable = true;
            tag.layoutColor = getResources().getColor(R.color.tagsColor);
            interestsTagView.addTag(tag);

            interestsArrayList.add(tag.text);

            interestsEditText.setText("");
        }
    }


    // set up the interests entry point
    private void initializeInterestsMap(User currentUser) {
        Map<String,Integer> interests = currentUser.getInterests();

        Resources res = getResources();
        interestsArray = res.getStringArray(R.array.interests);
        interestsMap = new HashMap<String, Integer>();
        for(String s : interestsArray) {
            interestsMap.put(s, interests.get(s));
        }

    }


    // initalize seek bars that represent a users rating of each category
    private void initializeSeekBars(User currentUser) {
        Map<String,Integer> interests = currentUser.getInterests();

        sportsSeekBar = (SeekBar) findViewById(R.id.sportsSeekBar);
        sportsSeekBar.setMax(10);
        sportsSeekBar.setProgress(interests.get(interestsArray[0]));
        sportsTextView = (TextView) findViewById(R.id.sportsInterestTextView);
        sportsTextView.setText("Sports (" + interests.get(interestsArray[0]) + ")");
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
        musicSeekBar.setProgress(interests.get(interestsArray[1]));
        musicTextView = (TextView) findViewById(R.id.musicInterestTextView);
        musicTextView.setText("Music (" + interests.get(interestsArray[1]) + ")");
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
        gamesSeekBar.setProgress(interests.get(interestsArray[2]));
        gamesTextView = (TextView) findViewById(R.id.gamesInterestTextView);
        gamesTextView.setText("Games (" + interests.get(interestsArray[2]) + ")");
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
        moviesSeekBar.setProgress(interests.get(interestsArray[3]));
        moviesTextView = (TextView) findViewById(R.id.moviesInterestTextView);
        moviesTextView.setText("Movies (" + interests.get(interestsArray[3]) + ")");
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
        technologySeekBar.setProgress(interests.get(interestsArray[4]));
        technologyTextView = (TextView) findViewById(R.id.technologyInterestTextView);
        technologyTextView.setText("Technology (" + interests.get(interestsArray[4]) + ")");
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
        scienceSeekBar.setProgress(interests.get(interestsArray[5]));
        scienceTextView = (TextView) findViewById(R.id.scienceInterestTextView);
        scienceTextView.setText("Science (" + interests.get(interestsArray[5]) + ")");
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
        politicsSeekBar.setProgress(interests.get(interestsArray[6]));
        politicsTextView = (TextView) findViewById(R.id.politicsInterestTextView);
        politicsTextView.setText("Politics (" + interests.get(interestsArray[6]) + ")");
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
        historySeekBar.setProgress(interests.get(interestsArray[7]));
        historyTextView = (TextView) findViewById(R.id.historyInterestTextView);
        historyTextView.setText("History (" + interests.get(interestsArray[7]) + ")");
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
        engineeringSeekBar.setProgress(interests.get(interestsArray[8]));
        engineeringTextView = (TextView) findViewById(R.id.engineeringInterestTextView);
        engineeringTextView.setText("Engineering (" + interests.get(interestsArray[8]) + ")");
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
        economicsSeekBar.setProgress(interests.get(interestsArray[9]));
        economicsTextView = (TextView) findViewById(R.id.economicsInterestTextView);
        economicsTextView.setText("Economics (" + interests.get(interestsArray[9]) + ")");
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
        literatureSeekBar.setProgress(interests.get(interestsArray[10]));
        literatureTextView = (TextView) findViewById(R.id.literatureInterestTextView);
        literatureTextView.setText("Literature (" + interests.get(interestsArray[10]) + ")");
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
        comicsSeekBar.setProgress(interests.get(interestsArray[11]));
        comicsTextView = (TextView) findViewById(R.id.comicsInterestTextView);
        comicsTextView.setText("Comics (" + interests.get(interestsArray[11]) + ")");
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
        religionSeekBar.setProgress(interests.get(interestsArray[12]));
        religionTextView = (TextView) findViewById(R.id.religionInterestTextView);
        religionTextView.setText("Religion (" + interests.get(interestsArray[12]) + ")");
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
        artsSeekBar.setProgress(interests.get(interestsArray[13]));
        artsTextView = (TextView) findViewById(R.id.artsInterestTextView);
        artsTextView.setText("Arts (" + interests.get(interestsArray[13]) + ")");
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
        travelSeekBar.setProgress(interests.get(interestsArray[14]));
        travelTextView = (TextView) findViewById(R.id.travelInterestTextView);
        travelTextView.setText("Travel (" + interests.get(interestsArray[14]) + ")");
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

    // set fields so that the user sees their profile in its current state
    public User setFields() {
        // get current user from controller
        currentUser = profileController.getFields();

        usernameEditText.setText(currentUser.getUsername());
        firstNameEditText.setText(currentUser.getFirst_name());
        lastNameEditText.setText(currentUser.getLast_name());
        bioEditText.setText(currentUser.getBio());

        genderSpinner.setSelection(genderAdapter.getPosition(currentUser.getGender()));

        // User types in classes. When he completes typing and presses "add" a tag should appear under the EditText
        coursesArrayList = new ArrayList<String>(currentUser.getCourses());
        for (String course : coursesArrayList) {
            Tag tag = new Tag(course);
            tag.isDeletable = true;
            tag.layoutColor = getResources().getColor(R.color.tagsColor);
            coursesTagView.addTag(tag);
        }
        coursesTagView.setOnTagDeleteListener(new TagView.OnTagDeleteListener() {

            @Override
            public void onTagDeleted(final TagView view, final Tag tag, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPersonalProfileView.this);
                builder.setMessage("\"" + tag.text + "\" will be delete. Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        view.remove(position);
                        Toast.makeText(EditPersonalProfileView.this, "\"" + tag.text + "\" deleted", Toast.LENGTH_SHORT).show();
                        coursesArrayList.remove(tag.text);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });

        // User types in tags. When he completes typing and presses "add" a tag should appear under the EditText
        interestsArrayList = new ArrayList<String>(currentUser.getTags());
        for (String tagStr : interestsArrayList) {
            Tag tag = new Tag(tagStr);
            tag.isDeletable = true;
            tag.layoutColor = getResources().getColor(R.color.tagsColor);
            interestsTagView.addTag(tag);
        }
        interestsTagView.setOnTagDeleteListener(new TagView.OnTagDeleteListener() {

            @Override
            public void onTagDeleted(final TagView view, final Tag tag, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPersonalProfileView.this);
                builder.setMessage("\"" + tag.text + "\" will be delete. Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        view.remove(position);
                        Toast.makeText(EditPersonalProfileView.this, "\"" + tag.text + "\" deleted", Toast.LENGTH_SHORT).show();
                        interestsArrayList.remove(tag.text);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });


        initializeInterestsMap(currentUser);
        initializeSeekBars(currentUser);

        return currentUser;
    }
}
