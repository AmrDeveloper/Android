package com.worldnews.amrdeveloper.worldnews.fragment;

public class Event {

    static boolean isNewsDataChanged = false;
    static boolean isTechDataChanged = false;
    static boolean isScienceDataChanged = false;
    static boolean isSportDataChanged = false;

    /*
     * Make all booleans true, that mean every fragment have new settings preferences
     */
    public static void onDataChange() {
        isNewsDataChanged = true;
        isTechDataChanged = true;
        isScienceDataChanged = true;
        isSportDataChanged = true;
    }
}
