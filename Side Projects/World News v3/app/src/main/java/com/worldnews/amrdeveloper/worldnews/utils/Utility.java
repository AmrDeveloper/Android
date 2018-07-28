package com.worldnews.amrdeveloper.worldnews.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utility {

    //Format The Date
    public static String dateFormat(String date) {
        // This is the time format from guardian JSON "2017-10-29T06:00:20Z"
        // will be changed to 29-10-2017 format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date newDate = format.parse(date);
            format = new SimpleDateFormat("dd-MM-yyyy, h:mm a");
            date = format.format(newDate);
        } catch (ParseException e) {
            Log.e("Formatter", "Problem with parsing the date format");
        }
        return date;
    }

    /**
     * @param context : Current Activity content
     * @param currentNewsUrl : Link to share it
     */
    public static void shareCurrentNews(Context context, String currentNewsUrl){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, currentNewsUrl);
        context.startActivity(Intent.createChooser(share, "Share This News"));
    }
}
