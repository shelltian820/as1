

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
import java.util.Date;

public class HabitTrackerActivity extends AppCompatActivity {

    private ListView dailyHabitsListView;
    public static final String FILENAME = "h_file.sav";
    public static ArrayList<Habit> myHabitsList;
    private static ArrayAdapter<Habit> adapter;
    private SimpleDateFormat df = new SimpleDateFormat("EEE, MMM dd, yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);
        TextView tv = (TextView) findViewById(R.id.currentDate);
        //myHabitsList = new ArrayList<Habit>();

        //display date
        //code from http://stackoverflow.com/questions/12934661/android-get-current-date-and-show-it-in-textview
        long date = System.currentTimeMillis();
        String dateString = df.format(date);
        tv.setText(dateString);


    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        //use this to clear file
        //clearFile();

        TextView tv = (TextView) findViewById(R.id.click_habit) ;
        if (myHabitsList.isEmpty()){
            tv.setText("There are no habits to complete.");
        }else{
            tv.setText("Click a habit to complete it.");
        }
        //add items to main list view
        createListView();

    }


    public void createListView(){
        //build adapter
        // code from https://www.youtube.com/watch?v=eAPFgC9URqc
        adapter = new ArrayAdapter<Habit>(this,
                R.layout.main_list_item, myHabitsList);
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
            Intent intent = new Intent(HabitTrackerActivity.this, NewHabit.class);
            startActivity(intent);

        }

    }
    public void viewAll(View v){
        if (v.getId() == R.id.viewAllButton) {
            Intent intent = new Intent(HabitTrackerActivity.this, ViewAll.class);
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

            myHabitsList = gson.fromJson(in,listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            myHabitsList = new ArrayList<Habit>();
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
            gson.toJson(myHabitsList, out);
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
        myHabitsList.clear();
        saveInFile();
    }
}


