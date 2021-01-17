package com.example.android.SemanticApp;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Data Access Object (DAO) for a word.
 * Each method performs a database operation, such as inserting or deleting a word,
 * running a DB query, or deleting all words.
 */

@Dao
public interface HolidayDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Holiday holiday);

    @Query("DELETE FROM holiday_table")
    void deleteAll();

    @Delete
    void delete(Holiday holiday);

    @Query("SELECT * from holiday_table LIMIT 1")
    Holiday[] getAnyHoliday();

    @Query("SELECT * from holiday_table ORDER BY id DESC")
    LiveData<List<Holiday>> getAllHolidays();

    @Query("SELECT * from holiday_table where product = \"BBGUNS4LESS\" ORDER BY id DESC ")
    LiveData<List<Holiday>> getBBHolidays();

    @Query("SELECT * from holiday_table where product = \"Whopper\" ORDER BY id DESC")
    LiveData<List<Holiday>> getWhopperHolidays();

    @Query("SELECT id from holiday_table ORDER BY id DESC LIMIT 1")
    int getLatestHolidayId();

    @Update
    void update(Holiday... holiday);
}

