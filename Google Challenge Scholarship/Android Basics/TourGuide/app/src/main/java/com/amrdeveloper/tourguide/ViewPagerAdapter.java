package com.amrdeveloper.tourguide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by AmrDeveloper on 2/24/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabsTitle =  new String[]{"Pharaonic","Religious","Modern","Other"};

    public ViewPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PharaonicFragment();
            case 1:
                return new ReligiousFragment();
            case 2:
                return new ModernFragment();
            default:
                return new OtherStatuesFragment();
        }
    }

    @Override
    public int getCount() {
        return tabsTitle.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabsTitle[position];
    }
}
