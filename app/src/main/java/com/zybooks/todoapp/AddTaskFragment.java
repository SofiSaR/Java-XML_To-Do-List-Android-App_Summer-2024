package com.zybooks.todoapp;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zybooks.todoapp.model.Task;
import com.zybooks.todoapp.viewmodel.TaskViewModel;

public class AddTaskFragment extends Fragment {

    public AddTaskFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_task, container, false);

        TaskViewModel mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        EditText taskName = rootView.findViewById(R.id.edit_title_view);
        EditText taskDesc = rootView.findViewById(R.id.edit_description_view);

        Button addTaskButton = rootView.findViewById(R.id.add_button);
        addTaskButton.setOnClickListener(
                (v) -> {
                    String name = taskName.getText().toString();
                    String desc = taskDesc.getText().toString();
                    if (name.isEmpty() || name.length() > 62) {
                        taskName.setError("Wrong input");
                        return;
                    }
                    if (desc.isEmpty()) {
                        taskDesc.setError("Wrong input");
                        return;
                    }
                    Task newTask = new Task(name, desc);
                    mTaskViewModel.addTask(newTask);
                    NavController navController = findNavController(rootView);
                    navController.popBackStack();
                }
        );

        return rootView;
    }
}
