package com.example.a2340project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.a2340project.databinding.ClassEditorBinding;

public class ClassEditorFragment extends Fragment {
    private ClassEditorBinding binding;

    private Course course;

    public ClassEditorFragment(Course course) {
        this.course = course;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
                             Bundle savedBundleInstance
    ) {

        binding = ClassEditorBinding.inflate(inflater, container, false);

//        AppCompatActivity mainActivity = (AppCompatActivity) getActivity();
//        ActionBar actionBar = mainActivity.getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setHomeButtonEnabled(false);

        MainActivity.updateMenu(getParentFragmentManager(), TaskbarMenuState.EDIT_CLASS);

        return binding.getRoot();
    }

}
