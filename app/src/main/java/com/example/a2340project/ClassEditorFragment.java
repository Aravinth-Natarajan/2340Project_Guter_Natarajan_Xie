package com.example.a2340project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.a2340project.databinding.ClassEditorBinding;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class ClassEditorFragment extends Fragment {
    private ClassEditorBinding binding;
    private LinearLayout datetimeLayout;
    private Course course;
    private boolean isNew;

    public ClassEditorFragment(Course course, boolean isNew) {
        this.course = course;
        this.isNew = isNew;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
                             Bundle savedBundleInstance
    ) {

        binding = ClassEditorBinding.inflate(inflater, container, false);
        datetimeLayout = binding.datetimeListLayout;

        MainActivity.updateMenu(getParentFragmentManager(), TaskbarMenuState.EDIT_CLASS);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        setupCourseNameHandler();
        setupSectionNameHandler();
        setupInstructorNameHandler();
        setupLocationNameHandler();
        setupInitialClassTimes();

        binding.addDatetimeButton.setOnClickListener((v -> {
            ClassTime classTime = new ClassTime();
            course.getClassTimes().add(classTime);
            addNewDatetimeInput(classTime, false);
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupCourseNameHandler() {
        if (!isNew) binding.classNameInput.setText(course.getName());
        binding.classNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                course.setName(s.toString());
            }
        });
    }

    private void setupSectionNameHandler() {
        if (!isNew) binding.sectionNameInput.setText(course.getSection());
        binding.sectionNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                course.setSection(s.toString());
            }
        });
    }

    private void setupInstructorNameHandler() {
        if (!isNew) binding.classInstructorInput.setText(course.getInstructor());
        binding.classInstructorInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                course.setInstructor(s.toString());
            }
        });
    }

    private void setupLocationNameHandler() {
        if (!isNew) binding.classLocationInput.setText(course.getLocation());
        binding.classLocationInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                course.setLocation(s.toString());
            }
        });
    }

    private void setupInitialClassTimes() {
        for (ClassTime classTime : course.getClassTimes()) {
            addNewDatetimeInput(classTime, true);
        }
    }

    private void addNewDatetimeInput(ClassTime classTime, boolean writeValues) {
        View datetimeInput = LayoutInflater.from(getContext())
                .inflate(R.layout.course_datetime_input, datetimeLayout, false);

        Spinner spinner = datetimeInput.findViewById(R.id.spinner_day_chooser);
        EditText timeField = datetimeInput.findViewById(R.id.time_input);
        EditText durationField = datetimeInput.findViewById(R.id.duration_input);
        spinner.setAdapter(ArrayAdapter.createFromResource(
                getContext(), R.array.days_of_week, android.R.layout.simple_spinner_item));

        if (writeValues) {
            spinner.setSelection(classTime.getDay().ordinal());
            timeField.setText(classTime.getTime());
            durationField.setText(Integer.toString(classTime.getDuration()));
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classTime.setDay(
                        DayOfWeek.valueOf(parent.getItemAtPosition(position).toString().toUpperCase()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        timeField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        classTime.setTime(s.toString());
                    }
                }
        );

        durationField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        classTime.setDuration(Integer.parseInt(s.toString()));
                    }
                }
        );

        datetimeInput.findViewById(R.id.cancel_classtime_button).setOnClickListener(v -> {
            course.getClassTimes().remove(classTime);
            Log.e("Classtime Button: ", "Removed from classtime list");
            datetimeLayout.removeView((View) v.getParent());
            Log.e("Classtime Button: ", "Removed from parent view: " + datetimeLayout.toString());
        });

        datetimeLayout.addView(datetimeInput, datetimeLayout.getChildCount());
    }

}
