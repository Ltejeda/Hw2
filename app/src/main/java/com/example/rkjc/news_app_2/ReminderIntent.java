package com.example.rkjc.news_app_2;

import android.app.IntentService;
import android.content.Intent;

public class ReminderIntent extends IntentService {
    public ReminderIntent() {
        super("ArticleReminderIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ReminderTask.executeTask(this, action);
    }
}
