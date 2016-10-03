package com.example.stian3_habittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Shelley on 2016-09-25.
 */
public class viewAll extends Activity {
    private ListView va_list_view;
    public static ArrayAdapter<Habit> vAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all);
        createListView();
    }


    public void createListView(){
        //build adapter
        // code from https://www.youtube.com/watch?v=eAPFgC9URqc
        vAdapter = new ArrayAdapter<Habit>(this,
                R.layout.va_list_item, habitTrackerActivity.myHabitsList);
        va_list_view = (ListView)findViewById(R.id.va_list_view);
        va_list_view.setAdapter(vAdapter);

        //add item click listener
        //code from http://www.ezzylearning.com/tutorial/handling-android-listview-onitemclick-event
        va_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //code from https://www.youtube.com/watch?v=Gi46yco8OJg
                Habit h = (Habit) va_list_view.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), "view habit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(viewAll.this, ShowRecord.class);
                intent.putExtra("habit",h);
                startActivity(intent);



            }
        });
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
