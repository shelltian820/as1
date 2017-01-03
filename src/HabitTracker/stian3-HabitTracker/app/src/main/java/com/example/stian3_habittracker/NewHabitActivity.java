package com.example.stian3_habittracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shelley on 2016-09-22.
 */
public class NewHabitActivity extends Activity{
    private EditText hnameText;
    private CheckBox cbMon;
    private CheckBox cbTue;
    private CheckBox cbWed;
    private CheckBox cbThu;
    private CheckBox cbFri;
    private CheckBox cbSat;
    private CheckBox cbSun;
    private EditText startDate;

    private boolean isSelectMon = false;
    private boolean isSelectTue = false;
    private boolean isSelectWed = false;
    private boolean isSelectThu = false;
    private boolean isSelectFri = false;
    private boolean isSelectSat = false;
    private boolean isSelectSun = false;

    private Calendar myCalendar = Calendar.getInstance();
    private SimpleDateFormat df = new SimpleDateFormat("mm/dd/yyyy");



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

        startDate = (EditText) findViewById(R.id.startDate);

        //http://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                view.setMinDate(new Date().getTime()-10000);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startDate.setText(df.format(myCalendar.getTime()));
            }

        };
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(NewHabitActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




    }
    //Handle button click
    public void addHabit(View v){
        if (v.getId() == R.id.addButton) {
            String hname = hnameText.getText().toString();

            if (cbMon.isChecked()) {isSelectMon = true;}
            if (cbTue.isChecked()) {isSelectTue = true;}
            if (cbWed.isChecked()) {isSelectWed = true;}
            if (cbThu.isChecked()) {isSelectThu = true;}
            if (cbFri.isChecked()) {isSelectFri = true;}
            if (cbSat.isChecked()) {isSelectSat = true;}
            if (cbSun.isChecked()) {isSelectSun = true;}

            //TODO: get picked date

            boolean atLeastOneDayChecked = false;
            boolean nameEntered = false;

            if(isSelectMon||isSelectTue||isSelectWed||isSelectThu||isSelectFri||isSelectSat||isSelectSun){
                atLeastOneDayChecked = true;
            }

            if(hname.length()>0 && hname.length()<100){
                nameEntered = true;
            }

            if(atLeastOneDayChecked && nameEntered){
                Habit newHabit = new Habit(hname, isSelectMon, isSelectTue, isSelectWed,
                        isSelectThu, isSelectFri, isSelectSat, isSelectSun);
                MainActivity.allHabitsList.add(newHabit);
                saveInFile();
                finish();
            }else{
                AlertDialog alertDialog = new AlertDialog.Builder(NewHabitActivity.this).create();
                alertDialog.setTitle("TRY AGAIN");
                alertDialog.setMessage("Your habit has no name and/or you have not checked a day.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                alertDialog.show();

            }

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






