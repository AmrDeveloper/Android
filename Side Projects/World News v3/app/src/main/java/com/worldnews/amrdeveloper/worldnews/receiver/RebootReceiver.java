package com.worldnews.amrdeveloper.worldnews.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.worldnews.amrdeveloper.worldnews.R;
import com.worldnews.amrdeveloper.worldnews.service.ReminderUtilities;

public class RebootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                jobServiceStateSetup(context);
            }
        }
    }

    private void jobServiceStateSetup(Context context){
        //Boot Receiver Called
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean notiState = sharedPreferences.getBoolean(context.getString(R.string.noti_turn_bass_key),
                context.getResources().getBoolean(R.bool.noti_turn_default));
        if (notiState) {
            ReminderUtilities.scheduleNewsReminder(context);
        } else {
            ReminderUtilities.unScheduleNewsReminder();
        }
    }
}
