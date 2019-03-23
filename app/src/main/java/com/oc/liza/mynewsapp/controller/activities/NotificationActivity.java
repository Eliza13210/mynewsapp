package com.oc.liza.mynewsapp.controller.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.evernote.android.job.JobManager;
import com.oc.liza.mynewsapp.R;
import com.oc.liza.mynewsapp.notification.NotificationJob;
import com.oc.liza.mynewsapp.network.UrlManager;

import java.util.Objects;

import butterknife.BindView;


public class NotificationActivity extends BaseActivity {

    protected UrlManager manager;
    private SharedPreferences pref;

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
        isSwitchChecked();
        initNotification();

    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_notification;
    }

    /**
     * Check if the switch has been activated by the user already and if so, get the last user input so the user can see the
     * search criteria set by the user when activating
     */
    private void isSwitchChecked() {

        pref = getSharedPreferences("MYNEWS_KEY", Context.MODE_PRIVATE);
        boolean switchIsChecked = pref.getBoolean("SWITCH_KEY", false);
        if (switchIsChecked) {
            //set switch to checked
            switchNotify.setChecked(true);
            //set search query
            query.setText(pref.getString("QUERY", null));
            //check the checkboxes that were selected
            cbHealth.setChecked(pref.getBoolean("CB_HEALTH", false));
            cbMovies.setChecked(pref.getBoolean("CB_MOVIES", false));
            cbScience.setChecked(pref.getBoolean("CB_SCIENCE", false));
        }
    }

    /**
     * Set the toolbar to be able to navigate back to Main activity
     * Use URL manager to save the search url with the user input
     * Set listener to switch
     */
    private void initNotification() {
        manager = new UrlManager(this);

        //Open the keyboard automatically
        query.setSelection(0);
        Objects.requireNonNull(this.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //Set listener to switch
        switchNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //get the user input
                    manager.getUserInput(query, null, null, cbHealth, cbMovies, cbScience);

                    //If at least one checkbox is selected and the user has put one search query
                    if (manager.checkConditions()) {
                        saveNotificationUrlAndState();
                        enableNotification();
                    } else {
                        manager.clearInput();
                        Toast.makeText(getApplicationContext(), "Sélectionnez au moins une catégorie et un mot clé", Toast.LENGTH_SHORT).show();
                        switchNotify.toggle();
                    }
                }
                //If switch unchecked
                else {
                    cancelNotification();
                }
            }
        });
    }

    /**
     * Get the user input and url and save in Sharedpreferences
     */
    private void saveNotificationUrlAndState() {
        pref.edit().putBoolean("SWITCH_KEY", true).apply();
        pref.edit().putString("QUERY", query.getText().toString()).apply();
        pref.edit().putBoolean("CB_HEALTH", cbHealth.isChecked()).apply();
        pref.edit().putBoolean("CB_SCIENCE", cbScience.isChecked()).apply();
        pref.edit().putBoolean("CB_MOVIES", cbMovies.isChecked()).apply();

        //save search url
        manager.createSearchUrl();
        manager.saveUrl("NOTIFY_URL");
    }

    /**
     * Use job creator to run a scheduled notification
     */

    private void enableNotification() {
        NotificationJob.schedulePeriodic();
    }

    /**
     * Cancel the scheduled job for notifications
     */
    private void cancelNotification() {
        manager.clearInput();
        pref.edit().putBoolean("SWITCH_KEY", false).apply();

        JobManager.instance().cancelAllForTag((NotificationJob.TAG));

        Toast toast = Toast.makeText(NotificationActivity.this, "Notifaction desactivée", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }
}
