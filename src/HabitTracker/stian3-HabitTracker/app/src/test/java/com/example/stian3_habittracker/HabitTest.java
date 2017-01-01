package com.example.stian3_habittracker;

import android.widget.ListView;

import org.junit.Test;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Shelley on 2016-12-31.
 */
public class HabitTest {
    public void testAddHabit(){}
    public void testRemoveHabit(){}
    public void testGetHabit(){}

    @Test
    public void testCompleteHabit(){
        Habit mockHabit = new Habit("mock habit",true,true,true,true,true,true,true);
        String mockString1 = "12/03/2016";
        String mockString2 = "11/03/2016";
        String mockString3 = "12/13/2016";

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date mockDate = (Date) dateFormat.parse(mockString1);
            mockHabit.complete(mockDate);
            assertEquals(mockHabit.getDatesCompleted().get(0), mockDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}
