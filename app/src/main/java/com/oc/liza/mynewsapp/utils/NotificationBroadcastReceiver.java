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
import android.widget.Toast;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.models.NewsObject;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private Disposable disposable;
    private String CHANNEL_ID;

    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context,"alarm", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPref = context.getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        String url = sharedPref.getString("NOTIFY_URL", null);
        CHANNEL_ID = sharedPref.getString("CHANNEL_KEY", null);
        disposable = NewsStream.streamFetchNewslist(url).subscribeWith(new DisposableObserver<NewsObject>() {

            @Override
            public void onNext(NewsObject news) {
                int hits = news.checkIfResult();

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
                Log.e("Error observer", "Error " + e);
            }

            @Override
            public void onComplete() {
            }
        });
        Log.e("cast", "Here's my code to execute 15 min");
       // if (disposable != null && !disposable.isDisposed()) disposable.dispose();

    }

    public void setAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificationBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        assert am != null;
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pi);

        Log.e("NotA", "enable notify");
    }

}
