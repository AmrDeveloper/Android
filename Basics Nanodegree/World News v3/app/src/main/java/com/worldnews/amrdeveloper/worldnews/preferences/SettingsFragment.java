package com.worldnews.amrdeveloper.worldnews.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.worldnews.amrdeveloper.worldnews.R;

/**
 * Created by AmrDeveloper on 3/13/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Register Share Preference Change

        getPreferenceScreen()
                .getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Un Register Share Preference Change
        getPreferenceScreen()
                .getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.news_settings);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();

        //Get The Number Of Preference
        int itemCount = preferenceScreen.getPreferenceCount();
        //Using Summary Method for all Preference
        for(int i = 0 ; i < itemCount ; i++){
            //Get Current Preference
            Preference p = preferenceScreen.getPreference(i);
            if (!(p instanceof CheckBoxPreference)) {
                //Get The Current Value
                String value = sharedPreferences.getString(p.getKey(), "");
                //Set This Value as Summary
                setPreferenceSummary(p, value);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null) {
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }
    }

    /**
     * @param preference The should add value to it
     * @param value to set as summary in preference
     */
    private void setPreferenceSummary(Preference preference, String value) {
        //If This preference Type Is List
        if (preference instanceof ListPreference){
            //Casting This Preference to List
            ListPreference listPreference = (ListPreference) preference;
            //Get The Value Index
            int prefIndex = listPreference.findIndexOfValue(value);
            //If Index is positive or zero
            if (prefIndex >= 0) {
                //Set Current Value As Summary
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
        //If This preference Type Is EditText
        else if (preference instanceof EditTextPreference) {
            //For EditTextPreference set the summary value
            preference.setSummary(value);
        }
    }
}
