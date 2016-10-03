package com.example.stian3_habittracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * Created by Shelley on 2016-09-22.
 */
public class newHabit extends Activity{
    private EditText hnameText;
    private CheckBox cbMon;
    private CheckBox cbTue;
    private CheckBox cbWed;
    private CheckBox cbThu;
    private CheckBox cbFri;
    private CheckBox cbSat;
    private CheckBox cbSun;

    private boolean isSelectMon = false;
    private boolean isSelectTue = false;
    private boolean isSelectWed = false;
    private boolean isSelectThu = false;
    private boolean isSelectFri = false;
    private boolean isSelectSat = false;
    private boolean isSelectSun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);

        hnameText = (EditText) findViewById(R.id.editHabitNameText);

        cbMon = (CheckBox) findViewById(R.id.checkBoxMon);
        cbTue = (CheckBox) findViewById(R.id.checkBoxTue);
        cbWed = (CheckBox) findViewById(R.id.checkBoxWed);
        cbThu = (CheckBox) findViewById(R.id.checkBoxThu);
        cbFri = (CheckBox) findViewById(R.id.checkBoxFri);
        cbSat = (CheckBox) findViewById(R.id.checkBoxSat);
        cbSun = (CheckBox) findViewById(R.id.checkBoxSun);


    }
    //Handle button click
    public void addHabit(View v){
        if (v.getId() == R.id.addButton) {
            String hname = hnameText.getText().toString();

            if (cbMon.isChecked()) {
                isSelectMon = true;
            }
            if (cbTue.isChecked()) {
                isSelectTue = true;
            }
            if (cbWed.isChecked()) {
                isSelectWed = true;
            }
            if (cbThu.isChecked()) {
                isSelectThu = true;
            }
            if (cbFri.isChecked()) {
                isSelectFri = true;
            }
            if (cbSat.isChecked()) {
                isSelectSat = true;
            }
            if (cbSun.isChecked()) {
                isSelectSun = true;
            }

            Habit newHabit = new Habit(hname, isSelectMon, isSelectTue, isSelectWed,
                    isSelectThu, isSelectFri, isSelectSat, isSelectSun);

            habitTrackerActivity.myHabitsList.add(newHabit);
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






