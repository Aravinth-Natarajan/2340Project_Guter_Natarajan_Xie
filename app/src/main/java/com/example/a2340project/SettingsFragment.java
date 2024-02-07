package com.example.a2340project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a2340project.databinding.ClassesFragmentBinding;
import com.example.a2340project.databinding.SettingsFragmentBinding;

import java.time.DayOfWeek;

public class SettingsFragment extends Fragment {
    private com.example.a2340project.databinding.SettingsFragmentBinding binding;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SettingsFragmentBinding.inflate(inflater, container, false);

        layoutManager = new LinearLayoutManager(getActivity());

        MainActivity.updateMenu(getParentFragmentManager(), TaskbarMenuState.HIDE_MENU);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = binding.settingsSpinner;
        spinner.setAdapter(ArrayAdapter.createFromResource(
                getContext(), R.array.settings_spinner_array, android.R.layout.simple_spinner_item));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String toPut = (String) parent.getItemAtPosition(position);
                Bundle menuUpdate = new Bundle();
                menuUpdate.putString("timeToAlarmStateKey", toPut);
                getParentFragmentManager().setFragmentResult("timeToAlarmUpdateKey", menuUpdate);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
