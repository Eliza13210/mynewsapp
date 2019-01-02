package com.oc.liza.mynewsapp.utils;


import android.view.View;

import com.oc.liza.mynewsapp.models.News;
import com.oc.liza.mynewsapp.models.NewsItem;
import com.oc.liza.mynewsapp.models.NewsObject;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class NewsStreamTest {
    @Mock
    private NewsStream newsStream;
    private NewsObject mockedNews;
    private NewsService mockedService;

    @Mock
    private View view;

    //Override the default AndroidSchedulers.mainThread() Scheduler since it can't be accessed from test

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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

        //1 - Get the stream
        Observable<NewsObject> observable = newsStream.streamFetchNewslist("https://api.nytimes.com/svc/topstories/v2/home.json?&amp;api-key=799e9f0e6e264b3a8e21b57f3f05dfd0");
        //2 - Create a new TestObserver
        TestObserver<NewsObject> testObserver = new TestObserver<>();
        //3 - Launch observable
        observable.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        List<NewsObject> news = testObserver.values();
        assertNotNull(news);

    }
}