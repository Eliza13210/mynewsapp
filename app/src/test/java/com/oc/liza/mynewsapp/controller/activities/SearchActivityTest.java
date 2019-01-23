package com.oc.liza.mynewsapp.controller.activities;

import android.content.Intent;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.network.UrlManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
public class SearchActivityTest {

    private SearchActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(SearchActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void testActivityExists() {
        assertNotNull(shadowOf(RuntimeEnvironment.application));
        assertNotNull(Robolectric.setupActivity(SearchActivity.class));
    }

    @Test
    public void clickingSearchButton_whenConditionsAreMet_thenStartSearchResultActivity() {
        UrlManager manager = Mockito.mock(UrlManager.class);
        activity.manager = manager;
        when(manager.checkConditions()).thenReturn(true);
        activity.findViewById(R.id.search_button).performClick();

        Intent expectedIntent = new Intent(activity, SearchResultActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void clickingSearchButton_whenConditionsAreNotMet_thenDontStartActivity() {
        UrlManager manager = Mockito.mock(UrlManager.class);
        activity.manager = manager;
        when(manager.checkConditions()).thenReturn(false);
        activity.findViewById(R.id.search_button).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertNull(actualIntent);
    }

}