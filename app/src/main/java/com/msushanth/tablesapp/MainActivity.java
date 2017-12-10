package com.msushanth.tablesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msushanth.tablesapp.BusinessLogicLayer.ControllerClasses.ProfileController;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Account.LogInView;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Account.LogOutView;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Chat.ChatFormXML;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Invitation.ProfileViewer;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Profile.EditPersonalProfileView;
import com.msushanth.tablesapp.PresentationLayer.ViewClasses.Search.SearchView;


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

    boolean goToChatTab;


    FirebaseAuth firebaseAuth;
    DatabaseReference dbReference;
    FirebaseUser fireBaseUser;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        goToChatTab = intent.getBooleanExtra("goToChatTab", false);

        firebaseAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();
        fireBaseUser = firebaseAuth.getCurrentUser();

        // Set up the toolbar, which contains the navigation icon, tabs, and app name
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        TextView pageTitle = (TextView) mToolbar.findViewById(R.id.pageTitle);
        setSupportActionBar(mToolbar);
        pageTitle.setText("Tables");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        ProfileController profileController = new ProfileController();
        profileController.setFields();
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
    private void setupViewPager(final ViewPager viewPager) {
        final SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchView(), "Search");
        adapter.addFragment(new ChatFormXML(), "Chat");
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {}

            @Override
            public void onPageSelected(int position) {
                if(position ==1 ) {
                    viewPager.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });
    }


    // Create the tabs and select the "Search" tab to be displayed at start up
    private void createTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        if(goToChatTab) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        } else {
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
        }
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
                            Intent editPersonalProfileIntent = new Intent(MainActivity.this, EditPersonalProfileView.class);
                            editPersonalProfileIntent.putExtra("UserID", currentUserProfile.getIdForFirebase());
                            startActivity(editPersonalProfileIntent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                }
                else if(item.toString().equals("Availability")) {
                    System.out.println("##$$%%$$##: Availability clicked");
                }
                else if(item.toString().equals("Log Out")) {
                    LogOutView logout = new LogOutView();
                    logout.logout();

                    // Send user back to login page when he clicks "Log Out"
                    Intent logInIntent = new Intent(MainActivity.this, LogInView.class);
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

