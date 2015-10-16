package com.clock.alarm.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private List timeList;
    private String fileName = "alarms.ser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void setAlarm(View view) throws IOException {
        AlarmReceiver alarm;
        TextView txtDuration = (TextView)findViewById(R.id.txtTime);
        TextView txtReminder = (TextView)findViewById(R.id.txtReminder);
        String message = txtReminder.getText().toString();

        try {
            read();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(timeList == null) {
            timeList = new ArrayList();
        } else {
            timeList.add(message);
        }

        write(timeList);

        alarm = new AlarmReceiver();
        alarm.setAlarm(getApplicationContext(), message, Integer.parseInt(txtDuration.getText().toString()));
        Toast.makeText(getApplicationContext(), "Alarm Set", Toast.LENGTH_SHORT).show();
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
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void read() throws IOException, ClassNotFoundException {
        FileInputStream fis = getApplicationContext().openFileInput(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        timeList = (List)is.readObject();
        is.close();
        fis.close();
        Log.d("Items", timeList.toString());
    }

    public void write(List list) throws IOException {
        FileOutputStream fos = getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(list);
        os.close();
        fos.close();
    }
}
