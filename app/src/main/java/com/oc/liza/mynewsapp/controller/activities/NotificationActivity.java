package com.oc.liza.mynewsapp.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.utils.NotificationTimerTask;
import com.oc.liza.mynewsapp.utils.UrlManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationActivity extends AppCompatActivity {

    private UrlManager manager;
    private NotificationTimerTask notify;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.query)
    EditText query;
    @BindView(R.id.switch_notify)
    Switch switchNotify;
    @BindView(R.id.cbHealth)
    CheckBox cbHealth;
    @BindView(R.id.cbMovies)
    CheckBox cbMovies;
    @BindView(R.id.cbScience)
    CheckBox cbScience;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        initNotification();
    }

    private void initNotification() {
        manager = new UrlManager(this);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        query.setSelection(0);
        Objects.requireNonNull(this.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        switchNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveNotifyUrl();
                    enableNotify();
                    switchNotify.isChecked();
                } else {
                    notify.cancelNotification();
                    Toast toast = Toast.makeText(NotificationActivity.this, "Notifaction desactivée", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private void saveNotifyUrl() {
        manager.getUserInput(query, null, null, cbHealth, cbMovies, cbScience);
        manager.createSearchUrl();
        manager.saveUrl("NOTIFY_URL");
        Log.e("Notify", manager.getUrl());
    }

    private void enableNotify() {
        Context context = getApplicationContext();
        notify = new NotificationTimerTask(context);
        notify.fetchNews();
    }
}
