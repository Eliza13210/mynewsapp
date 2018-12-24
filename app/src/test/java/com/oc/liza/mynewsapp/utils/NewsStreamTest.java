package com.oc.liza.mynewsapp.utils;


import com.oc.liza.mynewsapp.models.NewsObject;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.*;

public class NewsStreamTest {

    //Override the default AndroidSchedulers.mainThread() Scheduler since it can't be accessed from test

    @Before
    public void setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) {
                return Schedulers.trampoline();
            }
        });
    }

    @AfterClass
    public static void tearDownClass() {

        // Not strictly necessary because we can't reset the value set by setInitMainThreadSchedulerHandler,
        // but it doesn't hurt to clean up anyway.
        RxAndroidPlugins.reset();
    }


    @Test
    public void streamFetchNewslist() {


        NewsStream newsStream = new NewsStream();
        //1 - Get the stream
        Observable<NewsObject> observable = newsStream.streamFetchNewslist("http://");
        //2 - Create a new TestObserver
        TestObserver<NewsObject> testObserver = new TestObserver<>();
        //3 - Launch observable
        observable.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue


    }
}