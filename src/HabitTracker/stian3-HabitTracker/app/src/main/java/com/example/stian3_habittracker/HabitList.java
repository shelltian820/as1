package com.example.stian3_habittracker;

import java.util.ArrayList;

/**
 * Created by Shelley on 2016-09-28.
 */
public class HabitList {

    ArrayList<Habit> habits = new ArrayList<Habit>();

    public void addHabit(Habit habit){
        habits.add(habit);
    }

    public Habit getHabit(int index){
        return habits.get(index);
    }

    public void removeHabit(Habit habit){
        habits.remove(habit);
    }

    public boolean isEmpty(){ return habits.isEmpty();}


}
