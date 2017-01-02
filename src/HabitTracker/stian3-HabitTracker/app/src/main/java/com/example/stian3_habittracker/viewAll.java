package com.example.stian3_habittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Shelley on 2016-09-25.
 */
public class ViewAll extends Activity {
    private ListView va_list_view;
    public static ArrayAdapter<Habit> vAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all);
        createListView();
    }

    @Override
    protected void onStart(){
        super.onStart();
        TextView tv = (TextView) findViewById(R.id.click_info) ;
        if (HabitTrackerActivity.myHabitsList.isEmpty()){
            tv.setText("There are currently no habits.");
        }else{
            tv.setText("Tap on a habit for more information.");
        }
    }


    public void createListView(){
        //build adapter
        // code from https://www.youtube.com/watch?v=eAPFgC9URqc
        vAdapter = new ArrayAdapter<Habit>(this,
                R.layout.va_list_item, HabitTrackerActivity.myHabitsList);
        va_list_view = (ListView)findViewById(R.id.va_list_view);
        va_list_view.setAdapter(vAdapter);

        //add item click listener
        //code from http://www.ezzylearning.com/tutorial/handling-android-listview-onitemclick-event
        va_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //code from https://www.youtube.com/watch?v=Gi46yco8OJg
                Habit h = (Habit) va_list_view.getItemAtPosition(position);
                int h_index = position;
                //Toast.makeText(getBaseContext(), "view habit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ViewAll.this, ShowRecord.class);
                intent.putExtra("habit",h);
                intent.putExtra("habit_index",h_index);
                startActivity(intent);



            }
        });
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(HabitTrackerActivity.FILENAME, 0);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(HabitTrackerActivity.myHabitsList, out);
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
