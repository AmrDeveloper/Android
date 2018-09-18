package com.worldnews.amrdeveloper.worldnews.service;

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
