package com.oc.liza.mynewsapp.controller.activities;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.widget.CheckBox;
import android.widget.EditText;

import com.oc.liza.mynewsapp.utils.UrlManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.annotations.NonNull;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UrlManagerTest {
    @Mock
    EditText search_query;
    @Mock
    EditText begin;
    @Mock
    EditText end;
    @Mock
    CheckBox cb1;
    @Mock
    CheckBox cb2;
    @Mock
    CheckBox cb3;
    @Mock
    Context context;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUrl_WhenSearchQueryAndDates_ThenReturnUrl() {
        UrlManager manager = new UrlManager(context);
        Mockito.when(search_query.getText()).thenReturn(new MockEditable("christmas"));
        Mockito.when(begin.getText()).thenReturn(new MockEditable("20010101"));
        Mockito.when(end.getText()).thenReturn(new MockEditable("20121212"));

        Mockito.when(cb3.getText()).thenReturn(new MockEditable("Science"));
        Mockito.when(cb1.isChecked()).thenReturn(false);
        Mockito.when(cb2.isChecked()).thenReturn(false);
        Mockito.when(cb3.isChecked()).thenReturn(true);

        manager.getUserInput(search_query, begin, end, cb1, cb2, cb3);
        manager.createSearchUrl();
        String url = manager.getUrl();
        assertEquals("http://api.nytimes.com/svc/search/v2/articlesearch.json?&" +
                "api-key=799e9f0e6e264b3a8e21b57f3f05dfd0&q=Science%20christmas&begin_date=20010101" +
                "&end_date=20121212&sort=newest", url);
    }

    /**
     * A mocked Editable that allow us to mock user input
     */
    class MockEditable implements Editable {

        private String str;

        public MockEditable(String str) {
            this.str = str;
        }

        @Override
        @NonNull
        public String toString() {
            return str;
        }

        @Override
        public int length() {
            return str.length();
        }

        @Override
        public char charAt(int i) {
            return str.charAt(i);
        }

        @Override
        public CharSequence subSequence(int i, int i1) {
            return str.subSequence(i, i1);
        }

        @Override
        public Editable replace(int i, int i1, CharSequence charSequence, int i2, int i3) {
            return this;
        }

        @Override
        public Editable replace(int i, int i1, CharSequence charSequence) {
            return this;
        }

        @Override
        public Editable insert(int i, CharSequence charSequence, int i1, int i2) {
            return this;
        }

        @Override
        public Editable insert(int i, CharSequence charSequence) {
            return this;
        }

        @Override
        public Editable delete(int i, int i1) {
            return this;
        }

        @Override
        public Editable append(CharSequence charSequence) {
            return this;
        }

        @Override
        public Editable append(CharSequence charSequence, int i, int i1) {
            return this;
        }

        @Override
        public Editable append(char c) {
            return this;
        }

        @Override
        public void clear() {
        }

        @Override
        public void clearSpans() {
        }

        @Override
        public void setFilters(InputFilter[] inputFilters) {
        }

        @Override
        public InputFilter[] getFilters() {
            return new InputFilter[0];
        }

        @Override
        public void getChars(int i, int i1, char[] chars, int i2) {
        }

        @Override
        public void setSpan(Object o, int i, int i1, int i2) {
        }

        @Override
        public void removeSpan(Object o) {
        }

        @Override
        public <T> T[] getSpans(int i, int i1, Class<T> aClass) {
            return null;
        }

        @Override
        public int getSpanStart(Object o) {
            return 0;
        }

        @Override
        public int getSpanEnd(Object o) {
            return 0;
        }

        @Override
        public int getSpanFlags(Object o) {
            return 0;
        }

        @Override
        public int nextSpanTransition(int i, int i1, Class aClass) {
            return 0;
        }
    }
}
