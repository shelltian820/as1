package com.example.stian3_habittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Shelley on 2016-10-01.
 */
public class ShowRecord extends Activity {

    private Habit h;
    private SimpleDateFormat df = new SimpleDateFormat("EEE, MMM dd, yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_record);

        TextView habitName = (TextView) findViewById(R.id.habit_name);
        TextView dateAdded = (TextView) findViewById(R.id.date_added);
        TextView repeatOn = (TextView) findViewById(R.id.repeat_on);
        TextView fulfilledCount = (TextView) findViewById(R.id.fulfilled_count);
        TextView daysCompleted = (TextView) findViewById(R.id.days_completed);


        Intent intent = getIntent();
        h = (Habit) intent.getSerializableExtra("habit");

        habitName.append(h.getName());
        String d = df.format(h.getFirstDate());
        dateAdded.append(d);
        repeatOn.append(h.getOccurString());
        fulfilledCount.append(Integer.toString(h.getCompletedCount()));

        daysCompleted.append("\n");
        for(Date date: h.getDatesCompleted()){
            daysCompleted.append(df.format(date));
            daysCompleted.append("\n");
        }

    }


    //Handle button click
    public void delHabit(View v){
        if (v.getId() == R.id.delHabit) {
            habitTrackerActivity.myHabitsList.remove(h);
            Toast.makeText(getBaseContext(), "Habit deleted.", Toast.LENGTH_SHORT).show();
            saveInFile();
            finish();
        }

    }


    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(habitTrackerActivity.FILENAME, 0);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(habitTrackerActivity.myHabitsList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }





}
