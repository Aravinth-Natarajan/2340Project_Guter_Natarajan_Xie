package com.example.a2340project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a2340project.databinding.ClassesFragmentBinding;
import com.example.a2340project.databinding.LoginFragmentBinding;

public class LoginFragment extends Fragment {
    private LoginFragmentBinding binding;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = LoginFragmentBinding.inflate(inflater, container, false);

        layoutManager = new LinearLayoutManager(getActivity());


        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle menuUpdate = new Bundle();
                menuUpdate.putString("loginStateKey", binding.loginUsernameInput.getText().toString());
                getParentFragmentManager().setFragmentResult("loginUpdateKey", menuUpdate);

            }
        });
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle menuUpdate = new Bundle();
                menuUpdate.putString("registerStateKey", binding.loginUsernameInput.getText().toString());
                getParentFragmentManager().setFragmentResult("registerUpdateKey", menuUpdate);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
