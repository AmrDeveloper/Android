package com.worldnews.amrdeveloper.worldnews.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.worldnews.amrdeveloper.worldnews.adapter.ViewPagerAdapter;
import com.worldnews.amrdeveloper.worldnews.R;
import com.worldnews.amrdeveloper.worldnews.fragment.Event;
import com.worldnews.amrdeveloper.worldnews.service.ReminderUtilities;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start Job Services
        jobServiceStateSetup();

        // Find the view pager that will allow the user to swipe between fragments.
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page.
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager.
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs.
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);

        // Connect the tab layout with the view pager.
        tabLayout.setupWithViewPager(viewPager);

        // If we want to change the mode after rotation.
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) viewPager.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        // Gets the width of screen.
        int width = metrics.widthPixels;
        // Gets the Height of screen.
        int height = metrics.heightPixels;

        // Landscape Orientation
        if (width > height) {
            // Change the mode to fixed
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate Settings Menu
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.setting_menu) {
            //Go to Settings Activity
            goToSettingsActivity();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //Do no Thing add can't back to LoginActivity
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.news_country_key)) || key.equals(getString(R.string.news_order_key))) {
            Event.onDataChang();
        }
        if (key.equals(getString(R.string.noti_turn_bass_key))) {
            jobServiceStateSetup();
        }
    }

    private void goToSettingsActivity() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void jobServiceStateSetup() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean notiState = sharedPreferences.getBoolean(getString(R.string.noti_turn_bass_key),
                getResources().getBoolean(R.bool.noti_turn_default));
        if (notiState) {
            ReminderUtilities.scheduleNewsReminder(this);
        } else {
            ReminderUtilities.unScheduleNewsReminder();
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister MainActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
