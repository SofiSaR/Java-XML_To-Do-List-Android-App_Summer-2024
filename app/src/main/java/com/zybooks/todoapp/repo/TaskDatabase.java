package com.zybooks.todoapp.repo;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.zybooks.todoapp.model.Task;

@Database(entities = {Task.class}, version =1)
public abstract class TaskDatabase extends RoomDatabase{
    public abstract TaskDao taskDAO();
}
