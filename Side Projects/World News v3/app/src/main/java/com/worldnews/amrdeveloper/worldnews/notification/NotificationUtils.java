package com.worldnews.amrdeveloper.worldnews.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.NotificationCompat.Action;

import com.worldnews.amrdeveloper.worldnews.R;
import com.worldnews.amrdeveloper.worldnews.activities.MainActivity;
import com.worldnews.amrdeveloper.worldnews.service.NewNewsIntentService;
import com.worldnews.amrdeveloper.worldnews.service.ReminderTasks;


public class NotificationUtils {

    /*
     * This notification ID can be used to access our notification after we've displayed it. This
     * can be handy when we need to cancel the notification, or perhaps update it. This number is
     * arbitrary and can be set to whatever you like. 1138 is in no way significant.
     */
    private static final int NEW_NEWS_NOTIFICATION_ID = 1138;
    /**
     * This pending intent id is used to uniquely reference the pending intent
     */
    private static final int NEW_NEWS_PENDING_INTENT_ID = 3417;
    /**
     * This notification channel id is used to link notifications to this channel
     */
    private static final String NEW_NEWS_NOTIFICATION_CHANNEL_ID = "news_notification_channel";

    private static final int ACTION_READ_PENDING_INTENT_ID = 1;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 14;

    // This method will create a notification for charging. It might be helpful
    // to take a look at this guide to see an example of what the code in this method will look like:
    // https://developer.android.com/training/notify-user/build-notification.html
    public static void remindUserBecauseNewNews(Context context) {
        // Get the NotificationManager using context.getSystemService
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        //  Create a notification channel for Android O devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NEW_NEWS_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        // In the remindUser method use NotificationCompat.Builder to create a notification
        // that:
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NEW_NEWS_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.red))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.app_name)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(readNewsAction(context))
                .addAction(ignoreReadAction(context))
                .setAutoCancel(true);

        // CIf the build version is greater than JELLY_BEAN and lower than OREO,
        // set the notification's priority to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        //Trigger the notification by calling notify on the NotificationManager.
        // Pass in a unique ID of your choosing for the notification and notificationBuilder.build()
        notificationManager.notify(NEW_NEWS_NOTIFICATION_ID, notificationBuilder.build());
    }

    // Create a helper method called contentIntent with a single parameter for a Context. It
    // should return a PendingIntent. This method will create the pending intent which will trigger when
    // the notification is pressed. This pending intent should open up the MainActivity.
    private static PendingIntent contentIntent(Context context) {
        //Create an intent that opens up the MainActivity
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        // Create a PendingIntent using getActivity that:
        // - Take the context passed in as a parameter
        // - Takes an unique integer ID for the pending intent (you can create a constant for
        //   this integer above
        // - Takes the intent to open the MainActivity you just created; this is what is triggered
        //   when the notification is triggered
        // - Has the flag FLAG_UPDATE_CURRENT, so that if the intent is created again, keep the
        // intent but update the data
        return PendingIntent.getActivity(
                context,
                NEW_NEWS_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // Add a static method called drinkWaterAction
    private static Action readNewsAction(Context context) {
        // Create an Intent to launch WaterReminderIntentService
        Intent incrementWaterCountIntent = new Intent(context, NewNewsIntentService.class);
        // Set the action of the intent to designate you want to increment the water count
        incrementWaterCountIntent.setAction(ReminderTasks.ACTION_READ_NEWS_NOTIFICATION);
        // Create a PendingIntent from the intent to launch WaterReminderIntentService
        PendingIntent incrementWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_READ_PENDING_INTENT_ID,
                incrementWaterCountIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        // Create an Action for the user to tell us they've had a glass of water
        Action readNewsAction = new Action(R.drawable.read_icon,
                "Read",
                incrementWaterPendingIntent);
        //Return the action
        return readNewsAction;
    }

    // Add a static method called ignoreReminderAction
    private static Action ignoreReadAction(Context context) {
        //Create an Intent to launch WaterReminderIntentService
        Intent ignoreReminderIntent = new Intent(context, NewNewsIntentService.class);
        //Set the action of the intent to designate you want to dismiss the notification
        ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);
        // Create a PendingIntent from the intent to launch NewNewsIntentService
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // Create an Action for the user to ignore the notification (and dismiss it)
        Action ignoreReminderAction = new Action(R.drawable.cancel_icon,
                "No, thanks.",
                ignoreReminderPendingIntent);
        //Return the action
        return ignoreReminderAction;
    }


    // returns a Bitmap. This method is necessary to decode a bitmap needed for the notification.
    private static Bitmap largeIcon(Context context) {
        //  Get a Resources object from the context.
        Resources res = context.getResources();
        // resources object and R.drawable.ic_local_drink_black_24px
        return BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background);
    }

    //Create a method to clear all notifications
    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
