package com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.User;

import java.util.Map;

public class ProfileViewer extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference dbReference;
    FirebaseUser fireBaseUser;
    User currentUserProfile;

    TextView usersName;
    TextView usersEmail;
    TextView usersCourses;
    TextView usersTags;
    TextView usersGender;

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

    String [] interestsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_viewer);

        // Create progress dialog and dismiss it once all the data is written to the screen
        final ProgressDialog progressDialog = ProgressDialog.show(ProfileViewer.this, "Please wait...", "Processing", true);

        usersName = (TextView) findViewById(R.id.UsersName);
        usersEmail = (TextView) findViewById(R.id.UsersEmail);
        usersCourses = (TextView) findViewById(R.id.usersCourses);
        usersTags = (TextView) findViewById(R.id.usersTags);
        usersGender = (TextView) findViewById(R.id.usersGender);


        // Create seekbars and prevent them from being changed
        sportsSeekBar = (SeekBar) findViewById(R.id.sportsSeekBar); sportsSeekBar.setMax(10); sportsSeekBar.setProgress(0);
        sportsSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        musicSeekBar = (SeekBar) findViewById(R.id.musicSeekBar); musicSeekBar.setMax(10); musicSeekBar.setProgress(0);
        musicSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        gamesSeekBar = (SeekBar) findViewById(R.id.gamesSeekBar); gamesSeekBar.setMax(10); gamesSeekBar.setProgress(0);
        gamesSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        moviesSeekBar = (SeekBar) findViewById(R.id.moviesSeekBar); moviesSeekBar.setMax(10); moviesSeekBar.setProgress(0);
        moviesSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        technologySeekBar = (SeekBar) findViewById(R.id.technologySeekBar); technologySeekBar.setMax(10); technologySeekBar.setProgress(0);
        technologySeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        scienceSeekBar = (SeekBar) findViewById(R.id.scienceSeekBar); scienceSeekBar.setMax(10); scienceSeekBar.setProgress(0);
        scienceSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        politicsSeekBar = (SeekBar) findViewById(R.id.politicsSeekBar); politicsSeekBar.setMax(10); politicsSeekBar.setProgress(0);
        politicsSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        historySeekBar = (SeekBar) findViewById(R.id.historySeekBar); historySeekBar.setMax(10); historySeekBar.setProgress(0);
        historySeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        engineeringSeekBar = (SeekBar) findViewById(R.id.engineeringSeekBar); engineeringSeekBar.setMax(10); engineeringSeekBar.setProgress(0);
        engineeringSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        economicsSeekBar = (SeekBar) findViewById(R.id.economicsSeekBar); economicsSeekBar.setMax(10); economicsSeekBar.setProgress(0);
        economicsSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        literatureSeekBar = (SeekBar) findViewById(R.id.literatureSeekBar); literatureSeekBar.setMax(10); literatureSeekBar.setProgress(0);
        literatureSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        comicsSeekBar = (SeekBar) findViewById(R.id.comicsSeekBar); comicsSeekBar.setMax(10); comicsSeekBar.setProgress(0);
        comicsSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        religionSeekBar = (SeekBar) findViewById(R.id.religionSeekBar); religionSeekBar.setMax(10); religionSeekBar.setProgress(0);
        religionSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        artsSeekBar = (SeekBar) findViewById(R.id.artsSeekBar); artsSeekBar.setMax(10); artsSeekBar.setProgress(0);
        artsSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        travelSeekBar = (SeekBar) findViewById(R.id.travelSeekBar); travelSeekBar.setMax(10); travelSeekBar.setProgress(0);
        travelSeekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        sportsTextView = (TextView) findViewById(R.id.sportsInterestTextView);
        musicTextView = (TextView) findViewById(R.id.musicInterestTextView);
        gamesTextView = (TextView) findViewById(R.id.gamesInterestTextView);
        moviesTextView = (TextView) findViewById(R.id.moviesInterestTextView);
        technologyTextView = (TextView) findViewById(R.id.technologyInterestTextView);
        scienceTextView = (TextView) findViewById(R.id.scienceInterestTextView);
        politicsTextView = (TextView) findViewById(R.id.politicsInterestTextView);
        historyTextView = (TextView) findViewById(R.id.historyInterestTextView);
        engineeringTextView = (TextView) findViewById(R.id.engineeringInterestTextView);
        economicsTextView = (TextView) findViewById(R.id.economicsInterestTextView);
        literatureTextView = (TextView) findViewById(R.id.literatureInterestTextView);
        comicsTextView = (TextView) findViewById(R.id.comicsInterestTextView);
        religionTextView = (TextView) findViewById(R.id.religionInterestTextView);
        artsTextView = (TextView) findViewById(R.id.artsInterestTextView);
        travelTextView = (TextView) findViewById(R.id.travelInterestTextView);




        // TODO: pass in the Users unique id of the profile you want to view as part of the intent (from SelectMatchedUser).
        // Theres a way to do this.
        // Look it up
        // The passed in variable should be in the form of a string.
        // After you have figured this out, replace this line:
        //currentUserProfile = dataSnapshot.child(fireBaseUser.getUid()).getValue(User.class);
        // with:
        // userProfile = dataSnapshot.child(*passed in variable*).getValue(User.class);
        // and you will get the users profile you clicked on displayed on the screen.
        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        fireBaseUser = firebaseAuth.getCurrentUser();
        // Get user data from database and display it
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This is the current users profile
                currentUserProfile = dataSnapshot.child(fireBaseUser.getUid()).getValue(User.class);

                // Set the text for the TextViews using the appropriate User data.
                // set users name text view
                usersName.setText(currentUserProfile.getFirst_name() + " " + currentUserProfile.getLast_name());

                //set users email text view
                usersEmail.setText(fireBaseUser.getEmail());

                // set users courses textview
                usersCourses.setText(TextUtils.join(", ", currentUserProfile.getCourses()));

                // set users interest levels textview
                Resources res = getResources();
                interestsArray = res.getStringArray(R.array.interests);
                Map<String,Integer> interestsMap = currentUserProfile.getInterests();
                sportsSeekBar.setProgress(interestsMap.get(interestsArray[0]));
                musicSeekBar.setProgress(interestsMap.get(interestsArray[1]));
                gamesSeekBar.setProgress(interestsMap.get(interestsArray[2]));
                moviesSeekBar.setProgress(interestsMap.get(interestsArray[3]));
                technologySeekBar.setProgress(interestsMap.get(interestsArray[4]));
                scienceSeekBar.setProgress(interestsMap.get(interestsArray[5]));
                politicsSeekBar.setProgress(interestsMap.get(interestsArray[6]));
                historySeekBar.setProgress(interestsMap.get(interestsArray[7]));
                engineeringSeekBar.setProgress(interestsMap.get(interestsArray[8]));
                economicsSeekBar.setProgress(interestsMap.get(interestsArray[9]));
                literatureSeekBar.setProgress(interestsMap.get(interestsArray[10]));
                comicsSeekBar.setProgress(interestsMap.get(interestsArray[11]));
                religionSeekBar.setProgress(interestsMap.get(interestsArray[12]));
                artsSeekBar.setProgress(interestsMap.get(interestsArray[13]));
                travelSeekBar.setProgress(interestsMap.get(interestsArray[14]));
                sportsTextView.setText("Sports (" + interestsMap.get(interestsArray[0]) + ")");
                musicTextView.setText("Music (" + interestsMap.get(interestsArray[1]) + ")");
                gamesTextView.setText("Games (" + interestsMap.get(interestsArray[2]) + ")");
                moviesTextView.setText("Movies (" + interestsMap.get(interestsArray[3]) + ")");
                technologyTextView.setText("Technology (" + interestsMap.get(interestsArray[4]) + ")");
                scienceTextView.setText("Science (" + interestsMap.get(interestsArray[5]) + ")");
                politicsTextView.setText("Politics (" + interestsMap.get(interestsArray[6]) + ")");
                historyTextView.setText("History (" + interestsMap.get(interestsArray[7]) + ")");
                engineeringTextView.setText("Engineering (" + interestsMap.get(interestsArray[8]) + ")");
                economicsTextView.setText("Economics (" + interestsMap.get(interestsArray[9]) + ")");
                literatureTextView.setText("Literature (" + interestsMap.get(interestsArray[10]) + ")");
                comicsTextView.setText("Comics (" + interestsMap.get(interestsArray[11]) + ")");
                religionTextView.setText("Religion (" + interestsMap.get(interestsArray[12]) + ")");
                artsTextView.setText("Arts (" + interestsMap.get(interestsArray[13]) + ")");
                travelTextView.setText("Travel (" + interestsMap.get(interestsArray[14]) + ")");

                // set users tags textview
                usersTags.setText(TextUtils.join(", ", currentUserProfile.getTags()));

                // set users gender textview
                usersGender.setText(currentUserProfile.getGender());

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


    public void backButtonClicked(View v) {
        finish();
    }

}
