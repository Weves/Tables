package com.msushanth.tablesapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cunoraz.tagview.Tag;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.PresentationLayer.ActionClasses.Profile.EditPersonalProfileAction;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Account.LogInForm;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Account.LogOutForm;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Account.PasswordRecoveryForm;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat.ChatFormXML;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Invitation.ProfileViewer;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Profile.EditPersonalProfileForm;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Search.SearchFormXML;

import java.util.List;
import java.util.Map;


/*
 *
 *
 *
 */


public class MainActivity extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView navigationView;


    FirebaseAuth firebaseAuth;
    DatabaseReference dbReference;
    FirebaseUser fireBaseUser;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        fireBaseUser = firebaseAuth.getCurrentUser();

        // Set up the toolbar, which contains the navigation icon, tabs, and app name
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        // Create the navigation drawer
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Setup the viewpager with the sections adapter. (The things that get displayed in each tab)
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        setupViewPager(mViewPager);
        createTabs();
        createNavigationDrawerListener();

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This is the current users profile
                currentUser = dataSnapshot.child(fireBaseUser.getUid()).getValue(User.class);

                TextView usernameNavigationDrawer = navigationView.findViewById(R.id.usernameNavigationDrawer);
                usernameNavigationDrawer.setText(currentUser.getFirst_name() + " " + currentUser.getLast_name());

                TextView emailNavigationDrawer = navigationView.findViewById(R.id.emailNavigationDrawer);
                emailNavigationDrawer.setText(fireBaseUser.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }


    // To be able to open and close navigation bar using the buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Define what is displayed in the tabs
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchFormXML(), "Search");
        adapter.addFragment(new ChatFormXML(), "Chat");
        viewPager.setAdapter(adapter);
    }


    // Create the tabs and select the "Search" tab to be displayed at start up
    private void createTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();
    }


    // Add a listener for the items in the navigation menu, so we can select what happens when each item is clicked.
    private void createNavigationDrawerListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                System.out.println("@@##$$##@@: Position clicked: " + item.toString() + '\t' + item.getItemId());

                if(item.toString().equals("My Account")) {
                    dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This is the current users profile
                            System.out.println("##$$%%$$##: My Account clicked");
                            User currentUserProfile = dataSnapshot.child(fireBaseUser.getUid()).getValue(User.class);
                            Intent selectMatchedUsersIntent = new Intent(MainActivity.this, ProfileViewer.class);
                            selectMatchedUsersIntent.putExtra("matchedUsersID", currentUserProfile.getIdForFirebase());
                            startActivity(selectMatchedUsersIntent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                }
                else if(item.toString().equals("Edit Profile")) {
                    dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            System.out.println("##$$%%$$##: Edit Profile Clicked");



                            User currentUserProfile = dataSnapshot.child(fireBaseUser.getUid()).getValue(User.class);
                            Intent selectMatchedUsersIntent = new Intent(MainActivity.this, EditPersonalProfileForm.class);
                            selectMatchedUsersIntent.putExtra("matchedUsersID", currentUserProfile.getIdForFirebase());
                            startActivity(selectMatchedUsersIntent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                }
                else if(item.toString().equals("Availability")) {
                    System.out.println("##$$%%$$##: Availability clicked");
                }
                else if(item.toString().equals("Log Out")) {
                    LogOutForm logout = new LogOutForm();
                    logout.logout();

                    // Send user back to login page when he clicks "Log Out"
                    Intent logInIntent = new Intent(MainActivity.this, LogInForm.class);
                    startActivity(logInIntent);
                    finish();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}

