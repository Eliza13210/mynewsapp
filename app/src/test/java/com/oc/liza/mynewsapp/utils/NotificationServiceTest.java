package com.oc.liza.mynewsapp.utils;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class NotificationServiceTest {
    boolean isTaskCompleted = false;

    @Test
    public void cancelNotification() {
    }

    @Test
    public void fetchNews() throws InterruptedException {
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(isTaskCompleted){
                    isTaskCompleted=false;
                } else{
                    isTaskCompleted=true;
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask,0,
                10000);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(isTaskCompleted);
        Thread.sleep(10000);
        assertFalse(isTaskCompleted);

        timer.cancel();
    }
}
