package com.oc.liza.mynewsapp.controller.activities;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.utils.UrlManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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

    @Test
    public void clickSwitch_whenConditionsAreOk_thenSaveURL() {
        UrlManager manager = Mockito.mock(UrlManager.class);
        activity.manager = manager;
        when(manager.checkConditions()).thenReturn(true);
        activity.findViewById(R.id.switch_notify).performClick();

        assertTrue(activity.findViewById(R.id.switch_notify).isClickable());
        verify(manager).checkConditions();
        verify(manager).createSearchUrl();
        verify(manager).saveUrl(anyString());
    }

    @Test
    public void clickSwitch_whenConditionsAreNotOk_thenDontSaveURL() {
        UrlManager manager = Mockito.mock(UrlManager.class);
        activity.manager = manager;
        when(manager.checkConditions()).thenReturn(false);
        activity.findViewById(R.id.switch_notify).performClick();

        assertTrue(activity.findViewById(R.id.switch_notify).isClickable());
        verify(manager, never()).createSearchUrl();
        verify(manager, never()).saveUrl(anyString());
    }
}