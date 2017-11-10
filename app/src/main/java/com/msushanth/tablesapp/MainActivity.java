package com.msushanth.tablesapp;


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

import com.msushanth.tablesapp.PresentationLayer.FormClasses.Chat.ChatFormXML;
import com.msushanth.tablesapp.PresentationLayer.FormClasses.Search.SearchFormXML;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar, which contains the navigation icon, tabs, and app name
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        // Create the navigation drawer
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
        adapter.addFragment(new ChatFormXML(), "Chat");
        adapter.addFragment(new SearchFormXML(), "Search");
        viewPager.setAdapter(adapter);
    }


    // Create the tabs and select the "Search" tab to be displayed at start up
    private void createTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
    }


    // Add a listener for the items in the navigation menu, so we can select what happens when each item is clicked.
    private void createNavigationDrawerListener() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                System.out.println("@@##$$##@@: Position clicked: " + item.toString() + '\t' + item.getItemId());
                return false;
            }
        });
    }

}

