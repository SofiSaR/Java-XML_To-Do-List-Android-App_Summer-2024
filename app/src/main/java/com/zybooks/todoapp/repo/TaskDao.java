package com.zybooks.todoapp.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zybooks.todoapp.model.Task;

import java.util.List;


@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task WHERE id = :id")
    Task getTask(long id);

    @Query("SELECT * FROM Task ORDER BY id COLLATE NOCASE")
    List<Task> getTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addTask(Task task);

    @Update
    void updateTask(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateTaskList(Task... tasks);

    @Query("DELETE FROM Task")
    void deleteEntries();

}