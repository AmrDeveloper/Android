package com.worldnews.amrdeveloper.worldnews.sync;

import android.app.IntentService;
import android.content.Intent;

public class NewNewsIntentService extends IntentService {

    public NewNewsIntentService() {
        super("NewNewsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ReminderTasks.executeTask(this, action);
    }
}
