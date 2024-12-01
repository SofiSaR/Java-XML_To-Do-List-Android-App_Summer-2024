package com.zybooks.todoapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zybooks.todoapp.model.Task;
import com.zybooks.todoapp.viewmodel.TaskViewModel;

public class TaskDetailsFragment extends Fragment {

    public static final String ARG_TASK_ID = "task_id";

    TaskViewModel mTaskViewModel;
    private long taskId;

    public TaskDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);


        Bundle args = getArguments();
        if (args != null) {
            taskId = args.getLong(ARG_TASK_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_details, container, false);

        Task task = mTaskViewModel.getTask(taskId);
        if (task != null) {
            TextView titleTextView = rootView.findViewById(R.id.task_title);
            titleTextView.setText(task.getTitle());

            TextView descriptionTextView = rootView.findViewById(R.id.task_description);
            descriptionTextView.setText(task.getDescription());
        }

//        Thread thread = new Thread(() -> {
//            TaskViewModel mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
//            mTask = mTaskViewModel.getTask(taskId).getValue();
//            requireActivity().runOnUiThread(() -> {
//                TextView titleTextView = rootView.findViewById(R.id.task_title);
//                TextView descriptionTextView = rootView.findViewById(R.id.task_description);
//                titleTextView.setText(mTask.getTitle());
//                descriptionTextView.setText(mTask.getDescription());
//            });
//        });
//        thread.start();

        return rootView;
    }
}
