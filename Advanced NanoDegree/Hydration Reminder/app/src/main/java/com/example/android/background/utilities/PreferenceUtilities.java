/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.background.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * This class contains utility methods which update water and charging counts in SharedPreferences
 */
public final class PreferenceUtilities {

    public static final String KEY_WATER_COUNT = "water-count";
    public static final String KEY_CHARGING_REMINDER_COUNT = "charging-reminder-count";

    //ًWater counter default value
    private static final int DEFAULT_COUNT = 0;

    /**
     * @param context
     * @param glassesOfWater : the counter of water glasses save it into default SharedPreferences
     *
     */
    synchronized private static void setWaterCount(Context context, int glassesOfWater) {
        //Get The Default SharedPreferences object
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        //Put Glasses of water value
        editor.putInt(KEY_WATER_COUNT, glassesOfWater);
        editor.apply();
    }

    /**
     * increment water counter by one
     * @param context
     */
    synchronized public static void incrementWaterCount(Context context) {
        //Get Current water counter from SharedPreferences
        int waterCount = PreferenceUtilities.getWaterCount(context);
        //Add one to current value and save it
        PreferenceUtilities.setWaterCount(context, ++waterCount);
    }

    /**
     * @param context
     * @return glasses of water counter value from default SharedPreferences
     */
    public static int getWaterCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int glassesOfWater = prefs.getInt(KEY_WATER_COUNT, DEFAULT_COUNT);
        return glassesOfWater;
    }

    synchronized public static void incrementChargingReminderCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int chargingReminders = prefs.getInt(KEY_CHARGING_REMINDER_COUNT, DEFAULT_COUNT);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_CHARGING_REMINDER_COUNT, ++chargingReminders);
        editor.apply();
    }

    public static int getChargingReminderCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int chargingReminders = prefs.getInt(KEY_CHARGING_REMINDER_COUNT, DEFAULT_COUNT);
        return chargingReminders;
    }
}