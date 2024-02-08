package com.example.a2340project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2340project.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    public static final String USERS_KEY = "USERS_KEY";
    public static final String USERNAME_KEY = "USERNAME_KEY";
    private ActivityLoginBinding binding;

    private SharedPreferences usernameRegister;
    private SharedPreferences.Editor userNameRegisterEditor;
    private Set<String> users;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usernameRegister = getSharedPreferences(getString(R.string.username_register_key), MODE_PRIVATE);
        userNameRegisterEditor = usernameRegister.edit();

        users = usernameRegister.getStringSet(USERS_KEY, new HashSet<>());
    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.loginButton.setOnClickListener(v -> {
            hideOSK();
            String userEntry = binding.loginUsernameInput.getText().toString();

            if (users.contains(userEntry)) {
                launchMainActivity(userEntry);
            } else {
                Snackbar.make(this, v.getRootView(),
                        "Sorry, that user isn't registered! Please try a different username or register a new user.",
                        5000)
                        .show();
            }
        });

        binding.registerButton.setOnClickListener(v -> {
            hideOSK();

            // Attempt to register new user
            String userEntry = binding.loginUsernameInput.getText().toString();

            if (users.contains(userEntry)) {
                Snackbar.make(this, v.getRootView(),
                                "Sorry, that user is already registered! Please try a different username or login.",
                                5000)
                        .show();
            } else if (isInvalidXMLString(userEntry)) {
                Snackbar.make(this, v.getRootView(),
                                "Sorry, that username contains illegal characters! Please try a different username or login.",
                                5000)
                        .show();
            }
            else {
                users.add(userEntry);
                userNameRegisterEditor.putStringSet(USERS_KEY, users);
                userNameRegisterEditor.apply();

                launchMainActivity(userEntry);
            }
        });
    }

    private void launchMainActivity(String username) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra(USERNAME_KEY, username);
        startActivity(mainIntent);
    }

    private void hideOSK() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean isInvalidXMLString(String string) {
        return Pattern.compile("[<>&'\"]").matcher(string).find() || string.equals("");
    }
}
