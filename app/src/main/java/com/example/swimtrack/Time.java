package com.example.swimtrack;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "time_table") //Tells room to create a data table
public class Time {

    @PrimaryKey(autoGenerate = true) //Auto increments id each new data
    private int id;

    private String name;
    private String newTime;
    private String date;
    private boolean bestTime;

    public Time(String name, String newTime, String date, boolean bestTime) {
        this.name = name;
        this.newTime = newTime;
        this.date = date;
        this.bestTime = bestTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) { this.date = date; }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNewTime() {
        return newTime;
    }

    public String getDate() {
        return date;
    }

    public boolean isBestTime() {
        return bestTime;
    }

    public void setBestTime(boolean bestTime) {
        this.bestTime = bestTime;
    }
}
