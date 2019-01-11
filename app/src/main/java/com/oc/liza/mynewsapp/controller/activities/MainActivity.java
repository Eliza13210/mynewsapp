package com.oc.liza.mynewsapp.controller.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.utils.MyFragmentPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String CHANNEL_ID = "NOTIFICATION CHANNEL";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.activity_main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.activity_main_nav_view)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initMain();
        this.configureDrawerLayout();
        this.configureNavigationView();
        createNotificationChannel();
    }

    private void initMain() {
        //Initiate toolbar
        setSupportActionBar(mToolbar);

        // Set up the ViewPager with the sections adapter to navigate between the tabs
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    // Configure Drawer Layout
    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        //  Handle back click to close menu
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Configure NavigationView
    private void configureNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        startActivity(item);
        this.mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button
        startActivity(item);
        return true;
    }

    /**
     * Handle user click in drawer menu and action bar menu
     *
     * @param item the user clicked on
     */
    private void startActivity(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.search:
                //User chose the "Search" item
                Intent search = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(search);
                break;
            case R.id.action_notifications:
                // User chose the "Notification" item
                Intent notification = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(notification);
                break;
            case R.id.action_help:
                // User chose the "Help" action
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
                break;
            case R.id.action_about:
                // User chose the "About" action
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
            case R.id.action_science:
                // User chose the "Science" tab
                mViewPager.setCurrentItem(2);
                break;
            case R.id.action_health:
                // User chose the "Health" tab
                mViewPager.setCurrentItem(3);
                break;
            case R.id.action_movies:
                // User chose the "Movies" tab
                mViewPager.setCurrentItem(4);
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                super.onOptionsItemSelected(item);
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
        //Save the Channel Id in shared preferences
        SharedPreferences preferences = getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("CHANNEL_KEY", CHANNEL_ID).apply();

    }
}
