package com.worldnews.amrdeveloper.worldnews.fragment;

public class Event {

    static boolean isNewsDataChanged = false;
    static boolean isTechDataChanged = false;
    static boolean isScienceDataChanged = false;
    static boolean isSportDataChanged = false;

    public static void onDataChang() {
        isNewsDataChanged = true;
        isTechDataChanged = true;
        isScienceDataChanged = true;
        isSportDataChanged = true;
    }
}
