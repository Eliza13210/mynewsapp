package com.oc.liza.mynewsapp.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NotificationService extends Service {

    NotificationBroadcastReceiver alarm = new NotificationBroadcastReceiver();
    public void onCreate()
    {
        super.onCreate();
        Log.e("service", "on create service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e("service", "On start command setAlarm");
        alarm.setAlarm(this);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        alarm.setAlarm(this);
        Log.e("service", "on start setAlarm");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
