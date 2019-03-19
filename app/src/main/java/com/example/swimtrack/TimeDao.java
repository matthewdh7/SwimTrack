package com.example.swimtrack;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TimeDao {

    @Insert
    void insert(Time time);

    @Update
    void update(Time time);

    @Delete
    void delete(Time time);

    @Query("DELETE FROM time_table")
    void deleteAllTimes();

    @Query("SELECT * FROM time_table ORDER BY name")
    LiveData<List<Time>> getAllTimes();
}
