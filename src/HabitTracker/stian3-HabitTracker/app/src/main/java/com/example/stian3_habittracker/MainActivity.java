

package com.example.stian3_habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ListView dailyHabitsListView;
    public static final String FILENAME = "h_file.sav";
    public static ArrayList<Habit> allHabitsList;
    public static ArrayList<Habit> dailyHabitsList;
    private static ArrayAdapter<Habit> adapter;
    private SimpleDateFormat df = new SimpleDateFormat("EEE, MMM dd, yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);
        TextView tv = (TextView) findViewById(R.id.currentDate);
        //allHabitsList = new ArrayList<Habit>();

        //display date
        //code from http://stackoverflow.com/questions/12934661/android-get-current-date-and-show-it-in-textview
        long date = System.currentTimeMillis();
        String dateString = df.format(date);
        tv.setText(dateString);


    }

    @Override
    protected void onStart() {

        //use this to clear file
        //clearFile();

        super.onStart();
        loadFromFile();

        TextView tv = (TextView) findViewById(R.id.click_habit) ;
        if (allHabitsList.isEmpty()){
            tv.setText("There are no habits to complete.");
        }else{
            tv.setText("Click a habit to complete it.");
        }
        //add items to main list view
        createListView();

    }

    private void createListView(){

        //TODO: only show habits that occur on current day of week
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        dailyHabitsList = new ArrayList<Habit>();

        switch (day) {
            case Calendar.SUNDAY:
                for (Habit habit : allHabitsList){
                    if(habit.isStarted() && habit.isOccurSun()){
                        dailyHabitsList.add(habit);
                    }
                }
                break;
            case Calendar.MONDAY:
                for (Habit habit : allHabitsList){
                    if(habit.isStarted() && habit.isOccurMon()){
                        dailyHabitsList.add(habit);
                    }
                }
                break;
            case Calendar.TUESDAY:
                for (Habit habit : allHabitsList){
                    if(habit.isStarted() && habit.isOccurTue()){
                        dailyHabitsList.add(habit);
                    }
                }
                break;
            case Calendar.WEDNESDAY:
                for (Habit habit : allHabitsList){
                    if(habit.isStarted() && habit.isOccurWed()){
                        dailyHabitsList.add(habit);
                    }
                }
                break;
            case Calendar.THURSDAY:
                for (Habit habit : allHabitsList){
                    if(habit.isStarted() && habit.isOccurThu()){
                        dailyHabitsList.add(habit);
                    }
                }
                break;
            case Calendar.FRIDAY:
                for (Habit habit : allHabitsList){
                    if(habit.isStarted() && habit.isOccurFri()){
                        dailyHabitsList.add(habit);
                    }
                }
                break;
            case Calendar.SATURDAY:
                for (Habit habit : allHabitsList){
                    if(habit.isStarted() && habit.isOccurSat()){
                        dailyHabitsList.add(habit);
                    }
                }
                break;




        }

        //build adapter
        // code from https://www.youtube.com/watch?v=eAPFgC9URqc
        adapter = new ArrayAdapter<Habit>(this,
                R.layout.main_list_item, dailyHabitsList);
        dailyHabitsListView = (ListView)findViewById(R.id.dailyHabitsListView);
        dailyHabitsListView.setAdapter(adapter);

        //add item click listener
        //code from http://www.ezzylearning.com/tutorial/handling-android-listview-onitemclick-event
        dailyHabitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Habit completed once.", Toast.LENGTH_SHORT).show();

                Habit h = (Habit) dailyHabitsListView.getItemAtPosition(position);
                h.complete(new Date());
                adapter.notifyDataSetChanged();
                saveInFile();

            }
        });
    }

    //Handle button click
    public void newHabit(View v){
        if (v.getId() == R.id.newHabitButton) {
            Intent intent = new Intent(MainActivity.this, NewHabitActivity.class);
            startActivity(intent);

        }

    }
    public void viewAll(View v){
        if (v.getId() == R.id.viewAllButton) {
            Intent intent = new Intent(MainActivity.this, ViewAllActivity.class);
            startActivity(intent);
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Code from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();

            allHabitsList = gson.fromJson(in,listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            allHabitsList = new ArrayList<Habit>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(allHabitsList, out);
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

    private void clearFile(){
        allHabitsList.clear();
        saveInFile();
    }


}


