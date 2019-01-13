package com.oc.liza.mynewsapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.models.NewsObject;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private Disposable disposable;
    private String CHANNEL_ID;

    @Override
    public void onReceive(final Context context, Intent intent) {
        //Get the saved URL from Notification Activity
        SharedPreferences sharedPref = context.getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        String url = sharedPref.getString("NOTIFY_URL", null);
        CHANNEL_ID = sharedPref.getString("CHANNEL_KEY", null);

        //Do the API request
        disposable = NewsStream.streamFetchNewslist(url).subscribeWith(new DisposableObserver<NewsObject>() {

            @Override
            public void onNext(NewsObject news) {
                //Check how many hits
                int hits = news.checkIfResult();

                //Build the notification with the information
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notify)
                        .setContentTitle("Notification")
                        .setContentText("Il y a " + hits + " articles")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                notificationManager.notify(1, mBuilder.build());
            }


            @Override
            public void onError(Throwable e) {


                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notify)
                        .setContentTitle("Notification")
                        .setContentText("Error" + e)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                notificationManager.notify(1, mBuilder.build());
                Log.e("Error observer", "Error " + e);
            }

            @Override
            public void onComplete() {
            }
        });
        Log.e("cast", "Here's my code to execute 15 min");
       // if (disposable != null && !disposable.isDisposed()) disposable.dispose();

    }

    /**
     * Set the alarm with the alarm manager to execute once a day
     * @param context from Notification service
     */
    public void setAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificationBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 54321, i, PendingIntent.FLAG_CANCEL_CURRENT);
        assert am != null;
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pi);

        Log.e("broadcast", "enable notify");
    }

}
