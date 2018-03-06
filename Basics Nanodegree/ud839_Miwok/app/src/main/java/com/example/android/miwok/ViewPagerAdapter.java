package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by AmrDeveloper on 11/17/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "Numbers", "Colors", "Family","Phrases"};


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //switch to choose fragment
        if (position == 0)
        {
            return new NumberFragment();
        }
        else if (position == 1)
        {
            return new ColorsFragment();
        }
        else if (position == 2)
        {
            return new FamiltyFragment();
        }
        else if (position == 3)
        {
            return new PhrasesFragment();
        }
        //The Default
        else
        {
            return new NumberFragment();
        }
    }

    @Override
    public int getCount() {
        //Three Pager
        //number , familty , phrases , Colors
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
