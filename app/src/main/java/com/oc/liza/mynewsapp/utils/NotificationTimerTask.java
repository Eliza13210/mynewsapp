package com.oc.liza.mynewsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.oc.liza.mynewsapp.models.NewsObject;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationTimerTask {
    private Context context;
    private Disposable disposable;
    private String url;

    public NotificationTimerTask(Context context) {
        this.context = context;
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
                        if (news.checkIfResult()==0) {
                            Toast.makeText(context, "Il y a un nouvel article", Toast.LENGTH_LONG).show();
                        } if (news.checkIfResult()>0){
                            int hits=news.checkIfResult();
                            Toast.makeText(context,"Il y a " + hits + " nouveaux articles", Toast.LENGTH_LONG).show();
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
        Timer timer=new Timer();
        timer.schedule(task,0,24*60*60*1000);
    }
}
