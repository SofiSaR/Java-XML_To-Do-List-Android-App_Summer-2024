package com.zybooks.todoapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "description")
    private String mDescription;
    @ColumnInfo(name = "is_checked")
    private boolean isChecked;

    public Task() {}

    public Task(Task taskToCopy) {
        mId = taskToCopy.mId;
        mTitle = taskToCopy.mTitle;
        mDescription = taskToCopy.mDescription;
        isChecked = taskToCopy.isChecked;
    }

    public Task(int id, @NonNull String title, @NonNull String description, boolean checked) {
        mId = id;
        mTitle = title;
        mDescription = description;
        isChecked = checked;
    }

    public Task(@NonNull String title, @NonNull String description) {
        mTitle = title;
        mDescription = description;
        isChecked = false;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String name) {
        this.mTitle = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        this.isChecked = checked;
    }

}