package com.oc.liza.mynewsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;


import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

public class NotificationServiceTest {

    @Mock
    Context context;
    @Mock
    NotificationService service;
    @Mock
    SharedPreferences pref;

    @Mock ScheduledExecutorService scheduledExecutorService;
    @Mock ScheduledFuture<?> t;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test

    public void schedulerExists() {
        service = new NotificationService(context);
        service.createTimerTask();

        assertTrue(service.scheduler != null);
         }

         @Test
    public void scheduled(){

             Mockito.doReturn(t).when(scheduledExecutorService).scheduleAtFixedRate(
                     any(Runnable.class), anyLong(), anyLong(), any(TimeUnit.class));
         }
}
