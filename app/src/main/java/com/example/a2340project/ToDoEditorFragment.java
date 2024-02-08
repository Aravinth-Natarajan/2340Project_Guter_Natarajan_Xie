package com.example.a2340project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a2340project.databinding.TaskEditorBinding;

import java.util.Calendar;
import java.util.List;

public class ToDoEditorFragment extends Fragment {
    private TaskEditorBinding binding;
    private Task task;
    private boolean isNew;
    private DateTimePickers.TimePickerFragment timePicker;
    private DateTimePickers.DatePickerFragment datePicker;
    private List<Course> courses;

    public ToDoEditorFragment(Task task, boolean isNew, List<Course> courses) {
        this.task = task;
        this.isNew = isNew;
        this.courses = courses;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = TaskEditorBinding.inflate(inflater, container, false);

        MainActivity.updateMenu(getParentFragmentManager(), TaskbarMenuState.EDIT_TASK);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        Calendar taskCalendar;
        if (task.getDueDate() == null) {
            task.setDueDate(Calendar.getInstance());
        }
        taskCalendar = task.getDueDate();

//        timePicker = new DateTimePickers.TimePickerFragment(taskCalendar);
        datePicker = new DateTimePickers.DatePickerFragment(taskCalendar);

        binding.taskDueDateButton.setOnClickListener(v -> {
            datePicker.show(getParentFragmentManager(), "datePicker");
//            timePicker.show(getParentFragmentManager(), "timePicker");
        });

        setupTaskNameHandler();
        setupTaskTypeHandler();
        setupTaskDescriptionHandler();
        setupTaskCourseHandler();

        if (task.getTaskType().equals("Exam") || task.getTaskType().equals("Assignment")) {
            binding.taskCourseTitle.setVisibility(View.VISIBLE);
            binding.taskCourseSpinner.setVisibility(View.VISIBLE);
        } else {
            binding.taskCourseTitle.setVisibility(View.GONE);
            binding.taskCourseSpinner.setVisibility(View.GONE);
        }
    }

    public void setupTaskNameHandler() {
        if (!isNew) binding.taskNameInput.setText(task.getTitle());
        binding.taskNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                task.setTitle(s.toString());
            }
        });

    }

    public void setupTaskTypeHandler() {
        binding.taskTypeSpinner.setAdapter(
                ArrayAdapter.createFromResource(
                        getContext(), R.array.task_types, android.R.layout.simple_spinner_item)
        );

        if (!isNew) {
            int selection;
            switch(task.getTaskType()) {
                default:
                case "Task":
                    selection = 0;
                    break;
                case "Exam":
                    selection = 1;
                    break;
                case "Assignment":
                    selection = 2;
                    break;
            }
            binding.taskTypeSpinner.setSelection(selection);
        }

        binding.taskTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newTaskType = parent.getItemAtPosition(position).toString();
                task.setTaskType(newTaskType);
                if (newTaskType.equals("Exam") || newTaskType.equals("Assignment")) {
                    binding.taskCourseTitle.setVisibility(View.VISIBLE);
                    binding.taskCourseSpinner.setVisibility(View.VISIBLE);
                } else {
                    binding.taskCourseTitle.setVisibility(View.GONE);
                    binding.taskCourseSpinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setupTaskDescriptionHandler() {
        if (!isNew) binding.taskDescriptionInput.setText(task.getDescription());

        binding.taskDescriptionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                task.setDescription(s.toString());
            }
        });
    }

    public void setupTaskCourseHandler() {
        binding.taskCourseSpinner.setAdapter(
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, courses)
        );

        if (!isNew) binding.taskCourseSpinner.setSelection(courses.indexOf(task.getCourse()));

        binding.taskCourseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                task.setCourse((Course) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
