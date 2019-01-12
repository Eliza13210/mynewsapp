package com.oc.liza.mynewsapp.controller.activities;

import android.content.Context;
import android.content.Intent;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.utils.NotificationService;
import com.oc.liza.mynewsapp.utils.UrlManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)

public class NotificationActivityTest {

    private NotificationActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(NotificationActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);

    }

  /**  @Test
    public void clickSwitch_whenConditionsAreOk_thenCreateNotificationService() {
        UrlManager manager = Mockito.mock(UrlManager.class);
        activity.manager = manager;
        when(manager.checkConditions()).thenReturn(true);
        activity.findViewById(R.id.switch_notify).performClick();

        assertTrue(activity.findViewById(R.id.switch_notify).isClickable());
        assertNotNull(activity.intent);
    }

    @Test
    public void clickSwitch_whenConditionsAreNotOk_thenDontCreateNotificationService() {
        UrlManager manager = Mockito.mock(UrlManager.class);
        activity.manager = manager;
        when(manager.checkConditions()).thenReturn(false);
        activity.findViewById(R.id.switch_notify).performClick();

        assertTrue(activity.findViewById(R.id.switch_notify).isClickable());
        assertNull(activity.intent);
    }*/
}