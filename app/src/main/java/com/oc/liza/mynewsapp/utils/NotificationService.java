package com.oc.liza.mynewsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.models.NewsObject;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class NotificationService {

    private Disposable disposable;
    private String CHANNEL_ID;
    private final Context context;
    ScheduledExecutorService scheduler;
    Future<?> f;


    public NotificationService(Context context) {
        this.context = context;
    }

    public void cancelNotification() {
        //f.cancel(true);
        if(scheduler!=null)
        scheduler.shutdownNow();

    }

    public void createTimerTask() {

        scheduler =
                Executors.newScheduledThreadPool(1);
             final Runnable runnable = new Runnable() {

                 public void run() {
                     // call service
                     fetchNews();
                 }
            };

         f=scheduler.scheduleAtFixedRate
                (runnable, 0, 10, TimeUnit.MINUTES);

    }

    private void fetchNews() {

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
    }
    public void disposeWhenDestroy() {
        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
    }

}
