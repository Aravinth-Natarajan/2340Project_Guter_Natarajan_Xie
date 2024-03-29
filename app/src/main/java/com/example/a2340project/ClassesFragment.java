package com.example.a2340project;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a2340project.databinding.ClassesFragmentBinding;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class ClassesFragment extends Fragment {

    private ClassesFragmentBinding binding;
    private LinearLayoutManager layoutManager;
    private List<Course> courses;

    public ClassesFragment(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ClassesFragmentBinding.inflate(inflater, container, false);

        layoutManager = new LinearLayoutManager(getActivity());


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            ArrayList<ClassTime> gerardTimes = new ArrayList<>();
//            gerardTimes.add(new ClassTime(DayOfWeek.MONDAY, "9:00 AM", 75));
//            gerardTimes.add(new ClassTime(DayOfWeek.WEDNESDAY, "9:00 AM", 75));
//            gerardTimes.add(new ClassTime(DayOfWeek.FRIDAY, "6:00 PM", 157));
//            courses.add(new Course(
//                    "CS 3511", gerardTimes, "Howey", "Gerard", "goated"
//                    ));
//        }
//        courses.add(new Course("Test course 2", "Brent Key", "L"));
//        courses.add(new Course("Test course 3", "Super Bowl", "Tom Brady"));

        ClassListAdapter classListAdapter = new ClassListAdapter(courses, getActivity().getSupportFragmentManager());
        binding.courseListView.setAdapter(classListAdapter);
        binding.courseListView.setLayoutManager(new LinearLayoutManager(getContext()));

        MainActivity.updateMenu(getParentFragmentManager(), TaskbarMenuState.CLASS_LIST);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

/*
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
*/
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}