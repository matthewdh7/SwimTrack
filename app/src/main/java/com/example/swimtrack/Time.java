package com.example.swimtrack;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "time_table") //Tells room to create a data table
public class Time {

    @PrimaryKey(autoGenerate = true) //Auto increments id each new data
    private int id;

    private String name;
    private String newTime;
    private String bestTime;
    private String date;

    public Time(String name, String newTime, String date) {
        this.name = name;
        this.newTime = newTime;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) { this.date = date; }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNewTime() {
        return newTime;
    }

    public String getBestTime() {
        return bestTime;
    }

    public String getDate() {
        return date;
    }



}
