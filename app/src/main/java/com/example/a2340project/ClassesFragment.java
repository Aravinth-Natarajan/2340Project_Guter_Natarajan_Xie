package com.example.a2340project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a2340project.databinding.ClassesFragmentBinding;

import java.util.ArrayList;

public class ClassesFragment extends Fragment {

    private ClassesFragmentBinding binding;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ClassesFragmentBinding.inflate(inflater, container, false);

        layoutManager = new LinearLayoutManager(getActivity());


        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("Test course", "Howey", "Gerard"));
        courses.add(new Course("Test course 2", "Brent Key", "L"));
        courses.add(new Course("Test course 3", "Super Bowl", "Tom Brady"));

        ClassListAdapter classListAdapter = new ClassListAdapter(courses);
        binding.courseListView.setAdapter(classListAdapter);
        binding.courseListView.setLayoutManager(new LinearLayoutManager(getContext()));

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