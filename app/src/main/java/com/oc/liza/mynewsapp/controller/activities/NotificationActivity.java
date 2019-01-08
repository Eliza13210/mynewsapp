package com.oc.liza.mynewsapp.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.utils.NotificationService;
import com.oc.liza.mynewsapp.utils.UrlManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationActivity extends AppCompatActivity {

    protected UrlManager manager;
    protected NotificationService notify;
    private SharedPreferences pref;

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
        pref = getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        isSwitchChecked();
        initNotification();
    }

    private void isSwitchChecked() {
        boolean switchIsChecked = pref.getBoolean("SWITCH_KEY", false);
        if (switchIsChecked) {
            switchNotify.setChecked(true);
            query.setText(pref.getString("QUERY", null));
            cbHealth.setChecked(pref.getBoolean("CB_HEALTH", false));
            cbMovies.setChecked(pref.getBoolean("CB_MOVIES", false));
            cbScience.setChecked(pref.getBoolean("CB_SCIENCE", false));
        }
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
                    manager.getUserInput(query, null, null, cbHealth, cbMovies, cbScience);
                    if (manager.checkConditions()) {
                        saveNotificationUrlAndState();
                        enableNotify();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sélectionnez au moins une catégorie et un mot clé", Toast.LENGTH_SHORT).show();
                        switchNotify.toggle();
                    }

                } else {
                    pref.edit().putBoolean("SWITCH_KEY", false).apply();
                    if (notify != null) {
                        notify.cancelNotification();
                    }
                    Toast toast = Toast.makeText(NotificationActivity.this, "Notifaction desactivée", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private void saveNotificationUrlAndState() {
        // save user input and notification state
        pref.edit().putBoolean("SWITCH_KEY", true).apply();
        pref.edit().putString("QUERY", query.getText().toString()).apply();
        pref.edit().putBoolean("CB_HEALTH", cbHealth.isChecked()).apply();
        pref.edit().putBoolean("CB_SCIENCE", cbScience.isChecked()).apply();
        pref.edit().putBoolean("CB_MOVIES", cbMovies.isChecked()).apply();

        //save search url
        manager.createSearchUrl();
        manager.saveUrl("NOTIFY_URL");
    }

    private void enableNotify() {
        Context context = getApplicationContext();
        notify = new NotificationService(context);
        notify.createTimerTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notify.disposeWhenDestroy();
    }
}
