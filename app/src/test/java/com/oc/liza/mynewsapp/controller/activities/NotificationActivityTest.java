package com.oc.liza.mynewsapp.controller.activities;

import android.widget.Switch;

import com.oc.liza.mynewsapp.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)

public class NotificationActivityTest {

    private NotificationActivity activity;
    private Switch switchNotify;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(NotificationActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void switchShouldBeClickable() {
        activity.findViewById(R.id.switch_notify).performClick();

        assertTrue(activity.findViewById(R.id.switch_notify).isClickable());

    }

}