package com.worldnews.amrdeveloper.worldnews;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.worldnews.amrdeveloper.worldnews.Adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity{


    private ViewPager viewpager;
    private TabLayout sliding_tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = (ViewPager)findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);

        sliding_tabs = (TabLayout)findViewById(R.id.sliding_tabs);
        sliding_tabs.setupWithViewPager(viewpager);


    }
}
