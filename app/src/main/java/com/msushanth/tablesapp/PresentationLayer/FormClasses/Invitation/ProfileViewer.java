package com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
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
    String ID;

    TextView usersName;
    //TextView usersEmail;
    TextView usersBio;
    TextView usersCourses;
    TextView usersTags;
    TextView usersGender;

    ProgressBar sportsProgressBar;
    ProgressBar musicProgressBar;
    ProgressBar gamesProgressBar;
    ProgressBar moviesProgressBar;
    ProgressBar technologyProgressBar;
    ProgressBar scienceProgressBar;
    ProgressBar politicsProgressBar;
    ProgressBar historyProgressBar;
    ProgressBar engineeringProgressBar;
    ProgressBar economicsProgressBar;
    ProgressBar literatureProgressBar;
    ProgressBar comicsProgressBar;
    ProgressBar religionProgressBar;
    ProgressBar artsProgressBar;
    ProgressBar travelProgressBar;
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
        //usersEmail = (TextView) findViewById(R.id.UsersEmail);
        usersBio = (TextView) findViewById(R.id.bioContent);
        usersCourses = (TextView) findViewById(R.id.usersCourses);
        usersTags = (TextView) findViewById(R.id.usersTags);
        usersGender = (TextView) findViewById(R.id.usersGender);


        // Create seekbars and prevent them from being changed
        sportsProgressBar = (ProgressBar) findViewById(R.id.sportsProgressBar); sportsProgressBar.setMax(10); sportsProgressBar.setProgress(0);
        musicProgressBar = (ProgressBar) findViewById(R.id.musicProgressBar); musicProgressBar.setMax(10); musicProgressBar.setProgress(0);
        gamesProgressBar = (ProgressBar) findViewById(R.id.gamesProgressbar); gamesProgressBar.setMax(10); gamesProgressBar.setProgress(0);
        moviesProgressBar = (ProgressBar) findViewById(R.id.moviesProgressBar); moviesProgressBar.setMax(10); moviesProgressBar.setProgress(0);
        technologyProgressBar = (ProgressBar) findViewById(R.id.technologyProgressBar); technologyProgressBar.setMax(10); technologyProgressBar.setProgress(0);
        scienceProgressBar = (ProgressBar) findViewById(R.id.scienceProgressBar); scienceProgressBar.setMax(10); scienceProgressBar.setProgress(0);
        politicsProgressBar = (ProgressBar) findViewById(R.id.politicsProgressBar); politicsProgressBar.setMax(10); politicsProgressBar.setProgress(0);
        historyProgressBar = (ProgressBar) findViewById(R.id.historyProgressBar); historyProgressBar.setMax(10); historyProgressBar.setProgress(0);
        engineeringProgressBar = (ProgressBar) findViewById(R.id.engineeringProgressBar); engineeringProgressBar.setMax(10); engineeringProgressBar.setProgress(0);
        economicsProgressBar = (ProgressBar) findViewById(R.id.economicsProgressBar); economicsProgressBar.setMax(10); economicsProgressBar.setProgress(0);
        literatureProgressBar = (ProgressBar) findViewById(R.id.literatureProgressBar); literatureProgressBar.setMax(10); literatureProgressBar.setProgress(0);
        comicsProgressBar = (ProgressBar) findViewById(R.id.comicsProgressBar); comicsProgressBar.setMax(10); comicsProgressBar.setProgress(0);
        religionProgressBar = (ProgressBar) findViewById(R.id.religionProgressBar); religionProgressBar.setMax(10); religionProgressBar.setProgress(0);
        artsProgressBar = (ProgressBar) findViewById(R.id.artsProgressBar); artsProgressBar.setMax(10); artsProgressBar.setProgress(0);
        travelProgressBar = (ProgressBar) findViewById(R.id.travelProgressBar); travelProgressBar.setMax(10); travelProgressBar.setProgress(0);

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
                Intent intent = getIntent();
                ID = intent.getStringExtra("matchedUsersID");
                currentUserProfile = dataSnapshot.child(ID).getValue(User.class);

                // Set the text for the TextViews using the appropriate User data.
                // set users name text view
                usersName.setText(currentUserProfile.getFirst_name() + " " + currentUserProfile.getLast_name());

                //set users email text view
                //usersEmail.setText(fireBaseUser.getEmail());
                usersBio.setText(currentUserProfile.getBio());

                // set users courses textview
                usersCourses.setText(TextUtils.join(", ", currentUserProfile.getCourses()));

                // set users interest levels textview
                Resources res = getResources();
                interestsArray = res.getStringArray(R.array.interests);
                Map<String,Integer> interestsMap = currentUserProfile.getInterests();
                sportsProgressBar.setProgress(interestsMap.get(interestsArray[0]));
                musicProgressBar.setProgress(interestsMap.get(interestsArray[1]));
                gamesProgressBar.setProgress(interestsMap.get(interestsArray[2]));
                moviesProgressBar.setProgress(interestsMap.get(interestsArray[3]));
                technologyProgressBar.setProgress(interestsMap.get(interestsArray[4]));
                scienceProgressBar.setProgress(interestsMap.get(interestsArray[5]));
                politicsProgressBar.setProgress(interestsMap.get(interestsArray[6]));
                historyProgressBar.setProgress(interestsMap.get(interestsArray[7]));
                engineeringProgressBar.setProgress(interestsMap.get(interestsArray[8]));
                economicsProgressBar.setProgress(interestsMap.get(interestsArray[9]));
                literatureProgressBar.setProgress(interestsMap.get(interestsArray[10]));
                comicsProgressBar.setProgress(interestsMap.get(interestsArray[11]));
                religionProgressBar.setProgress(interestsMap.get(interestsArray[12]));
                artsProgressBar.setProgress(interestsMap.get(interestsArray[13]));
                travelProgressBar.setProgress(interestsMap.get(interestsArray[14]));

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
