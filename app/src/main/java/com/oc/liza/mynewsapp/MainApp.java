package com.oc.liza.mynewsapp;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.oc.liza.mynewsapp.utils.NotificationJobCreator;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new NotificationJobCreator());
    }
}
