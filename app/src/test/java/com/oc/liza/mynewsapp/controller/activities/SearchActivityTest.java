package com.oc.liza.mynewsapp.controller.activities;

import android.content.Intent;

import com.oc.liza.mynewsapp.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
public class SearchActivityTest {

    private SearchActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(SearchActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void testSomething() throws Exception {
        assertNotNull(shadowOf(RuntimeEnvironment.application));
        assertTrue(Robolectric.setupActivity(SearchActivity.class) != null);
    }

    @Test
    public void clickingSearchButton_shouldStartSearchResultActivity() {
        activity.findViewById(R.id.search_button).performClick();

        Intent expectedIntent = new Intent(activity, SearchResultActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}