package com.oc.liza.mynewsapp.controller.activities;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.oc.liza.mynewsapp.utils.NotificationJob;
import com.oc.liza.mynewsapp.utils.UrlManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationActivity extends AppCompatActivity {

    protected UrlManager manager;
    private SharedPreferences pref;
    Context context;
    protected Intent intent;
    AlarmManager am;

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

        context = getApplicationContext();
    }

    /**
     * Check if the switch has been activated by the user already and if so, get the last user input so the user can see the
     * search criteria set by the user when activating
     */
    private void isSwitchChecked() {
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

        //the toolbar to navigate back to main
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

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
                        Toast.makeText(getApplicationContext(), "Sélectionnez au moins une catégorie et un mot clé", Toast.LENGTH_SHORT).show();
                        switchNotify.toggle();
                    }
                }
                //If switch unchecked
                else {
                    cancelAlarm();
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
     * Start a service to run a scheduled notification
     */

    private void enableNotification() {

        context = getApplicationContext();


        NotificationJob.schedulePeriodic();
        Log.e("NotA","Notification job schedule");
        /**
         *
         *
         *         intent = new Intent(context, MyIntentService.class);
         *         context.startService(intent);
         *
         *         if (Build.VERSION.SDK_INT >= 21) {
         *
         *          am.setAlarmClock(new AlarmManager.AlarmClockInfo(AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent), pendingIntent);
         *
         *             Log.e("broadcast", "alarm clock Alarm manager");
         *

         Toast toast = Toast.makeText(NotificationActivity.this, "Notifaction par jobscheduler", Toast.LENGTH_SHORT);
         Util.scheduleJob(context);
         Log.e("sdk", "21 and up");
         } else {*/

        Log.e("NotA", "enable Alarm manager");
        setAlarm(context);


    }

    /**
     * Cancel the scheduled alarm for notifications
     */
    private void cancelAlarm() {

        pref.edit().putBoolean("SWITCH_KEY", false).apply();

        context = getApplicationContext();
      //  JobManager.instance().cancel(NotificationJob.TAG);

      /**    if (Build.VERSION.SDK_INT >= 21) {
         JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
         assert jobScheduler != null;
         jobScheduler.cancel(1);

         Log.e("sdk", "cancel 21 and up");
         } else {

              //Stop the AlarmManager
              if (am != null)
                  am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
              Intent i = new Intent(getApplicationContext(), NotificationBroadcastReceiver.class);
              PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 54321, i, 0);
              assert am != null;
              am.cancel(pi);

              Log.e("NotA", "cancel alarm manager");

          }*/
        Toast toast = Toast.makeText(NotificationActivity.this, "Notifaction desactivée", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();

    }

    //Stop the service
    //      context.stopService(i);
    public void setAlarm(Context context) {


        /**Works fine on Marshmallow
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificationBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 54321, i, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        assert am != null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Toast toast = Toast.makeText(NotificationActivity.this, "Notifaction par jobscheduler", Toast.LENGTH_SHORT);
            Util.scheduleJob(context);
            Log.e("notA", "job scheduler 21 and up");
            } else {
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

            Log.e("broadcast", "Lollipop Alarm manager");
        }
*/
    }
}
