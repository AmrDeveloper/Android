package com.worldnews.amrdeveloper.worldnews;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.worldnews.amrdeveloper.worldnews.Adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {

    //ViewPager For Show four News Fragment
    private ViewPager viewpager;
    //TabLayout For Switch Between Fragments
    private TabLayout tabLayout;
    //TextView to show no internet Connection Message
    private TextView noConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Views
        viewpager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.sliding_tabs);
        noConnection = findViewById(R.id.noConnection);

        //Method for Tab Layout Setting Mode
        setTabLayoutMode();

    }

    //get height and width and switch TabLayout Mode Between Fixed and Scrollable
    private void setTabLayoutMode() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager window = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        window.getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        //landscape
        if (width > height) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        //portrait
        else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    //Check Internet Connection
    private boolean checkInternetConnection() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(checkInternetConnection()){
            viewpager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            noConnection.setVisibility(View.INVISIBLE);

            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewpager.setAdapter(adapter);

            tabLayout.setupWithViewPager(viewpager);
            //Simple Method to Change Mode
            setTabLayoutMode();
        }
        else{
            viewpager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            noConnection.setVisibility(View.VISIBLE);
        }
    }
}
