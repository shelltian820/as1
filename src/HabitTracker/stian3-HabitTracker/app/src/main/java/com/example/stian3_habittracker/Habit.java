package com.example.stian3_habittracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Shelley on 2016-09-26.
 */
public class Habit implements Serializable{

    //code from https://www.youtube.com/watch?v=Gi46yco8OJg
    private static final long serialVersionUID = 1L;

    private String name;
    private Date dateAdded;
    private Date startDate;
    private boolean started = false;
    private boolean occurMon = false;
    private boolean occurTue = false;
    private boolean occurWed = false;
    private boolean occurThu = false;
    private boolean occurFri = false;
    private boolean occurSat = false;
    private boolean occurSun = false;
    private int completedCount = 0;
    private ArrayList<Date> datesCompleted = new ArrayList<Date>();
    private ArrayList<Date> datesIncomplete = new ArrayList<Date>();


    //constructor for when date is not entered
    public Habit(String name, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat, boolean sun){
        this.name = name;
        this.dateAdded = new Date();
        this.startDate = new Date();

        this.occurMon = mon;
        this.occurTue = tue;
        this.occurWed = wed;
        this.occurThu = thu;
        this.occurFri = fri;
        this.occurSat = sat;
        this.occurSun = sun;

        this.started = true;

    }
    //constructor for when start date is entered
    public Habit(String name, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat, boolean sun, Date date){
        this.name = name;
        this.dateAdded = new Date();
        this.startDate = date;

        this.occurMon = mon;
        this.occurTue = tue;
        this.occurWed = wed;
        this.occurThu = thu;
        this.occurFri = fri;
        this.occurSat = sat;
        this.occurSun = sun;

        this.started = false;
    }


    public String getName() {
        return name;
    }

    public int getCompletedCount(){
        return completedCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getDateAdded(){
        return dateAdded;
    }


    //displays which days habit is repeated
    public String getOccurString(){
        String s = "";
        if (occurMon) s += "Mon ";
        if (occurTue) s += "Tue ";
        if (occurWed) s += "Wed ";
        if (occurThu) s += "Thu ";
        if (occurFri) s += "Fri ";
        if (occurSat) s += "Sat ";
        if (occurSun) s += "Sun ";
        return s;

    }

    public ArrayList<Date> getDatesCompleted(){
        return datesCompleted;
    }

    public ArrayList<Date> getDatesIncomplete() {
        return datesIncomplete;
    }

    public void setName(String name) throws InvalidHabitException {
        if (name.length() > 100 || name.length()<1) {
            //TODO: edit habit name
            //Toast.makeText(Habit.this, "Your habit name is too short/long",Toast.LENGTH_LONG);
            throw new InvalidHabitException();
        }
        this.name = name;

    }

    public void setOccuringDays(){
        //TODO:let user change occuring days
    }

    public void setStartDate(){
        //TODO: let user change start date
    }

    @Override
    public String toString(){
        return  name;
    }

    public void complete(Date date){
        this.completedCount += 1;

        //add date to datesCompleted
        datesCompleted.add(date);
    }

}

