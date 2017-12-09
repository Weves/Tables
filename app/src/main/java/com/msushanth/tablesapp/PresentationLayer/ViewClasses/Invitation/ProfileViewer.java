package com.msushanth.tablesapp.PresentationLayer.ViewClasses.Invitation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.ProfileController;
import com.msushanth.tablesapp.R;
import com.msushanth.tablesapp.User;

import java.util.List;
import java.util.Map;

public class ProfileViewer extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView pageTitle;

    String ID;

    //TextView usersName;
    //TextView usersEmail;
    TextView usersBio;
    TextView usersGender;


    TagView coursesTagView;
    TagView interestsTagView;


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

    // create the screen to display a users profile
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_viewer);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        pageTitle = (TextView) mToolbar.findViewById(R.id.pageTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // add the back button to the toolbar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Create progress dialog and dismiss it once all the data is written to the screen
        final ProgressDialog progressDialog = ProgressDialog.show(ProfileViewer.this, "Please wait...", "Processing", true);

        usersBio = (TextView) findViewById(R.id.bioContent);
        usersGender = (TextView) findViewById(R.id.usersGender);

        coursesTagView = (TagView) findViewById(R.id.CoursesTagView);
        interestsTagView = (TagView) findViewById(R.id.InterestsTagView);

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

        Intent intent = getIntent();
        ID = intent.getStringExtra("matchedUsersID");

        getUser(ID);


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


    // get a users info and set the display elemements for this class
    private void getUser(String ID) {
        ProfileController profileController = new ProfileController();
        profileController.getUser(ID, this);
    }

    // set the display elements on the screen
    public void setDisplayElements(User currentUserProfile) {

        // Set the text for the TextViews using the appropriate User data.
        // set users name text view
        //usersName.setText(currentUserProfile.getFirst_name() + " " + currentUserProfile.getLast_name());
        pageTitle.setText(currentUserProfile.getFirst_name() + " " + currentUserProfile.getLast_name());


        //set users email text view
        //usersEmail.setText(fireBaseUser.getEmail());
        usersBio.setText(currentUserProfile.getBio());

        List<String> coursesArrayList = currentUserProfile.getCourses();
        for(int i=0; i<coursesArrayList.size(); i++) {
            Tag tag = new Tag(coursesArrayList.get(i));
            tag.layoutColor = Color.parseColor("#7C4DFF");
            tag.layoutColorPress = Color.parseColor("#7C4DFF");
            coursesTagView.addTag(tag);
        }

        List<String> tagsArrayList = currentUserProfile.getTags();
        for(int i=0; i<tagsArrayList.size(); i++) {
            Tag tag = new Tag(tagsArrayList.get(i));
            tag.layoutColor = Color.parseColor("#7C4DFF");
            tag.layoutColorPress = Color.parseColor("#7C4DFF");
            interestsTagView.addTag(tag);
        }


        // set users interest levels textview
        Resources res = getResources();
        interestsArray = res.getStringArray(R.array.interests);
        Map<String,Integer> interestsMap = currentUserProfile.getInterests();

        if(interestsMap.get(interestsArray[0]) != 0) {
            sportsProgressBar.setProgress(interestsMap.get(interestsArray[0]));
        }
        if(interestsMap.get(interestsArray[1]) != 0) {
            musicProgressBar.setProgress(interestsMap.get(interestsArray[1]));
        }
        if(interestsMap.get(interestsArray[2]) != 0) {
            gamesProgressBar.setProgress(interestsMap.get(interestsArray[2]));
        }
        if(interestsMap.get(interestsArray[3]) != 0) {
            moviesProgressBar.setProgress(interestsMap.get(interestsArray[3]));
        }
        if(interestsMap.get(interestsArray[4]) != 0) {
            technologyProgressBar.setProgress(interestsMap.get(interestsArray[4]));
        }
        if(interestsMap.get(interestsArray[5]) != 0) {
            scienceProgressBar.setProgress(interestsMap.get(interestsArray[5]));
        }
        if(interestsMap.get(interestsArray[6]) != 0) {
            politicsProgressBar.setProgress(interestsMap.get(interestsArray[6]));
        }
        if(interestsMap.get(interestsArray[7]) != 0) {
            historyProgressBar.setProgress(interestsMap.get(interestsArray[7]));
        }
        if(interestsMap.get(interestsArray[8]) != 0) {
            engineeringProgressBar.setProgress(interestsMap.get(interestsArray[8]));
        }
        if(interestsMap.get(interestsArray[9]) != 0) {
            economicsProgressBar.setProgress(interestsMap.get(interestsArray[9]));
        }
        if(interestsMap.get(interestsArray[10]) != 0) {
            literatureProgressBar.setProgress(interestsMap.get(interestsArray[10]));
        }
        if(interestsMap.get(interestsArray[11]) != 0) {
            comicsProgressBar.setProgress(interestsMap.get(interestsArray[11]));
        }
        if(interestsMap.get(interestsArray[12]) != 0) {
            religionProgressBar.setProgress(interestsMap.get(interestsArray[12]));
        }
        if(interestsMap.get(interestsArray[13]) != 0) {
            artsProgressBar.setProgress(interestsMap.get(interestsArray[13]));
        }
        if(interestsMap.get(interestsArray[14]) != 0) {
            travelProgressBar.setProgress(interestsMap.get(interestsArray[14]));
        }


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


        // set users gender textview
        usersGender.setText(currentUserProfile.getGender());

    }


}
