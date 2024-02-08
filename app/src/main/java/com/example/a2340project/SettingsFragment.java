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

import com.example.a2340project.databinding.SettingsFragmentBinding;

import java.util.Arrays;

public class SettingsFragment extends Fragment {
    private SettingsFragmentBinding binding;
    private String notifTimerSelection;

    public SettingsFragment(String notifTimerSelection) {
        this.notifTimerSelection = notifTimerSelection;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SettingsFragmentBinding.inflate(inflater, container, false);

        MainActivity.updateMenu(getParentFragmentManager(), TaskbarMenuState.HIDE_MENU);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = binding.settingsSpinner;
        spinner.setAdapter(ArrayAdapter.createFromResource(
                getContext(), R.array.settings_spinner_array, android.R.layout.simple_spinner_dropdown_item));

        int selPos = Arrays.asList(getResources().getStringArray(R.array.settings_spinner_array)).indexOf(notifTimerSelection);
        spinner.setSelection(selPos);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                notifTimerSelection = parent.getItemAtPosition(position).toString();
                Bundle settingsUpdate = new Bundle();
                settingsUpdate.putString("timeToAlarmStateKey", notifTimerSelection);
                getParentFragmentManager().setFragmentResult("timeToAlarmUpdateKey", settingsUpdate);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
