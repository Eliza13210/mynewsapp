package com.oc.liza.mynewsapp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationAutoStart extends BroadcastReceiver {

    NotificationBroadcastReceiver alarm=new NotificationBroadcastReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()))
           // if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            alarm.setAlarm(context);
        }
    }
}
