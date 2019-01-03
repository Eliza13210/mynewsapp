package com.oc.liza.mynewsapp.controller.activities;

import android.content.Intent;
import android.view.MenuItem;

import com.oc.liza.mynewsapp.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)

public class MainActivityTest {
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void when_MenuItemSearchClicked_then_StartSearchActivity() {
        shadowOf(activity).clickMenuItem(R.id.search);
        Intent startedIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertNotNull(startedIntent);
    }

    @Test
    public void when_MenuItemNotificationClicked_then_StartNotificationActivity() {
        shadowOf(activity).clickMenuItem(R.id.action_notifications);
        Intent startedIntent = ShadowApplication.getInstance().getNextStartedActivity();
        Intent expectedIntent = new Intent(activity, NotificationActivity.class);
        assertEquals(expectedIntent.getComponent(), startedIntent.getComponent());
    }

    @Test
    public void when_MenuItemHelpClicked_then_StartHelpActivity() {
        shadowOf(activity).clickMenuItem(R.id.action_help);
        Intent startedIntent = ShadowApplication.getInstance().getNextStartedActivity();
        Intent expectedIntent = new Intent(activity, HelpActivity.class);
        assertNotNull(startedIntent);
        assertEquals(expectedIntent.getComponent(), startedIntent.getComponent());

    }

    @Test
    public void when_MenuItemAboutClicked_then_StartAboutActivity() {
        shadowOf(activity).clickMenuItem(R.id.action_about);
        Intent startedIntent = ShadowApplication.getInstance().getNextStartedActivity();
        Intent exoectedIntent = new Intent(activity, AboutActivity.class);
        assertNotNull(startedIntent);
    }
}