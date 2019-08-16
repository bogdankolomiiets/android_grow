package com.example.practicetwo.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.practicetwo.Constants;

@Entity
public class Task implements Constants {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = COLUMN_TITLE)
    private String title;

    @ColumnInfo(name = COLUMN_DESCRIPTION)
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
