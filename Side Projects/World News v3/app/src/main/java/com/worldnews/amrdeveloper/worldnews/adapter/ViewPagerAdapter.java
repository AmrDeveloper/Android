package com.worldnews.amrdeveloper.worldnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.worldnews.amrdeveloper.worldnews.fragment.ScienceFragment;
import com.worldnews.amrdeveloper.worldnews.fragment.NewsFragment;
import com.worldnews.amrdeveloper.worldnews.fragment.SportFragment;
import com.worldnews.amrdeveloper.worldnews.fragment.TechFragment;

/**
 * Created by AmrDeveloper on 1/29/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] fragmentsName = {"News", "Tech", "Science", "Sport"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //switch to choose fragment
        if (position == 0)
        {
            return new NewsFragment();
        }
        if (position == 1)
        {
            return new TechFragment();
        }
        if (position == 2)
        {
            return new ScienceFragment();
        }
        if (position == 3)
        {
            return new SportFragment();
        }
        //The Default
        else
        {
            return new NewsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return fragmentsName[position];
    }
}
