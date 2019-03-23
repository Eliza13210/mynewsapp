package com.oc.liza.mynewsapp.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.models.NewsObject;
import com.oc.liza.mynewsapp.network.NewsStream;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationJob extends Job {

    private Disposable disposable;
    public static final String TAG = "show_notification_job_tag";
    private String CHANNEL_ID;
    private int hits;
    private String message;

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        checkHits();
        return Result.SUCCESS;
    }

    private void checkHits() {

        //Get the saved URL from Notification Activity
        SharedPreferences sharedPref = getContext().getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        String url = sharedPref.getString("NOTIFY_URL", null);
        CHANNEL_ID = sharedPref.getString("CHANNEL_KEY", null);

        //Do the API request
        disposable = NewsStream.streamFetchNewslist(url).subscribeWith(new DisposableObserver<NewsObject>() {

            @Override
            public void onNext(NewsObject news) {
                //Check how many hits
                hits = news.checkIfResult();
                createNotification(hits);
            }

            @Override
            public void onError(Throwable e) {
                //Create notification with error message
                hits = -1;
                createNotification(hits);
                Log.e("Error api request", "Error " + e);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void createNotification(int hits) {

        //Show correct message in the notification depending on the result
        if (hits == -1) {
            message = "Error";
        } else if (hits < 2) {
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
                .setPeriodic(TimeUnit.DAYS.toMillis(1), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }
}
