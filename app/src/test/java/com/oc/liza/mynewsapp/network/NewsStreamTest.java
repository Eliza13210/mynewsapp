package com.oc.liza.mynewsapp.network;

import com.oc.liza.mynewsapp.models.NewsObject;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)

public class NewsStreamTest {

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
        RxAndroidPlugins.reset();
    }

    @Test
    public void fetchTopStoriesTest() {
        //1 - Get the stream
        Observable<NewsObject> observable = NewsStream.streamFetchNewslist("https://api.nytimes.com/svc/topstories/v2/home.json?&api-key=799e9f0e6e264b3a8e21b57f3f05dfd0");
        //2 - Create a new TestObserver
        TestObserver<NewsObject> testObserver = new TestObserver<>();
        //3 - Launch observable
        observable.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        // 4 - Get list of user fetched
        NewsObject news = testObserver.values().get(0);
        List<NewsObject> newsFetched = new ArrayList<>();
        newsFetched.addAll(news.getList());

        assertNotNull(newsFetched);
        assertTrue(news.checkIfResult() != 0);
    }

    @Test
    public void fetchMostPopularStoriesTest() {
        //1 - Get the stream
        Observable<NewsObject> observable = NewsStream.streamFetchNewslist("https://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/7.json?&api-key=799e9f0e6e264b3a8e21b57f3f05dfd0");
        //2 - Create a new TestObserver
        TestObserver<NewsObject> testObserver = new TestObserver<>();
        //3 - Launch observable
        observable.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        // 4 - Get list of user fetched
        NewsObject news = testObserver.values().get(0);
        List<NewsObject> newsFetched = new ArrayList<>();
        newsFetched.addAll(news.getList());

        assertNotNull(newsFetched);
        assertTrue(news.checkIfResult() != 0);
    }

    @Test
    public void fetchSearchNewsTest() {
        //1 - Get the stream
        Observable<NewsObject> observable = NewsStream.streamFetchNewslist("https://api.nytimes.com/svc/search/v2/articlesearch.json?&api-key=799e9f0e6e264b3a8e21b57f3f05dfd0&q=science");
        //2 - Create a new TestObserver
        TestObserver<NewsObject> testObserver = new TestObserver<>();
        //3 - Launch observable
        observable.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue

        // 4 - Get list of user fetched
        NewsObject news = testObserver.values().get(0);
        List<NewsObject> newsFetched = new ArrayList<>();
        newsFetched.addAll(news.getList());

        assertNotNull(newsFetched);
        assertTrue(news.checkIfResult() != 0);
    }
}