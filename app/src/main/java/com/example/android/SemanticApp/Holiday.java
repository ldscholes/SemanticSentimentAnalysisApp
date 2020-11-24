package com.example.android.SemanticApp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity( tableName = "holiday_table")
public class Holiday {

    @PrimaryKey( autoGenerate = true )
    private int id ;

    @NonNull
    @ColumnInfo(name = "holiday")
    private String mHoliday;

    @ColumnInfo(name = "notes")
    private String mNotes;

    @ColumnInfo(name = "opinion")
    private String mOpinion;

    public Holiday(@NonNull String holiday) {
        this.mHoliday = holiday;}



    /*
     * This constructor is annotated using @Ignore, because Room expects only
     * one constructor by default in an entity class.
     */

    @Ignore
    public Holiday(int id, @NonNull String holiday, String notes, String opinion) {
        this.id = id;
        this.mHoliday = holiday;
        this.mNotes = notes;
        this.mOpinion = opinion;

    }

    @Ignore
    public Holiday(@NonNull String holiday, String notes, String opinion) {
        this.mHoliday = holiday;
        this.mNotes = notes;
        this.mOpinion = opinion;
    }

    public String getHoliday(){return this.mHoliday;}

    public String getNotes(){return this.mNotes;}

    public void setNotes(String notes){this.mNotes = notes; }

    public String getOpinion(){return this.mOpinion;}

    public void setOpinion(String opinion){this.mOpinion = opinion; }

    public void setId (int id) { this.id = id; }

    public int getId () { return id ; }

}
