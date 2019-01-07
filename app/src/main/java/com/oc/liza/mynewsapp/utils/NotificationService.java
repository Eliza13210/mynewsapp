package com.oc.liza.mynewsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.models.NewsObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationService {
    private String CHANNEL_ID;
    private Context context;
    private Disposable disposable;
    private String url;
    protected Timer timer;
    protected TimerTask task;

    public NotificationService(Context context) {
        this.context = context;
        timer = new Timer();
    }

    public void cancelNotification() {
        if (timer != null)
            timer.cancel();
    }

    public void createTimerTask() {
        task = new TimerTask() {
            @Override
            public void run() {
                fetchNews();
            }
        };
        timer.scheduleAtFixedRate(task, 0, TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    }

    private void fetchNews() {

        SharedPreferences sharedPref = context.getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        url = sharedPref.getString("NOTIFY_URL", null);
        CHANNEL_ID = sharedPref.getString("CHANNEL_KEY", null);
        disposable = NewsStream.streamFetchNewslist(url).subscribeWith(new DisposableObserver<NewsObject>() {
            @Override
            public void onNext(NewsObject news) {
                // if (news.checkIfResult() > 0) {
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
    }
}
