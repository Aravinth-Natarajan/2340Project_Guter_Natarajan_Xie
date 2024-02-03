package com.example.a2340project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.a2340project.databinding.ClassDetailsFragmentBinding;

public class ClassDetailsFragment extends Fragment {
    private ClassDetailsFragmentBinding binding;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedBundleInstance
        ) {

        binding = ClassDetailsFragmentBinding.inflate(inflater, container, false);

        AppCompatActivity mainActivity = (AppCompatActivity) getActivity();
        ActionBar actionBar = mainActivity.getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeButtonEnabled(false);

        Bundle menuUpdate = new Bundle();
        menuUpdate.putString("menuStateKey", "VIEW_CLASS");
        getParentFragmentManager().setFragmentResult("menuUpdateKey", menuUpdate);

        return binding.getRoot();
    }

}
