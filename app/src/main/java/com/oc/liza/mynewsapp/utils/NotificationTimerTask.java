package com.oc.liza.mynewsapp.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.models.NewsObject;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationTimerTask {

    private static final String CHANNEL_ID = "NOTIFICATION CHANNEL";
    private Context context;
    private Disposable disposable;
    private String url;
    private Timer timer;

    public NotificationTimerTask(Context context) {
        this.context = context;
        timer = new Timer();
        createNotificationChannel();
    }

    public void cancelNotification() {
        assert timer != null;
        timer.cancel();
    }

    public void fetchNews() {

        SharedPreferences sharedPref = context.getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        url = sharedPref.getString("NOTIFY_URL", null);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                disposable = NewsStream.streamFetchNewslist(url).subscribeWith(new DisposableObserver<NewsObject>() {
                    @Override
                    public void onNext(NewsObject news) {
                        if (news.checkIfResult() > 0) {
                            int hits = news.checkIfResult();
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                                    .setContentTitle("Notification")
                                    .setContentText("Il y a " + hits + " nouveaux articles")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                            notificationManager.notify(1, mBuilder.build());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Error observer", "Error " + e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
            }
        };

        timer.schedule(task, 0, 24 * 60 * 60 * 1000);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
