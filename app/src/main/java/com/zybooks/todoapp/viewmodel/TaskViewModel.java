package com.zybooks.todoapp.viewmodel;

import android.app.Application;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.todoapp.R;
import com.zybooks.todoapp.TaskDetailsFragment;
import com.zybooks.todoapp.ToDoFragment;
import com.zybooks.todoapp.model.Task;
import com.zybooks.todoapp.repo.TaskRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskViewModel extends AndroidViewModel {

    private final TaskRepository mTaskRepo;
    private boolean mToDoFragActive = false;

    private final ScheduledExecutorService executorService;

    public TaskViewModel( Application application) {
        super(application);
        mTaskRepo = TaskRepository.getInstance(application.getApplicationContext());
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void startTimer() {
        long delay = 60 - Calendar.getInstance().get(Calendar.SECOND);
        executorService.scheduleWithFixedDelay(() -> {
            if (mTaskRepo.getRefreshMode().equals("Minute"))
                resetCheckedTasks();
            else if (mTaskRepo.getRefreshMode().equals("Hour") && Calendar.getInstance().get(Calendar.MINUTE) == 0)
                resetCheckedTasks();
            else if (mTaskRepo.getRefreshMode().equals("Day") && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 0 && Calendar.getInstance().get(Calendar.MINUTE) == 0)
                resetCheckedTasks();
        }, delay, 60, TimeUnit.SECONDS);
    }

    public void stopTimer() {
        executorService.shutdownNow();
    }

    private void resetCheckedTasks() {
        Log.d("Message", "Outside reset tasks");
        List<Task> tasks = getTasks();
        if (tasks != null) {
            for (Task task : tasks) {
                task.setIsChecked(false);
                updateTask(task);
            }
            updateTaskList(tasks);
            Log.d("Message", "Entered reset tasks");
        }
    }

    public Task getTask(long taskId) {
        return mTaskRepo.getTask(taskId);
    }
    public List<Task> getTasks(){
        return mTaskRepo.getTasks();
    }

    public boolean getToDoFragActive() {
        return mToDoFragActive;
    }
    public void setToDoFragActive(boolean toDoFragActive) {
        this.mToDoFragActive = toDoFragActive;
    }

    public String getRefreshMode() {
        return mTaskRepo.getRefreshMode();
    }
    public void setRefreshMode(String refreshMode) {
        mTaskRepo.setRefreshMode(refreshMode);
    }

    public void addTask(Task t) {
        mTaskRepo.addTask(t);
    }

    public void updateTask(Task task) {
        mTaskRepo.updateTask(task);
    }

    public void updateTaskList(List<Task> tasks) {
        mTaskRepo.updateTaskList(tasks);
    }

    public void deleteEntries() {
        mTaskRepo.deleteEntries();
    }
}