package com.marwa.eltayeb.tweetme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class UserUtils {

    private Context context;
    private static final String PREFERENCES = "information";

    public UserUtils(Context context){
        this.context = context;
    }

    public void userSaveInfo() {
        if (isUserLoggedIn())
        {
            Intent intent = new Intent(context,MainActivity.class);
            //TODO : send username to MainActivity
            context.startActivity(intent);
        }
        else{
            //TODO : Save username
            setUserLoggedIn();
            //TODO : send username to MainActivity
            //TODO : Open Main Activity
        }
    }

    /**
     * Make key Value in SharedPreferences is True
     */
    private void setUserLoggedIn() {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("key", true);
        editor.apply();
    }

    /**
     * return true if it first time to insert
     */
    private boolean isUserLoggedIn() {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean("key", false);
    }
}
