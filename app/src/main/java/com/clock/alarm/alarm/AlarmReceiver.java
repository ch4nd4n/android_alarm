package com.clock.alarm.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by ckumar on 16/10/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    final public static String ONE_TIME = "onetime";
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            String message = bundle.getString("alarm_message");
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }

    }
    public void setAlarm(Context context, String message, int duration) {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        intent.putExtra("alarm_message", message);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, duration);

        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() , pi);
    }
}
