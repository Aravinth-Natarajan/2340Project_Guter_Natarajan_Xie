package com.example.a2340project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2340project.databinding.ClassesFragmentBinding;
import com.example.a2340project.databinding.ClassesFragmentBinding;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

private ClassesFragmentBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ClassesFragmentBinding.inflate(inflater, container, false);

        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("Test course"));

        ClassListAdapter classListAdapter = new ClassListAdapter(courses);
        binding.courseListView.setAdapter(classListAdapter);

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