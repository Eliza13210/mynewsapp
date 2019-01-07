package com.oc.liza.mynewsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.*;

public class NotificationServiceTest {

    @Mock
    Context context;
    @Mock
    NotificationService service;
    @Mock
    SharedPreferences pref;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test

    public void timerExists() {
        service = new NotificationService(context);
        service.createTimerTask();

        assertTrue(service.timer != null);
        assertTrue(service.task!=null);
    }
}
