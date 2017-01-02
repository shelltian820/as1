package com.example.stian3_habittracker;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.roomorama.caldroid.CaldroidFragment;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shelley on 2016-10-01.
 */
public class HabitInfoActivity extends FragmentActivity {

    private Habit h;
    private SimpleDateFormat df = new SimpleDateFormat("EEE, MMM dd, yyyy");
    private int h_index;

    private CaldroidFragment caldroidFragment = new CaldroidFragment();
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
        h_index = intent.getIntExtra("habit_index",9999);


        habitName.setText(h.getName());
        String d = df.format(h.getDateAdded());
        dateAdded.append(d);
        repeatOn.append(h.getOccurString());
        fulfilledCount.append(Integer.toString(h.getCompletedCount()));

        daysCompleted.append("\n");
        for(Date date: h.getDatesCompleted()){
            //add in scrollview
            daysCompleted.append(df.format(date));
            daysCompleted.append("\n");
            //add in calendar
            markComplete(date);
        }

        addCalendar();





    }

    private void addCalendar(){
        //TODO: highlight dates

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.caldroid, caldroidFragment);
        t.commit();

    }
    public void markIncomplete(Date date){
        ColorDrawable red = new ColorDrawable(getResources().getColor(R.color.incomplete));
        caldroidFragment.setBackgroundDrawableForDate(red, date);
    }
    public void markComplete(Date date){
        ColorDrawable green = new ColorDrawable(getResources().getColor(R.color.complete));
        caldroidFragment.setBackgroundDrawableForDate(green, date);
    }


    //Handle button click
    public void delHabit(View v){
        if (v.getId() == R.id.delHabit) {
            //TODO: Make warning

            MainActivity.allHabitsList.remove(h_index);
            Toast.makeText(getBaseContext(), "Habit deleted.", Toast.LENGTH_SHORT).show();
            saveInFile();
            ViewAllActivity.vAdapter.notifyDataSetChanged();
            finish();
        }

    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(MainActivity.FILENAME, 0);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(MainActivity.allHabitsList, out);
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
