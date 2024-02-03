package com.example.a2340project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.a2340project.databinding.ClassDetailsBinding;

public class ClassDetailsFragment extends Fragment {
    private ClassDetailsBinding binding;
    private Course course;

    public ClassDetailsFragment(Course course) {
        this.course = course;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedBundleInstance
        ) {

        binding = ClassDetailsBinding.inflate(inflater, container, false);

        AppCompatActivity mainActivity = (AppCompatActivity) getActivity();
        ActionBar actionBar = mainActivity.getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(false);

        updateMenu();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.courseName.setText(course.getName());
        binding.section.setText("Section: " + course.getSection());
        binding.instructor.setText("Instructor: " + course.getInstructor());
        binding.location.setText("Location: " + course.getLocation());
        binding.meetingTimes.setAdapter(new MeetingTimesAdapter(getContext(), course.getClassTimes()));
    }

    private void updateMenu() {
        Bundle menuUpdate = new Bundle();
        menuUpdate.putString("menuStateKey", "VIEW_CLASS");
        getParentFragmentManager().setFragmentResult("menuUpdateKey", menuUpdate);
    }

}
