package com.worldnews.amrdeveloper.worldnews.sync;

import android.content.Context;
import android.content.Intent;

import com.worldnews.amrdeveloper.worldnews.activities.MainActivity;
import com.worldnews.amrdeveloper.worldnews.notification.NotificationUtils;

public class ReminderTasks {

    public static final String ACTION_READ_NEWS_NOTIFICATION = "read-this-news";
    //  Add a public static constant called ACTION_DISMISS_NOTIFICATION
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";

    public static final String ACTION_NOTIFICATION_REMIND = "create-notification";

    public static void executeTask(Context context, String action) {
        if (ACTION_READ_NEWS_NOTIFICATION.equals(action)) {
            readThisNews(context);
        } else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        } else if (ACTION_NOTIFICATION_REMIND.equals(action)) {
            alertNewsReminder(context);
        }
    }

    private static void readThisNews(Context context) {
        //Test Action
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        // If the water count was incremented, clear any notifications
        NotificationUtils.clearAllNotifications(context);
    }

    private static void alertNewsReminder(Context context) {
        //Reminder user about new news
        NotificationUtils.remindUserBecauseNewNews(context);
    }
}
