package com.closetshare.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity implements ActionBar.TabListener {

    public static final String CLOSETSHARE_SHARED_PREFS = "CLOSETSHARE_SHARED_PREFS";

    public static final String NOT_LOGGED_IN_PREFS_KEY = "NOT_LOGGED_IN_PREFS_KEY";

    public static final String USERID_PREFS_KEY = "USERID_PREFS_KEY";

    public static final String USERNAME_PREFS_KEY = "USERNAME_PREFS_KEY";

    private static final int INTRO_ACTIVITY_REQUEST_CODE = 1;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    private boolean notLoggedIn = true;

    private int mUserId;

    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        // The ViewPager is responsible for changing the fragments based on the user swiping
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }

        // check to see if user is logged in, if not prompt login
        checkForPreferences();

        // Shows logged in username
        Toast.makeText(this, mUsername, Toast.LENGTH_SHORT).show();
    }

    // credit: https://github.com/GoogleCloudPlatform/solutions-mobile-backend-starter-android-client
    private void checkForPreferences() {
        SharedPreferences settings = getSharedPreferences(CLOSETSHARE_SHARED_PREFS,
                Context.MODE_PRIVATE);

        boolean notLoggedIn = true;

        if (settings != null) {
            notLoggedIn = settings.getBoolean(NOT_LOGGED_IN_PREFS_KEY, true) && this.notLoggedIn;
            mUserId = settings.getInt(USERID_PREFS_KEY, 0);
            mUsername = settings.getString(USERNAME_PREFS_KEY, "null");
        }

        if (notLoggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, INTRO_ACTIVITY_REQUEST_CODE);
        } else {
            setTitle(mUsername);
        }
    }

    /**
     * Override Activity lifecycle method.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result codes
        if (requestCode == INTRO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "CANCELED", Toast.LENGTH_SHORT).show();
                finish();   // finish main activity if user cancelled login
            } else {
                checkForPreferences();
            }
        }

        // call super method to ensure unhandled result codes are handled
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Iconify the widget by default

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreferences settings = getSharedPreferences(CLOSETSHARE_SHARED_PREFS,
                Context.MODE_PRIVATE);

        if (settings != null) {
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            editor.commit();
            checkForPreferences();
        }
    }

    // Monitor tab changes
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected,
        // switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int NUM_PAGES = 3;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Returns a Fragment based on selected tab position.
         *
         * @param position of the selected tab.
         * @return Fragment based on position.
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentFeed();
                case 1:
                    return new FragmentExplore();
                case 2:
                    return new FragmentCloset();
                default:
                    return null;    // something bad happened
            }
        }

        /**
         * Gets the count of total fragment pages.
         *
         * @return the total number of fragment pages.
         */
        @Override
        public int getCount() {
            // Show 3 total pages.
            return NUM_PAGES;
        }

        /**
         * Gets the title of the selected tab.
         *
         * @param position of the selected tab.
         * @return the title of the selected tab.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }
}
