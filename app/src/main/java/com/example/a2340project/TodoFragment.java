package com.example.a2340project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a2340project.databinding.TodoFragmentBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Comparator;

public class TodoFragment extends Fragment {

private TodoFragmentBinding binding;

private ArrayList<Task> tasks;
private ArrayList<Task> incompleteTasks;
private ToDoList toDoList;

    public TodoFragment(ToDoList toDoList) {
        this.toDoList = toDoList;
    }
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = TodoFragmentBinding.inflate(inflater, container, false);

        // TODO: get rid of this later
        tasks = new ArrayList<>();
        incompleteTasks = new ArrayList<>();

        Bundle menuUpdate = new Bundle();
        menuUpdate.putString("menuStateKey", "HIDE_MENU");
        getParentFragmentManager().setFragmentResult("menuUpdateKey", menuUpdate);


        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/

        binding.sortByButton.setOnClickListener((v) -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), v);
            popupMenu.setOnMenuItemClickListener(this::onTodoListSortOptionClicked);
            popupMenu.getMenuInflater().inflate(R.menu.todo_sort_menu, popupMenu.getMenu());
            popupMenu.show();
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean onTodoListSortOptionClicked(MenuItem option) {
        int id = option.getItemId();

        // TODO: implement sorting with/without completed tasks
        if (id == R.id.sort_by_classes) {
            Snackbar.make(binding.getRoot(), "Sort by classes button", 3000).show();
            return true;
        }
        else if (id == R.id.sort_by_due_date) {
            Snackbar.make(binding.getRoot(), "Sort by due date button", 3000).show();
            return true;
        }
        else {
            return false;
        }
    }
}