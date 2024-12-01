package com.zybooks.todoapp;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.todoapp.model.Task;
import com.zybooks.todoapp.viewmodel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends Fragment {

    TaskViewModel mTaskViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_to_do, container, false);

        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        mTaskViewModel.setToDoFragActive(true);

        View.OnClickListener onClickListener = itemView -> {

            long selectedTaskId = (long) itemView.getTag();
            Bundle args = new Bundle();
            args.putLong(TaskDetailsFragment.ARG_TASK_ID, selectedTaskId);

            // Replace list with details
            Navigation.findNavController(itemView).navigate(R.id.show_task_details, args);
        };

        ImageButton addTaskBtn = rootView.findViewById(R.id.add_task_btn);

        addTaskBtn.setOnClickListener(
                (v) -> Navigation.findNavController(rootView).navigate(R.id.show_add_task_page)
        );

        RecyclerView recyclerView = rootView.findViewById(R.id.task_list);
        //mTaskViewModel.deleteEntries();
        List<Task> tasks = mTaskViewModel.getTasks();
        if (tasks != null) {
            recyclerView.setAdapter(new TaskAdapter(tasks, onClickListener));
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTaskViewModel.setToDoFragActive(false);
    }


    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private final List<Task> mTasks;
        private final View.OnClickListener mOnClickListener;

        public TaskAdapter(List<Task> tasks, View.OnClickListener onClickListener) {
            mTasks = tasks;
            mOnClickListener = onClickListener;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            int position2 = holder.getAdapterPosition();
            Task task = mTasks.get(position2);
            holder.bind(task);
            holder.itemView.setTag(task.getId());

            holder.itemView.setOnClickListener(mOnClickListener);

            holder.mTaskCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    task.setIsChecked(isChecked);
                    mTaskViewModel.updateTask(task);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

    private static class TaskHolder extends RecyclerView.ViewHolder {

        private final TextView mTaskTextView;
        private final CheckBox mTaskCheckBox;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.task_item, parent, false));
            mTaskTextView = itemView.findViewById(R.id.task_text);
            mTaskCheckBox = itemView.findViewById(R.id.task_checkbox);
        }

        public void bind(Task task) {
            mTaskTextView.setText(task.getTitle());
            mTaskCheckBox.setChecked(task.getIsChecked());
        }
    }
}