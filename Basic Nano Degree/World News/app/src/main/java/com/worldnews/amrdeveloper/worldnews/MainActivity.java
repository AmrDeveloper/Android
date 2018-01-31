package com.worldnews.amrdeveloper.worldnews;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.worldnews.amrdeveloper.worldnews.Adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity{

    //ViewPager For Show four News Fragment
    private ViewPager viewpager;
    //TabLayout For Switch Between Fragments
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = (ViewPager)findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);

        tabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewpager);


    }


    //get height and width and switch TabLayout Mode Between Fixed and Scrollable
    private void setTabLayoutMode(){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager window = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        window.getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        //landscape
        if(width > height){
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        //portrait
        else
        {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }
}
