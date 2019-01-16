package com.oc.liza.mynewsapp.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.controller.activities.MainActivity;
import com.oc.liza.mynewsapp.models.NewsObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationJob extends Job {
    static final String TAG = "show_notification_job_tag";
    private String CHANNEL_ID;
    private int hits;

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        PendingIntent pi = PendingIntent.getActivity(getContext(), 0,
                new Intent(getContext(), MainActivity.class), 0);

        Log.e("Job", "on run job ");
        checkHits();
        return Result.SUCCESS;
    }

    private void checkHits() {

        //Get the saved URL from Notification Activity
        SharedPreferences sharedPref = getContext().getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        String url = sharedPref.getString("NOTIFY_URL", null);
        CHANNEL_ID = sharedPref.getString("CHANNEL_KEY", null);

        //Do the API request
        Disposable disposable = NewsStream.streamFetchNewslist(url).subscribeWith(new DisposableObserver<NewsObject>() {

            @Override
            public void onNext(NewsObject news) {
                //Check how many hits
                hits = news.checkIfResult();
                //Create notification
                createNotification(hits);
            }

            @Override
            public void onError(Throwable e) {
                //Create notification with error message
                createNotification(-1);

                Log.e("Error api request", "Error " + e);
            }

            @Override
            public void onComplete() {
            }
        });

        Log.e("cast", "Here's my code to execute 15 min");
        if (disposable != null && !disposable.isDisposed()) disposable.dispose();

    }

    private void createNotification(int result) {
        //Show correct message in the notification depending on the result
        String message;
        if (result == -1) {
            message = "Error";
        } else if (result < 2) {
            message = "Il y a " + hits + " article";
        } else {
            message = "Il y a " + hits + " articles";
        }

        //Build the notification with the information
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notify)
                .setContentTitle("Notification")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(1, mBuilder.build());
    }

    public static void schedulePeriodic() {
        new JobRequest.Builder(NotificationJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .build()
                .schedule();

        Log.e("NotJ", "schedule per");
    }
}
