package com.example.a2340project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a2340project.databinding.TaskDetailsBinding;

public class ToDoDetailsFragment extends Fragment {
    private TaskDetailsBinding binding;
    private Task task;
    private TaskViewModel taskViewModel;

    public ToDoDetailsFragment(Task task) {
        this.task = task;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = TaskDetailsBinding.inflate(inflater, container, false);

        MainActivity.updateMenu(getParentFragmentManager(), TaskbarMenuState.VIEW_TASK);

        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        taskViewModel.setTask(task);
        taskViewModel.setIsNew(false);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.taskName.setText(task.getTitle());
        binding.taskType.setText(task.getTaskType());
        binding.taskDueDate.setText(task.getDueDateString());
        binding.taskDescription.setText(task.getDescription());

        switch(task.getTaskType()) {
            case "Exam":
                binding.taskLocation.setText("Location: " + task.getCourse().getLocation());
                binding.taskLocation.setVisibility(View.VISIBLE);
            case "Assignment":
                binding.associatedCourse.setText("Course: " + task.getCourse().getName());
                binding.associatedCourse.setVisibility(View.VISIBLE);
                break;
            case "Task":
            default:
                binding.associatedCourse.setVisibility(View.GONE);
                binding.taskLocation.setVisibility(View.GONE);
                break;
        }
    }
}
